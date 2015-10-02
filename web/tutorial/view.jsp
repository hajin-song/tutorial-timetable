<%-- 
    Document   : view
    Created on : 2015. 2. 4, 오후 9:42:04
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
            <h1>List of Timestream</h1>
        </header>
    </div>
    <div class="error row well">
        <c:forEach var="error" items="${requestScope.errors}">
            <p><c:out value="${error}"></c:out></p>
        </c:forEach>  
    </div>
    <div class="col-xs-12">
        <section class="col-xs-7">
            <div class="section-header">
                <h2>Existing Timestreams</h2>
            </div>
            <div class="table-striped">
                <table class = "table-striped">
                    <tr class="table-header">
                        <th class="col-xs-2">Stream Day</th>
                        <th class="col-xs-2">Stream Start Time</th>
                        <th class="col-xs-2">Stream End Time</th>
                        <th class="col-xs-1"></th>
                    </tr>
                    <c:forEach var="stream" items="${requestScope.result}">
                        <tr class="table-row">
                            <form action='${pageContext.request.contextPath}/TutorialHandler' method='POST'>
                                <td class="col-xs-1"><input type='text' name='stream_day' value = <c:out value="${stream.timestreamPK.streamDay}"></c:out> readonly='readonly' /></td>
                                <td class="col-xs-1"><input type='text' name='stream_start' value = <c:out value="${stream.timestreamPK.getStreamTimeStart()}"></c:out> readonly='readonly' /></td>
                                <td class="col-xs-1"><input type='text' name='stream_end' value = <c:out value="${stream.timestreamPK.getStreamTimeEnd()}"></c:out> readonly='readonly' /></td>
                                <td class="col-xs-1"><input type='submit' name='action' value='Remove Timestream' onClick="return confirm('Are you sure you?');" class="btn btn-warning btn-lg"/></td>
                            </form>
                        </tr>
                    </c:forEach>        
                </table>
            </div>
        </section>
        <aside class="col-xs-4 col-xs-offset-1">
            <div class="section-header">
                <h2>Add New Timestream</h2>
            </div>
            <form action='${pageContext.request.contextPath}/TutorialHandler' method='POST'>
                <div class="form-group">
                    <div class="col-xs-3">
                        <div class="row hugefy">
                            <label for="new_day">Day</label>    
                            <input type='text' name='new_day' id ='new_day' class="form-control input-lg"/>
                        </div>
                        <div class="row hugefy">
                            <label for="new_start">Start Time</label>    
                            <input type='text' name='new_start' id ='new_start'class="form-control input-lg"/>
                        </div>
                        <div class="row hugefy">
                            <label for="new_end">End Time</label>    
                            <input type='text' name='new_end' id='new_end' class="form-control input-lg"/><br/>
                        </div>
                        <input type='submit' name='action' value='Add Stream' class="btn btn-default btn-primary btn-lg"/>
                    </div>
                </div>

            </form>
        </aside>

            

        
        
    </div>
    
    

    <%@ include file="/footer.jsp" %>
</html>