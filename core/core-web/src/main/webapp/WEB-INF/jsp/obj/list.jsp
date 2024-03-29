<%@ taglib tagdir="/WEB-INF/tags/bootstrap" prefix="bootstrap" %>
<%@ taglib tagdir="/WEB-INF/tags/crud" prefix="crud" %>
<c:set var="baseUrl" value="${context}/obj" scope="page"/>
<bootstrap:template1 title="${cs.name}">
    <jsp:attribute name="navbar">
        <a class="brand" href="#">Module Admin</a>
    </jsp:attribute>
    <jsp:attribute name="sidebar">
        <div class="well">
            <script type="text/javascript">
                $(function () {
                    var list = $('ul#sidebarList');
                    $.getJSON('${baseUrl}/api/list', function (json) {
                        for (var key in json.data) {
                            var item = json.data[key];
                            list.append('<li><a href="${baseUrl}/' + item.slug + '">' + item.name + '</a></li>');
                        }
                        list.append('<li class="divider"></li>');
                    });
                });
            </script>

            <ul class="nav nav-list" id="sidebarList">
                <li class="nav-header">
                    Module
                </li>
            </ul>

        </div>
    </jsp:attribute>
    <jsp:body>
        <crud:list/>
    </jsp:body>
</bootstrap:template1>
