<%-- 
    Document   : set_constraints
    Created on : 2015. 2. 4, 오후 3:48:54
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
            <h1>Tutorial Constraints</h1>
        </header>   
    </div>
    <div class="error row well">
        <c:forEach var="error" items="${requestScope.errors}">
            <p><c:out value="${error}"></c:out></p>
        </c:forEach>  
    </div>
    
    <section class="form-group col-xs-8">
        <div class="section-header">
            <h2>Set Constraints</h2>
        </div>
        <div class="col-xs-3">                        
            <form action='${pageContext.request.contextPath}/TutorialHandler' method='POST'>
                <div class="row hugefy">
                    <label for="minStudents">Minimum Number of Students</label>    
                    <input type='text' name='minStudents' id="minStudents" class="form-control input-lg" />
                </div>
                <div class="row hugefy">
                    <label for="maxStudents">Maximum Number of Students</label>    
                    <input type='text' name='maxStudents' id="maxStudents" class="form-control input-lg" />
                </div>
                <div class="row hugefy">
                    <label for="semester">Semester</label>    
                    <select name = "semester" id ="semester" class ="form-control input-lg">
                        <option value="1">Semester One</option>
                        <option value="2">Semester Two</option>
                    </select>
                </div>
                <div class="row hugefy">
                    <label for="level">Subject Level</label>    
                    <div class="checkbox col-xs-12">
                        <label><input type="checkbox" name="level" value="1" class ="checkbox checkboxl">Level One</input></label><br/>
                        <label><input type="checkbox" name="level" value="2" class ="checkbox checkboxl">Level Two</input></label><br/>
                        <label><input type="checkbox" name="level" value="3" class ="checkbox checkboxl">Level Three</input></label><br/>
                        <label><input type="checkbox" name="level" value="4" class ="checkbox checkboxl">Level Four</input></label><br/>
                        <label><input type="checkbox" name="level" value="9" class ="checkbox checkboxl">Level Nine</input></label><br/>
                    </div>
                </div>
                <input type='submit' name='action' value='Get Subjects' class="btn btn-default btn-primary btn-lg"/>
            </form>
        </div>
    </section>
    <%@ include file="/footer.jsp" %>
</html>

