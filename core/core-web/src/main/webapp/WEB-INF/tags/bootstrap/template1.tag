<%@ include file="/WEB-INF/jsp/common.jsp" %>
<%@tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="title" %>
<%@ attribute name="head" fragment="true" %>
<%@ attribute name="navbar" fragment="true" %>
<%@ attribute name="sidebar" fragment="true" %>
<%@ attribute name="debug" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title><c:out value="${title}" default="[Template]"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <style type="text/css">
        body {
            padding-top: 60px;
            padding-bottom: 40px;
        }
    </style>

    <link href="${context}/core/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <script src="${context}/core/jquery/jquery-1.7.2.min.js"></script>
    <script src="${context}/core/bootstrap/js/bootstrap.min.js"></script>

    <link href="${context}/core/chosen/chosen.css" rel="stylesheet">
    <script src="${context}/core/chosen/chosen.jquery.min.js"></script>

    <jsp:invoke fragment="head"/>
</head>
<body>

<c:if test="${not empty navbar}">
    <div class="navbar navbar-fixed-top">
        <div class="navbar-inner">
            <div class="container-fluid">
                <jsp:invoke fragment="navbar"/>
            </div>
        </div>
    </div>
</c:if>

<div class="container-fluid">
    <div class="row-fluid">
        <c:if test="${not empty sidebar}">
            <div class="span3">
                <jsp:invoke fragment="sidebar"/>
            </div>
            <div class="span9">
                <jsp:doBody/>
            </div>
        </c:if>
        <c:if test="${empty sidebar}">
            <div class="span12">
                <jsp:doBody/>
            </div>
        </c:if>
    </div>
    <c:if test="${debug}">
        <div class="row-fluid">
            <div class="span12">
                <jsp:include page="/WEB-INF/jsp/debug.jsp"/>
            </div>
        </div>
    </c:if>
</div>

</body>
</html>