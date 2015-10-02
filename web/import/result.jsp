<%-- 
    Document   : result
    Created on : 2015. 2. 26, 오전 11:16:57
    Author     : Hajin
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <%@ include file="/header.jsp" %>
    <section>
        <div class="row">
            <header class="page-header">
                <h1>Import Data</h1>
            </header>
        </div> 
        <div class="col-xs-6">
            <p class="lead hugefy">Number of Rows parsed: <c:out value="${requestScope.rows}"></c:out></p>
            <p class="lead hugefy">Number of Students parsed: <c:out value="${requestScope.students}"></c:out></p>
            <p class="lead hugefy">Number of Subjects parsed: <c:out value="${requestScope.subjects}"></c:out></p>
            <p class="lead hugefy">Number of Student-Subject relationship parsed: <c:out value="${requestScope.ss}"></c:out></p>
            <a href="${pageContext.request.contextPath}/import/main.jsp" class="btn btn-info btn-lg menu-btn">Back to Data Manager Main</a>
        </div>
    </section>
    <%@ include file="/footer.jsp" %>
</html>
