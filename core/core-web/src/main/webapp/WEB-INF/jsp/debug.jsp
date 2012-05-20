<div class="tabbable">
    <ul class="nav nav-tabs">
        <li class="active"><a href="#tabParams" data-toggle="tab">Request Parameters
            <span class="badge">${fn:length(paramValues)}</span>
        </a></li>
        <li><a href="#tabRequestAttributes" data-toggle="tab">Request Attributes
            <span class="badge">${fn:length(requestScope)}</span>
        </a></li>
        <li><a href="#tabSessionAttributes" data-toggle="tab">Session Attributes
            <span class="badge">${fn:length(sessionScope)}</span>
        </a></li>
        <li><a href="#tabContextAttributes" data-toggle="tab">Application Attributes
            <span class="badge">${fn:length(requestScope.applicationScope)}</span>
        </a></li>
        <li><a href="#tabCookies" data-toggle="tab">Cookies
            <span class="badge">${fn:length(cookie)}</span>
        </a></li>
        <li><a href="#tabHttpHeaders" data-toggle="tab">HTTP Headers
            <span class="badge">${fn:length(header)}</span>
        </a></li>
        <li><a href="#tabHttpRequest" data-toggle="tab">HTTP Request</a></li>
    </ul>
    <div class="tab-content">
        <div class="tab-pane active" id="tabParams">
            <table class="table table-striped">
                <tbody>
                <c:forEach var="entry" items="${paramValues}">
                    <tr>
                        <td style="width:150px"><span class="label label-success">${entry.key}</span></td>
                        <td>
                            <c:if test="${fn:length(entry.value) eq 1}">
                                ${entry.value[0]}
                            </c:if>
                            <c:if test="${fn:length(entry.value) gt 1}">
                                <span class="badge badge-info">${fn:length(entry.value)} values</span>
                                <ol><c:forEach var="val" items="${entry.value}">
                                    <li>${val}</li>
                                </c:forEach></ol>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="tab-pane" id="tabRequestAttributes">
            <table class="table table-striped">
                <tbody>
                <%--skip fitered--%>
                <c:forEach var="entry" items="${requestScope}">
                    <c:if test="${!fn:startsWith(entry.key, 'javax.') && !fn:startsWith(entry.key, 'org.')}">
                        <tr>
                            <td style="width:150px"><span class="label label-success">${entry.key}</span></td>
                            <td>
                                    <%--<span class="label">${entry.value.class}</span><br/>--%>
                                    ${entry.value}
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
                <%--now display filtered--%>
                <c:forEach var="entry" items="${requestScope}">
                    <c:if test="${fn:startsWith(entry.key, 'javax.') || fn:startsWith(entry.key, 'org.')}">
                        <tr>
                            <td><span class="label">${entry.key}</span></td>
                            <td>
                                    <%--<span class="label">${entry.value.class.name}</span><br/>--%>
                                    ${entry.value}
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="tab-pane" id="tabSessionAttributes">
            <table class="table table-striped">
                <tbody>
                <c:forEach var="entry" items="${sessionScope}">
                    <tr>
                        <td>${entry.key}</td>
                        <td>${entry.value}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="tab-pane" id="tabContextAttributes">
            <table class="table table-striped">
                <tbody>
                <c:forEach var="entry" items="${requestScope.applicationScope}">
                    <tr>
                        <td>${entry.key}</td>
                        <td>${entry.value}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="tab-pane" id="tabHttpRequest">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th style="width:150px">Name</th>
                    <th>Value</th>
                </tr>
                </thead>
                <tbody>
                <%
                    StringBuilder sb = new StringBuilder();
                    sb.append("<tr><td>AuthType</td><td>" + request.getAuthType() + "</td></tr>\n");
                    sb.append("<tr><td>CharacterEncoding</td><td>" + request.getCharacterEncoding() + "</td></tr>\n");
                    sb.append("<tr><td>ContentLength</td><td>" + request.getContentLength() + "</td></tr>\n");
                    sb.append("<tr><td>ContentType</td><td>" + request.getContentType() + "</td></tr>\n");
                    sb.append("<tr><td>ContextPath</td><td>" + request.getContextPath() + "</td></tr>\n");
                    sb.append("<tr><td>Method</td><td>" + request.getMethod() + "</td></tr>\n");
                    sb.append("<tr><td>PathInfo</td><td>" + request.getPathInfo() + "</td></tr>\n");
                    sb.append("<tr><td>PathTranslated</td><td>" + request.getPathTranslated() + "</td></tr>\n");
                    sb.append("<tr><td>Protocol</td><td>" + request.getProtocol() + "</td></tr>\n");
                    sb.append("<tr><td>QueryString</td><td>" + request.getQueryString() + "</td></tr>\n");
                    sb.append("<tr><td>RemoteAddr</td><td>" + request.getRemoteAddr() + "</td></tr>\n");
                    sb.append("<tr><td>RemoteHost</td><td>" + request.getRemoteHost() + "</td></tr>\n");
                    sb.append("<tr><td>RemoteUser</td><td>" + request.getRemoteUser() + "</td></tr>\n");
                    sb.append("<tr><td>RequestedSessionId</td><td>" + request.getRequestedSessionId() + "</td></tr>\n");
                    sb.append("<tr><td>RequestURI</td><td>" + request.getRequestURI() + "</td></tr>\n");
                    sb.append("<tr><td>RequestURL</td><td>" + request.getRequestURL() + "</td></tr>\n");
                    sb.append("<tr><td>Scheme</td><td>" + request.getScheme() + "</td></tr>\n");
                    sb.append("<tr><td>ServerName</td><td>" + request.getServerName() + "</td></tr>\n");
                    sb.append("<tr><td>ServerPort</td><td>" + request.getServerPort() + "</td></tr>\n");
                    sb.append("<tr><td>ServletPath</td><td>" + request.getServletPath() + "</td></tr>\n");
                    out.println(sb.toString());
                %>
                </tbody>
            </table>
        </div>
        <div class="tab-pane" id="tabCookies">
            <table class="table table-striped">
                <tbody>
                <c:forEach items="${cookie}" var="entry">
                    <tr>
                        <td style="width:150px"><span class="label">${entry.key}</span></td>
                        <td>
                            Value: ${entry.value.value}<br/>
                            Path: ${entry.value.path}<br/>
                            Domain: ${entry.value.domain}<br/>
                            Max Age: ${entry.value.maxAge}<br/>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="tab-pane" id="tabHttpHeaders">
            <table class="table table-striped">
                <tbody>
                <c:forEach var="entry" items="${headerValues}">
                    <tr>
                        <td style="width:150px"><span class="label label-success">${entry.key}</span></td>
                        <td>
                            <c:if test="${fn:length(entry.value) eq 1}">
                                ${entry.value[0]}
                            </c:if>
                            <c:if test="${fn:length(entry.value) gt 1}">
                                <ol><c:forEach var="val" items="${entry.value}">
                                    <li>${val}</li>
                                </c:forEach></ol>
                            </c:if>

                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

