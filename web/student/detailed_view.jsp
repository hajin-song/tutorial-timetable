<%-- 
    Document   : detailed_view
    Created on : 2015. 2. 2, 오전 9:33:24
    Author     : Hajin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <%@ include file="/header.jsp" %>
    
    <section>
        <header class="page-header col-xs-12">
            <h1>Student Details</h1>
        </header>
        <div class="row">
            <c:set var="student" value="${student}" scope="request"/>
            <div class="col-xs-4">
                <label class="detail-view">Student ID</label>
                <label class="form-control input-lg detail-view"><c:out value="${student.studentId}"/></label>
                <label class=" detail-view">Student Name</label>
                <label class="form-control input-lg detail-view"> <c:out value="${student.getName()}"/></label>
            </div>
            <div class="col-xs-8 table-responsive">
                <table class = "table-striped">
                    <tr class="table-header">
                        <th class="col-xs-2">Subject Code</th>
                        <th class="col-xs-2">Subject Name</th>
                        <th class="col-xs-1">Semester</th>
                        <th class="col-xs-2">Package Title</th>
                        <th class="col-xs-1">Degree</th>
                        <th class="col-xs-1"></th>
                    </tr>
                    <c:forEach var="subject" items="${requestScope.subjects}">
                        <c:set var="subjectPK" value="${subject.studentsubjectPK}"></c:set>
                        <tr class="table-row">
                            <form action='${pageContext.request.contextPath}/StudentHandler' method='POST'>
                                <input type='hidden' name='studentid' value = <c:out value="${subjectPK.studentId}"></c:out> />
                                <td class="col-xs-1"><input size="8" type='text' name='subjectcode' value = <c:out value="${subjectPK.subjectCode}"></c:out> readonly='readonly' /></td>
                                <td class="col-xs-2"><c:out value="${subject.getSubject().subjectName}"/></td>
                                <td class="col-xs-1"><input size="1" type='text' name='semester' value = <c:out value="${subjectPK.semester}"></c:out> readonly='readonly' /></td>
                                <td class="col-xs-2"><c:out value="${subject.studyPkgTitle}"/></td>
                                <td class="col-xs-1"><c:out value="${subject.degreeType}"/></td>
                                <td class="col-xs-1"><input type='submit' name='action' value='Remove Subject' class="btn btn-warning btn-lg" onClick="return confirm('Are you sure you?');"/></td>
                            </form>
                        </tr>
                    </c:forEach>     
                </table>
            </div>
        </div>
    </section>

    <%@ include file="/footer.jsp" %>
</html>
