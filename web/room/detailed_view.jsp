<%-- 
    Document   : edit
    Created on : 2015. 2. 4, 오전 11:49:16
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
            <header class="page-header col-xs-12">
                <h1>Room Details</h1>
            </header>
        </div>
        <div class="error well">
            <c:forEach var="error" items="${requestScope.errors}">
                <p><c:out value="${error}"></c:out></p>
            </c:forEach>  
        </div>   
        <div class="col-xs-3">
            <form action='${pageContext.request.contextPath}/RoomHandler' method='POST'>
                <c:set var="room" value="${room}" scope="request"/>
                <input type='hidden' name='roomId' value = "${room.id}"/>
                <div class="row">
                    <label for="name" class="detail-view">Room Name</label>    
                    <input type='text' name='name' id ='name' class="form-control detail-view input-lg col-xs-1" value = <c:out value="${room.roomName}"></c:out> readonly='readonly' />
                </div>
                <div class="row">
                    <label for="minCapacity" class="detail-view">Minimum Capacity</label>    
                    <input type='text' name='minCapacity' id ='minCapacity' class="form-control detail-view input-lg col-xs-1" value = <c:out value="${room.minCapacity}"></c:out> />
                </div>
                <div class="row">
                    <label for="maxCapacity" class="detail-view">Maximum Capacity</label>    
                    <input type='text' name='maxCapacity' id ='maxCapacity' class="form-control detail-view input-lg col-xs-1" value = <c:out value="${room.maxCapacity}"></c:out> />
                </div>
                <div class="row">
                    <input type="submit" name='action' value="Edit Room" onClick="return confirm('Are you sure you?');" class="btn btn-warning btn-lg menu-btn"/>
                </div>
                <div class="row">
                    <a href="${pageContext.request.contextPath}/RoomHandler" class="btn btn-info btn-lg menu-btn">Back to Room List</a>
                </div>
            </form>
        </div>
    </section>
    <%@ include file="/footer.jsp" %>
</html>