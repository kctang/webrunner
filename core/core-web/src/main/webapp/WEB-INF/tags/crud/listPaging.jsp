<c:if test="${page.totalPages gt 1}">
    <div class="pagination pagination-right">
        <ul>
            <c:set var="paramQ" value="q=${param.q}"/>

            <c:if test="${!page.firstPage}">
                <li><a href="${baseUrl}?${paramQ}&page.page=${page.number}">Prev</a></li>
            </c:if>

            <c:set var="maxPages" value="5"/>

            <c:forEach var="currentPage"
                       begin="${page.number - maxPages >= 0 ? page.number - maxPages : 0}"
                       end="${page.number + maxPages < page.totalPages ? page.number + maxPages : page.totalPages-1}">

                <c:if test="${page.number eq currentPage}">
                    <li class="active">
                        <a href="#">${currentPage+1}</a>
                    </li>
                </c:if>
                <c:if test="${page.number ne currentPage}">
                    <li>
                        <a href="${baseUrl}?${paramQ}&page.page=${currentPage+1}">${currentPage+1}</a>
                    </li>
                </c:if>

            </c:forEach>

            <c:if test="${!page.lastPage}">
                <li><a href="${baseUrl}?${paramQ}&page.page=${page.number+2}">Next</a></li>
            </c:if>
        </ul>
    </div>
</c:if>
