<%-- 
    Document   : view
    Created on : 2015. 2. 1, 오전 10:26:21
    Author     : Hajin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <%@ include file="/header.jsp" %>
    <section>
    <div class='row'>
        <header class="page-header col-xs-12">
            <h1>List of Student</h1>
        </header>
    </div>
        <div class="table-responsive">
            <table class = "table-striped">
                <tr class="table-header">
                    <td class="col-xs-3">Student ID</td>
                    <td class="col-xs-3">Student Name</td>
                    <td class="col-xs-2">Action</td>
                </tr>
                <c:forEach var="student" items="${requestScope.result}">
                    <tr class="table-row">
                        <form action='StudentHandler' method='POST'>
                        <td class="col-xs-3"><input size="8" type='text' name='studentid' value = <c:out value="${student.studentId}"></c:out> readonly='readonly' /></td>
                        <td class="col-xs-3"><c:out value="${student.getName()}"></c:out></td>
                        <td class="col-xs-2"><input type='submit' name='action' value='View Detail' class="btn btn-info btn-lg"/></td>
                        </form>
                    </tr>

                </c:forEach>        
            </table>
        </div>
    </section>
        <%@ include file="/footer.jsp" %>
</html>
