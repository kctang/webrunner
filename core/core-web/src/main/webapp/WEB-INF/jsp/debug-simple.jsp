<%@ page import="java.util.Enumeration"%>
<%
    String name;
    Enumeration e;
    StringBuffer sb;
%>
<style type="text/css">
    <!--
    .dbg {
        font-size: 90%;
    }
    //-->
</style>

<%-- BEGIN: Request Processing Time --%>
<br><br>
<table class="dbg" width="100%" border="0" cellpadding="2" cellspacing="0">
    <tr>
        <td bgcolor="000000"><font color="FFFFFF">
            <b>Request Processed [${requestScope.processingTime}ms]</b>
        </font></td>
    </tr>
</table>
<br>
<%-- END: Request Processing Time --%>

<%-- BEGIN: Request Parameters --%>
<table class="dbg" width="100%" border="0" cellpadding="2" cellspacing="0">
    <tr>
        <td bgcolor="#CCCCCC"> <b>Request Parameters</b></td>
    </tr>
    <tr>
        <td>
            <%
                sb = new StringBuffer();
                e = request.getParameterNames();
                String[] values;

                if(!e.hasMoreElements()) {
                    sb.append("<table class=\"dbg\" width=\"100%\" border=\"1\" cellpadding=\"2\" cellspacing=\"0\">\n");
                    sb.append("  <tr>\n");
                    sb.append("    <td>-No Request Parameter-</td>\n");
                    sb.append("  </tr>\n");
                    sb.append("</table>\n");
                } else {
                    sb.append("<table class=\"dbg\" width=\"100%\" border=\"1\" cellpadding=\"2\" cellspacing=\"0\">\n");
                    while(e.hasMoreElements()) {
                        name = (String) e.nextElement();
                        values = request.getParameterValues(name);

                        sb.append("  <tr><td>" + name + "</td><td>&nbsp;");

                        if(values.length>1) {
                            for(int i=0; i<values.length; i++) {
                                sb.append("<i>[" + i + "]</i> " +  values[i] + "<br>");
                            }
                        } else {
                            sb.append(values[0]);
                        }

                        sb.append("</td></tr>\n");
                    }
                    sb.append("</table>\n");
                }
                out.print(sb.toString());
            %>
        </td>
    </tr>
</table>
<br>
<%-- END: Request Parameters --%>


<table class="dbg" width="100%" border="0" cellpadding="2" cellspacing="0">
    <tr>
        <td bgcolor="#CCCCCC"> <b>Attributes</b></td>
    </tr>
    <tr>
        <td>
            <%-- BEGIN: Request Attributes --%>
            <table class="dbg" width="100%" border="1" cellpadding="2" cellspacing="0">
                <tr>
                    <td colspan="3"><b><i>Request</i></b></td>
                </tr>
                <%
                    e = request.getAttributeNames();
                    if(!e.hasMoreElements()) {
                %>
                <tr>
                    <td colspan="3">-No Attribute-</td>
                </tr>
                <%
                } else {
                %>
                <tr>
                    <td><i>Name</i></td>
                    <td><i>Type</i></td>
                    <td><i>Value</i></td>
                </tr>
                <%
                    while(e.hasMoreElements()) {
                        name = (String) e.nextElement();
                        pageContext.setAttribute("name", name);

                        if(request.getAttribute(name)!=null) {
                %>
                <tr valign="top">
                    <td><%= name %></td>
                    <td><%= request.getAttribute(name).getClass().getName() %></td>
                    <td><pre style="overflow: auto">${requestScope[name]}
                    </pre></td>
                </tr>
                <%
                            }
                        }
                    }
                %>
            </table>
            <br>
            <%-- END: Request Attributes --%>

            <%-- BEGIN: Session Attributes --%>
            <table class="dbg" width="100%" border="1" cellpadding="2" cellspacing="0">
                <tr>
                    <td colspan="3"><b><i>Session</i></b></td>
                </tr>
                <%
                    e = request.getSession().getAttributeNames();
                    if(!e.hasMoreElements()) {
                %>
                <tr>
                    <td colspan="3">-No Attribute-</td>
                </tr>
                <%
                } else {
                %>
                <tr>
                    <td><i>Name</i></td>
                    <td><i>Type</i></td>
                    <td><i>Value</i></td>
                </tr>
                <%
                    while(e.hasMoreElements()) {
                        name = (String) e.nextElement();
                %>
                <tr valign="top">
                    <td><%= name %></td>
                    <td><%= request.getSession().getAttribute(name).getClass().getName() %></td>
                    <td><pre style="overflow: auto"><%= request.getSession().getAttribute(name) %>
              </pre></td>
                </tr>
                <%
                        }
                    }
                %>
            </table>
            <br>
            <%-- END: Session Attributes --%>

            <%-- BEGIN: Context Attributes --%>
            <%
                boolean showContextAttributes = false;
                if(showContextAttributes) {
            %>
            <table class="dbg" width="100%" border="1" cellpadding="2" cellspacing="0">
                <tr>
                    <td colspan="3"><b><i>Context</i></b></td>
                </tr>
                <%
                    e = request.getSession().getServletContext().getAttributeNames();
                    if(!e.hasMoreElements()) {
                %>
                <tr>
                    <td colspan="3">-No Attribute-</td>
                </tr>
                <%
                } else {
                %>
                <tr>
                    <td><i>Name</i></td>
                    <td><i>Type</i></td>
                    <td><i>Value</i></td>
                </tr>
                <%
                    while(e.hasMoreElements()) {
                        name = (String) e.nextElement();
                %>
                <tr valign="top">
                    <td><%= name %></td>
                    <td><%= request.getSession().getServletContext().getAttribute(name).getClass().getName() %></td>
                    <td><pre style="overflow: auto"><%= request.getSession().getServletContext().getAttribute(name) %>
                  </pre></td>
                </tr>
                <%
                        }
                    }
                %>
            </table>
            <br>
            <%
                }
            %>
            <%-- END: Context Attributes --%>

        </td>
    </tr>
</table>

<%-- BEGIN: Http Request --%>
<table class="dbg" width="100%" border="0" cellpadding="2" cellspacing="0">
    <tr>
        <td bgcolor="#CCCCCC"> <b>Http Request</b></td>
    </tr>
    <tr>
        <td>
            <%
                sb = new StringBuffer();
                sb.append("<table class=\"dbg\" width=\"100%\" border=\"1\" cellpadding=\"2\" cellspacing=\"0\">\n");
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
                sb.append("</table>\n");
                out.println(sb.toString());
            %>
        </td>
    </tr>
</table>
<br>
<%-- END: Http Request --%>

<%-- BEGIN: Cookies --%>
<table class="dbg" width="100%" border="0" cellpadding="2" cellspacing="0">
    <tr>
        <td bgcolor="#CCCCCC"> <b>Cookies</b></td>
    </tr>
    <tr>
        <td>
            <%
                sb = new StringBuffer();
                Cookie[] cookies = request.getCookies();

                if(cookies==null || cookies.length==0) {
                    sb.append("<table class=\"dbg\"  width=\"100%\" border=\"1\" cellpadding=\"2\" cellspacing=\"0\">\n");
                    sb.append("  <tr> \n");
                    sb.append("    <td>-No Cookie-</td>\n");
                    sb.append("  </tr>\n");
                    sb.append("</table>\n");
                } else {
                    sb.append("<table class=\"dbg\" width=\"100%\" border=\"1\" cellpadding=\"2\" cellspacing=\"0\">\n");
                    for(int i=0; i<cookies.length; i++) {
                        sb.append("  <tr> \n");
                        sb.append("    <td>" + cookies[i].getName() + "</td>\n");
                        sb.append("    <td>");
                        sb.append("value=" + cookies[i].getValue());
                        sb.append("; expires=" + cookies[i].getMaxAge());
                        sb.append("; path=" + cookies[i].getPath());
                        sb.append("; domain=" + cookies[i].getDomain());
                        sb.append("</td>\n");
                        sb.append("  </tr>\n");
                    }
                    sb.append("</table>\n");
                }
                out.print(sb.toString());
            %>
        </td>
    </tr>
</table>
<br>
<%-- END: Cookies --%>

<%-- BEGIN: HTTP Headers--%>
<table class="dbg" width="100%" border="0" cellpadding="2" cellspacing="0">
    <tr>
        <td bgcolor="#CCCCCC"><b>HTTP Headers</b></td>
    </tr>
    <tr>
        <td>
            <%
                sb = new StringBuffer();
                e = request.getHeaderNames();

                if(!e.hasMoreElements()) {
                    sb.append("<table class=\"dbg\" width=\"100%\" border=\"1\" cellpadding=\"2\" cellspacing=\"0\">\n");
                    sb.append("  <tr> \n");
                    sb.append("    <td>-No Http Header-</td>\n");
                    sb.append("  </tr>\n");
                    sb.append("</table>\n");
                } else {
                    sb.append("<table class=\"dbg\" width=\"100%\" border=\"1\" cellpadding=\"2\" cellspacing=\"0\">\n");
                    while(e.hasMoreElements()) {
                        name = (String) e.nextElement();
                        sb.append("  <tr><td>" + name + "</td><td>" + request.getHeader(name) + "</td></tr>\n");
                    }
                    sb.append("</table>\n");
                }
                out.print(sb.toString());
            %>
        </td>
    </tr>
</table>
<%-- END: HTTP Headers--%>