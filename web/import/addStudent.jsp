<%-- 
    Document   : addStudent
    Created on : 2015. 3. 27, 오후 8:30:28
    Author     : Hajin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <%@ include file="/header.jsp" %>
    <section>
        <div class="row">
            <header class="page-header col-xs-12">
                <h1>Manual Student Import</h1>
            </header>
        </div>  
        <div class="error well">
            <c:forEach var="error" items="${requestScope.errors}">
                <p><c:out value="${error}"></c:out></p>
            </c:forEach>  
        </div>
        <div class="col-xs-4">
            <form method="POST" action="${pageContext.request.contextPath}/ImportHandler" >
                <div class="form-group">
                    <div class="col-xs-6">
                        <label for="fname" class="detail-view">Student ID</label>    
                        <input type="text" name="ID" id ='ID' class="form-control detail-view input-lg"/>
                        <label for="fname" class="detail-view">Student First Name</label>    
                        <input type="text" name="fname" id ='fname' class="form-control detail-view input-lg"/>
                        <label for="mname" class="detail-view">Student Middle Name</label>    
                        <input type="text" name="mname" id ='mname'class="form-control detail-view input-lg"/>
                        <label for="lname" class="detail-view">Student Last Name</label>    
                        <input type="text" name="lname" id='lname' class="form-control detail-view input-lg"/>
                        <label for="lname" class="detail-view">Student Title</label>    
                        <select name = "title" id ="title" class ="form-control form-group-lg input-lg hugefy">
                            <option value="Mr">Mr</option>
                            <option value="Ms">Ms</option>
                        </select><br/> 
                        <input type='submit' name='action' value='Add New Student' class="btn btn-primary btn-lg menu-btn"/>
                        <a href="${pageContext.request.contextPath}/import/main.jsp" class="btn btn-info btn-lg menu-btn">Back to Data Manager</a>
                    </div>
                </div>
            </form>
        </div>
    </section>
    <%@ include file="/footer.jsp" %>
</html>
