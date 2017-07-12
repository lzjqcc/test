<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false"%>
<html>
<head>
    <title>Title</title>
    welcome ${username}
</head>
<body>
    <a href="<%=application.getContextPath()%>/delete">delete</a>
    <a href="<%=application.getContextPath()%>/insert">insert</a>
    <a href="<%=application.getContextPath()%>/find">find</a>
    ${message}
</body>
</html>
