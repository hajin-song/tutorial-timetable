<%-- 
    Document   : set_tutors
    Created on : 2015. 2. 6, 오후 4:37:33
    Author     : Hajin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">


<html>
    <%@ include file="/header.jsp" %>
    <div class="row">
        <header class="page-header col-xs-12">
            <h1>Set Tutor for Subject</h1>
        </header>
    </div>
    <div class="error row well">
        <c:forEach var="error" items="${requestScope.errors}">
            <p><c:out value="${error}"></c:out></p>
        </c:forEach>  
    </div>
    
    <section class="row">
        <div class="col-xs-4 well">
            <div class="section-header">
                <h2>Add New Tutorial</h3>
            </div>
                <form method="POST" action="${pageContext.request.contextPath}/TutorialHandler" >
                    <div class="form-group">
                        <div class="col-xs-5">
                            <label for="code">Subject Code</label>    
                            <input type="text" name="code" id ='code' class="form-control input-lg"/><br/>
                            <input type='submit' name='action' value='Add New Tutorial' class="btn btn-default btn-primary btn-lg"/>
                        </div>
                    </div>
                </form>
        </div>
    </section>
    <div>
        <div class="section-header">
            <h2>Tutorial Details</h3>
        </div>
            <form action='${pageContext.request.contextPath}/TutorialHandler' method='POST'>
                <input type="hidden" name="tutorials" value="requestScope.tutes"/>
                <div class="row table-responsive">
                    <table class = "table-striped table-tutorial-edit">
                        <thead>
                            <tr class="header">
                                <th class="col-xs-2">Subject Code</th>
                                <th class="col-xs-2">Subject Name</th>
                                <th class="col-xs-1">Student No.</th>
                                <th class="col-xs-2">Tutor</th>
                                <th class="col-xs-2">Students</th>
                                <th class="col-xs-1">Delete</th>
                                <th class="col-xs-1">Collapse</th>
                            </tr>
                        </thead>

                        <c:forEach var="tutes" items="${requestScope.tutes}">
                            <tbody>
                                <tr>
                                    <td><c:out value="${tutes.getCode()}"></c:out></td>
                                    <td><input type="text" name="${tutes.getCode()}_name" class="form-control input-lg" value="${tutes.getName()}"/></input></td>
                                    <td><c:out value="${tutes.getStudents().size()}"></c:out></td>
                                    <td>
                                        <select class="select input-lg" name=<c:out value="${tutes.getCode()}_tutor"></c:out> >
                                            <option value disabled selected>SELECT TUTOR</option>
                                            <c:forEach items="${requestScope.staffs}" var="tutor">
                                                <c:choose>
                                                    <c:when test="${tutes.getTutor().getName()==tutor.getName()}">
                                                        <option value="${tutor.getName()}" selected="selected">${tutor.getName()}</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${tutor.getName()}">${tutor.getName()}</option>
                                                    </c:otherwise>
                                                </c:choose>

                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td>
                                        <span onmouseover="show(tp_${tutes.getCode()})" onmouseout="hide(tp_${tutes.getCode()})">Show Student</span>
                                        <table class="tbl-sub" id="tp_${tutes.getCode()}">
                                        <c:forEach var="student" items="${tutes.getStudents()}">
                                            <tr><td class="col-xs-7">${student.getName()}</td></tr>
                                        </c:forEach>
                                        </table>
                                    </td>
                                    <td>
                                        <input type="checkbox" name="${tutes.getCode()}_delete" value="true"></input>
                                    </td>
                                    <td>
                                        <input type="checkbox" name="${tutes.getCode()}_collapse" value="true"></input>
                                    </td>       
                                </tr>
                            </tbody>
                        </c:forEach>   
                    </table>
                </div>
                <input class="btn btn-default btn-primary btn-lg" type='submit' name='action' value='Save Changes' onClick="return confirm('Are you sure you?');"/>    
            </form>
    </div>

    <%@ include file="/footer.jsp" %>
    <script>
        function show (elem) {  
            elem.style.display="block";
        }
        function hide (elem) { 
            elem.style.display=""; 
        }
    </script>

</html>

