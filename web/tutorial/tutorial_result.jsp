<%-- 
    Document   : tutorial_result
    Created on : 2015. 2. 7, 오전 12:08:57
    Author     : Hajin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <%@ include file="/header.jsp" %>
    <header class="sectionTitle">Tutorial Timetable Result</header>
    <div class="content">
        <a href="${pageContext.request.contextPath}/main.jsp">Back to Main</a>
        <h1>The software has failed to generate the timetable, please refer to the following list of the subjects and their clashes</h1>
            <table class = "table-striped table-tutorial-edit">
                <c:forEach var="tute" items="${requestScope.tutes}">
                    <tr>
                        <td><c:out value="${tute.getCode()}"></c:out></td>
                        <td><c:out value="${tute.getTutor().getName()}"></c:out></td>
                        <td><c:out value="${tute.getConstraints().toString()}"></c:out></td>
                    </tr>
                </c:forEach>   
            </table> 
    </div>
    <%@ include file="/footer.jsp" %>
</html>