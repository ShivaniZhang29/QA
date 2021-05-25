<%@ page import="com.example.Web.WolframAlpha2" %><%--
  Created by IntelliJ IDEA.
  User: shivani
  Date: 2021/5/25
  Time: 1:29 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String question = request.getParameter("question");
%>
<html>
<head>
    <title>Answer</title>
</head>
<body>
<p>Answer to your query: </p>
<%=WolframAlpha2.query(question)%>
</body>
</html>
