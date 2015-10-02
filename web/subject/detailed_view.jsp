<%-- 
    Document   : detailed_view
    Created on : 2015. 2. 3, 오전 11:25:40
    Author     : Hajin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <%@ include file="/header.jsp" %>
    <div class="row">
        <header class="page-header col-xs-12">
            <h1>Subject Details</h1>
        </header>
    </div>
    <div class="error well lead">
        <c:forEach var="error" items="${requestScope.errors}">
            <p><c:out value="${error}"></c:out></p>
        </c:forEach>  
    </div>   
    <section>
        <c:set var="subject" value="${subject}" scope="request"/>
        <c:set var="students" value="${students}" scope="request"/>
        <c:set var="sss" value="${sss}" scope="request"/>
        <div class="row">
            <div class="col-xs-3">
                    <label class="detail-view">Subject Code</label>
                    <label class="form-control detail-view input-lg"><c:out value="${subject.subjectCode}"></c:out></label>
                    <label class="detail-view">Subject Name</label>
                    <label class="form-control detail-view input-lg"><c:out value="${subject.subjectName}"></c:out></label>
                    <label class="detail-view">Subject Size</label>
                    <label class="form-control detail-view input-lg"><c:out value="${subject.size}"></c:out></label>
                <div class= "well lead">
                    <div class="section-header">
                        <h3>Register New Student</h3>
                    </div>
                    <form method="POST" action="${pageContext.request.contextPath}/SubjectHandler" >
                        <div class="form-group">
                            <label for="ID" class="detail-view">Student ID</label>    
                            <input type="text" name="ID" id ='ID' class="form-control input-lg"/><br/>
                            <label for="semester" class="detail-view">Semester</label>    
                            <select name = "semester" id ="semester" class ="form-control input-lg">
                                <option value="1">Semester One</option>
                                <option value="2">Semester Two</option>
                            </select><br/> 
                            <input type='hidden' name='subjectcode' value = <c:out value="${subject.subjectCode}"></c:out>  />
                            <input type='submit' name='action' value='Register Student' class="btn btn-primary btn-lg"/>
                        </div>
                    </form>
                </div>
            </div>
                            
            <div class="col-xs-9">
                <div class="col-xs-11 table-responsive">
                    <c:choose>
                    <c:when test="${fn:length(students)>0}">
                        <table class = "table-striped">
                            <tr class="table-header">
                                <th class="col-xs-1">Student ID</th>
                                <th class="col-xs-3">Student Name</th>
                                <th class="col-xs-1">Semester</th>
                                <th class="col-xs-1"></th>
                            </tr>
                            <c:forEach var="i" begin="0" end="${fn:length(students)-1}" > 
                                <c:set var="studentPK" value="${sss[i]}"/>
                                <c:set var="student" value="${students[i]}"/>
                                <tr class="table-row">
                                    <form action='${pageContext.request.contextPath}/SubjectHandler' method='POST'>
                                        <input type='hidden' name='subjectcode' value = <c:out value="${subject.subjectCode}"></c:out>  />
                                        <td class="col-xs-1"><input size="6" type='text' name='studentid' value = <c:out value="${student.studentId}"></c:out> readonly='readonly' /></td>
                                        <td class="col-xs-3"><c:out value="${student.getName()}"></c:out></td>
                                        <td class="col-xs-1"><input size="1" type='text' name='semester' value = <c:out value="${studentPK.getStudentsubjectPK().semester}"></c:out> readonly='readonly' /></td>
                                        <td class="col-xs-1"><input type='submit' name='action' onClick="return confirm('Are you sure you?');"  value='Remove Student' class="btn btn-warning btn-lg"/></td>
                                    </form>
                                </tr>
                            </c:forEach>     
                        </table>
                    </c:when>
                    <c:otherwise>No Students to Display</c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </section>

    <%@ include file="/footer.jsp" %>
</html>
