<c:set var="title">List Users (Test Page)</c:set>
<bootstrap:template1 title="${title}" debug="true">
    <jsp:attribute name="navbar">
        <a class="brand" href="#">${title}</a>
    </jsp:attribute>
    <jsp:body>
        <%--@elvariable id="userList" type="java.util.List<model.demo.jpa.User>"--%>
        <c:forEach items="${userList}" var="user">
            ${user.username}<br/>
        </c:forEach>
    </jsp:body>
</bootstrap:template1>
