<%-- 
    Document   : view
    Created on : 2015. 2. 3, 오후 5:42:52
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
                <h1>List of Room</h1>
            </header>
        </div>
        <div class="error well row">
            <c:forEach var="error" items="${requestScope.errors}">
                <p><c:out value="${error}"></c:out></p>
            </c:forEach>  
        </div>
        <div class="row">

                <div class="row">
                    <section class="col-xs-7 table-responsive">
                        <header class="page-header lead">
                            <h2>Existing Rooms</h2>
                        </header>
                        <table class = "table-striped">
                            <tr class="table-header">
                                <th class="col-xs-2">Room Name</th>
                                <th class="col-xs-2">Min Capacity</th>
                                <th class="col-xs-2">Max Capacity</th>
                                <th class="col-xs-1"></th>
                                <th class="col-xs-1"></th>
                            </tr>
                            <c:forEach var="room" items="${requestScope.result}">
                                <tr class="table-row">
                                    <form action='${pageContext.request.contextPath}/RoomHandler' method='POST'>
                                        <input type='hidden' name='roomid' value = <c:out value="${room.id}"></c:out> />
                                        <td class="col-xs-2"><c:out value="${room.roomName}"></c:out></td>
                                        <td class="col-xs-2"><c:out value="${room.minCapacity}"></c:out></td>
                                        <td class="col-xs-2"><c:out value="${room.maxCapacity}"></c:out></td>
                                        <td class="col-xs-1"><input class="btn btn-info btn-lg" type='submit' name='action' value='View Detail'/></td>
                                        <td class="col-xs-1"><input class="btn btn-warning btn-lg" type='submit' name='action' onClick="return confirm('Are you sure you?');" value='Remove Room'/></td>
                                    </form>
                                </tr>
                            </c:forEach>         
                        </table>

                    </section>
                    <aside  class="col-xs-4 col-xs-offset-1">
                        <header class="page-header">
                            <h2>Add new Room</h2>
                        </header>
                        <form action="${pageContext.request.contextPath}/RoomHandler" method = 'POST'>
                            <div class="form-group">
                                <div class="col-xs-6">
                                    <div class="row">
                                        <label for="name" class="detail-view">Room Name</label>    
                                        <input  type="text" name="name" id ='name' class="form-control input-lg"/>
                                    </div>
                                    <div class="row">
                                        <label for="minCapacity" class="detail-view">Minimum Capacity</label>    
                                        <input type="text" name="minCapacity" id ='minCapacity' class="form-control input-lg"/>
                                    </div>
                                    <div class="row">
                                        <label for="maxCapacity" class="detail-view">Maximum Capacity</label>    
                                        <input type="text" name="maxCapacity" id='maxCapacity' class="form-control input-lg"/>
                                    </div>
                                    <div class="row">
                                        <input type='submit' name='action' value='Add New Room' class="hugefy btn btn-primary btn-lg menu-btn"/>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </aside>
                </div>   
        </div>
    </section>
  
    <%@ include file="/footer.jsp" %>
</html>

