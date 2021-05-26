package com.example.Web;

import com.wolfram.alpha.WAEngine;
import com.wolfram.alpha.WAException;
import com.wolfram.alpha.WAPlainText;
import com.wolfram.alpha.WAPod;
import com.wolfram.alpha.WAQuery;
import com.wolfram.alpha.WAQueryResult;
import com.wolfram.alpha.WASubpod;

import java.sql.*;


public class WolframAlpha2 {
    private static String APPID = "JV3K2Y-58RKKEHW5V";

    public static String query(String question) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        final String url = "jdbc:mysql://172.31.11.200:3306/qa_db?user=admin&password=981229Zy&characterEncoding=UTF-8&useSSL=false";
        Connection conn = DriverManager.getConnection(url);
        Statement statement = conn.createStatement();
        String querySql = String.format("select * from qa where question = '%s'", question);
        ResultSet resultSet = statement.executeQuery(querySql);
        String answer = null;
        while (resultSet.next()) {
            answer = resultSet.getString("answer");
        }

        if (answer == null) {
            WAEngine engine = new WAEngine();
            engine.setAppID(WolframAlpha2.APPID);
            engine.addFormat("plaintext");
            WAQuery query = engine.createQuery();
            query.setInput(question);
            try {
                System.out.println("Asking Wolfram Alpha: " + question);
                WAQueryResult queryResult = engine.performQuery(query);

                if (queryResult.isError()) {
                    return "<noresults></noresults>";
                } else if (!queryResult.isSuccess()) {
                    return "<noresults></noresults>";
                } else {
                    for (WAPod pod : queryResult.getPods()) {
                        for (WASubpod subpod : pod.getSubpods()) {
                            for (Object element : subpod.getContents()) {
                                if (element instanceof WAPlainText) {
                                    answer = ((WAPlainText) element).getText();
                                    String insertSql = String.format("insert into qa (question, answer) values ('%s', '%s')",
                                            question, answer);
                                    statement.executeUpdate(insertSql);
                                }
                            }
                        }
                    }
                }
            } catch (WAException e) {
                return "<noresults></noresults>";
            }
        }

        if (resultSet != null) {
            try {
                statement.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            } finally {
                statement = null;
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            } finally {
                resultSet = null;
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            } finally {
                conn = null;
            }
        }


        if (answer != null) {
            StringBuffer sb = new StringBuffer("");
            sb.append("<section><title>" + question + "</title><sectioncontents>");
            sb.append(answer);
            sb.append("</sectioncontents></section>");

            return sb.toString();
        }

        return "<noresults></noresults>";
    }

}