<%-- 
    Document   : addSubject
    Created on : 2015. 3. 27, 오후 9:49:17
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
                <h1>Manual Subject Import</h1>
            </header>
        </div>   
        <div class="error well">
            <c:forEach var="error" items="${requestScope.errors}">
                <p><c:out value="${error}"></c:out></p>
            </c:forEach>  
        </div>
        <div class="col-xs-2">
            <form method="POST" action="${pageContext.request.contextPath}/ImportHandler" >
                <label for="code" class="detail-view">Subject Code</label>    
                <input type="text" name="code" id ='code' class="form-control detail-view input-lg"/>
                <label for="name" class="detail-view">Subject Name</label>    
                <input type="text" name="name" id ='name' class="form-control detail-view input-lg"/>
                <input type='submit' name='action' value='Add New Subject' class="btn btn-primary btn-lg menu-btn"/>
                <a href="${pageContext.request.contextPath}/import/main.jsp" class="btn btn-info btn-lg menu-btn">Back to Data Manager</a>
            </form>
        </div>
    </section>
    <%@ include file="/footer.jsp" %>
</html>
