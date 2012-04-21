<gae:template title="GaeRun WebshareConfiguration">
    <ul>
        <c:forEach items="${configs}" var="config">
            <%--@elvariable id="config" type="net.big2.gaerun.core.dev.WebshareConfiguration"--%>
            <li>${config.name}
                <ul>
                    <c:forEach var="url" items="${config.urlList}">
                        <li><a href="${url}">${url}</a></li>
                    </c:forEach>
                </ul>
            </li>
        </c:forEach>
    </ul>
</gae:template>