<%--
  Created by IntelliJ IDEA.
  User: shivani
  Date: 2021/5/21
  Time: 10:14 上午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="wolfram.WolframAlpha2" %>

<!DOCTYPE html>
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