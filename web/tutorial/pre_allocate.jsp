<%-- 
    Document   : pre_allocate
    Created on : 2015. 2. 8, 오후 9:34:32
    Author     : Hajin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <%@ include file="/header.jsp" %>
    <header class="page-header">
        <h1>Manual Allocation</h1>
    </header>
    <section class="table-responsive col-xs-8">
        <form action='${pageContext.request.contextPath}/TutorialHandler' method='POST'>
            <table class = "table-striped table-tutorial-edit">
                <thead>
                    <tr class="header">
                        <th class="col-xs-1">Stream</th>
                        <c:forEach var="room" items="${requestScope.rooms}">
                            <th class="col-xs-1">
                                <c:out value="${room.getRoomName()}"></c:out>
                            </th>     
                        </c:forEach>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="stream" items="${requestScope.streams}">
                        <tr>
                            <td><c:out value="${stream.toFormattedString()}"></c:out></td>
                            <c:forEach var="room" items="${requestScope.rooms}">
                                <td>
                                    <select class="select" name=<c:out value="${stream.toString()}_${room.toString()}_tute"></c:out> >
                                        <option value disabled selected> -- select tutorial -- </option>
                                        <c:forEach items="${requestScope.tutes}" var="tute">
                                            <option value="${tute.getCode()}">${tute.getCode()}</option>
                                        </c:forEach>
                                    </select>
                                </td>     
                            </c:forEach>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <input type='submit' name='action' value='Generate Timetable' class="btn btn-default btn-primary btn-lg hugefy"/>
        </form>
    </section>
    
    
    
    <%@ include file="/footer.jsp" %>
</html>