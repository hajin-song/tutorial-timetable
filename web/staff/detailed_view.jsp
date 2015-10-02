<%-- 
    Document   : detailed_view
    Created on : 2015. 2. 3, 오후 5:06:13
    Author     : Hajin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <%@ include file="/header.jsp" %>
    <section>
        <div class="row">
            <header class="page-header col-xs-12">
                <h1>Tutor Details</h1>
            </header>
        </div>
        <div class="error well lead">
            <c:forEach var="error" items="${requestScope.errors}">
                <p><c:out value="${error}"></c:out></p>
            </c:forEach>  
        </div>   

        <c:set var="staff" value="${staff}" scope="request"/>
        <div class="row">
            <div class="row">
                <div class="col-xs-2">
                    <div class="section-header">
                        <h2>Tutor Info</h3>
                    </div>
                    <label class="detail-view">Staff ID </label>
                    <label class="form-control detail-view input-lg">${staff.id}</label>
                    <label class="detail-view">Staff Name: </label>
                    <label class="form-control detail-view input-lg">${staff.getName()}</label>
                    <a href="${pageContext.request.contextPath}/StaffHandler" class="btn btn-info btn-lg menu-btn">Back to Tutor List</a>
                </div>

                <section class="col-xs-6 table-responsive col-xs-offset-2">
                    <div class="section-header">
                        <h2>Availability</h3>
                    </div>
                        <table class = "table-striped">
                            <tr class="table-header">
                                <th class="col-xs-2">Availability</th>
                                <th class="col-xs-1">Action</th>
                            </tr>
                            <c:forEach var="preference" items="${requestScope.preference}">
                                <tr class="table-row">
                                    <td class="col-xs-2">${preference.toString()}</td>
                                    <form action='${pageContext.request.contextPath}/StaffHandler' method='POST'>
                                        <input type='hidden' name='prefID' value = "${preference.id}"/>
                                        <input type='hidden' name='staffid' value = "${staff.id}"/>
                                        <td class="col-xs-1"><input type='submit' name='action' value='Remove Availability' onClick="return confirm('Are you sure you?');" class="btn btn-warning btn-lg"/></td>
                                    </form>
                                </tr>
                            </c:forEach>
                        </table><br/>
            </div>
            <div class="well table-responsive col-xs-6 col-xs-offset-4">
                <div class="section-header">
                    <h2>Add Availability</h3>
                </div>

                <form action='${pageContext.request.contextPath}/StaffHandler' method='POST'>
                        <input type="hidden" name="staffid" value="${staff.id}"></input>
                        <table class = "table-striped">
                            <tr class="table-header">
                                <th class="col-xs-3">Stream Name</th>
                                <th class="col-xs-1">Availability</th>
                            </tr>
                            <c:forEach var="stream" items="${requestScope.streams}">
                                <tr class="table-row">
                                    <td class="col-xs-3">${stream.toFormattedString()}</td>
                                    <td class="col-xs-1"><input type="checkbox" name= "add" class="big_check"value="${stream.toString()}" /></td>
                                </tr>
                            </c:forEach>
                        </table>
                        <br/>
                        <div class="col-xs-2">
                            <input type='submit' name='action' value='Edit Tutor' class="btn btn-primary btn-lg"/>
                        </div>
                </form>  
            </div>
        </div>
    </section>
    <%@ include file="/footer.jsp" %>
</html>
