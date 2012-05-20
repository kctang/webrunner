<%--
net.big2.webrunner.core.web.BaseController
--%>
<bootstrap:template1 title="Bootstrap" debug="true">
    <jsp:attribute name="navbar">
        <a class="brand" href="#">Error!</a>
    </jsp:attribute>
    <jsp:body>
        <div class="page-header">
            <h1>${message}
                <small>${exception}</small>
            </h1>
        </div>
        <pre class="pre-scrollable">${stackTrace}</pre>
    </jsp:body>
</bootstrap:template1>

