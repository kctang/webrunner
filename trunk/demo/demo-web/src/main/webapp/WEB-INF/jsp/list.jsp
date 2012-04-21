<html>
<head>
    <title></title>
</head>
<body>

<%--@elvariable id="personList" type="java.util.List<net.big2.webrunner.demo.jpa.model.Person>"--%>
<c:forEach items="${personList}" var="person">
    ${person.name}<br/>
</c:forEach>

<%@include file="debug.jsp"%>

</body>
</html>