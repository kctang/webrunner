<%--
net.big2.webrunner.core.web.BaseController
--%>
<bootstrap:template1 title="Bootstrap" debug="false">
    <jsp:attribute name="navbar">
        <a class="brand" href="#">Error!</a>
    </jsp:attribute>
    <jsp:body>
        <div class="page-header">
            <h1>${exception.message}
                <div><small>${message}</small></div>
            </h1>
        </div>
        <pre class="pre-scrollable">${stackTrace}</pre>
    </jsp:body>
</bootstrap:template1>