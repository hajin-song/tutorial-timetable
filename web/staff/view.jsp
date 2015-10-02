<%-- 
    Document   : view
    Created on : 2015. 2. 3, 오후 4:16:19
    Author     : Hajin
--%>

<%@page import="org.whitley.object.entities.Staff"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <%@ include file="/header.jsp" %>
    <section>
        <div class='row'>
            <header class="page-header col-xs-12">
                <h1>List of Tutor</h1>
            </header>
        </div>
        <div class="error row well">
            <c:forEach var="error" items="${requestScope.errors}">
                <p><c:out value="${error}"></c:out></p>
            </c:forEach>  
        </div>
        
        <div class="col-xs-12">
            <div class="row">
                <section class="col-xs-6">
                    <header class="section-header">
                        <h2>Existing Tutors</h2>
                    </header>
                    <table class = "table-striped">
                        <tr class="table-header">
                           <th class="col-xs-3">Staff Name</th>
                           <th class="col-xs-1"></th>
                           <th class="col-xs-1"></th>
                        </tr>
                        <c:forEach var="staff" items="${requestScope.result}">
                            <tr class="table-row">
                                <form action='${pageContext.request.contextPath}/StaffHandler' method='POST'>
                                    <input type='hidden' name='staffid' value ="${staff.id}" />
                                    <td class="col-xs-3"><c:out value="${staff.getName()}"></c:out></td>
                                    <td class="col-xs-1"><input type='submit' name='action' value='View Detail' class="btn btn-info btn-lg"/></td>
                                    <td class="col-xs-1"><input type='submit' name='action' value='Remove Tutor' onClick="return confirm('Are you sure you?');" class="btn btn-danger btn-lg"/></td>
                                </form>
                            </tr>
                        </c:forEach>         
                    </table>

                </section>
                <aside class="col-xs-4 col-xs-offset-1">
                    <header class="section-header">
                        <h2>Add New Tutor</h2>
                    </header>
                    <form action="${pageContext.request.contextPath}/StaffHandler" method = 'POST'>
                        <div class="form-group">
                            <div class="col-xs-6">
                                <div class="row">
                                    <label for="fname" class="detail-view">Staff First Name</label>    
                                    <input type="text" name="fname" id ='fname' class="form-control input-lg"/>
                                </div>
                                <div class="row">
                                    <label for="mname" class="detail-view">Staff Middle Name</label>    
                                    <input type="text" name="mname" id ='mname'class="form-control input-lg"/>
                                </div>
                                <div class="row">
                                    <label for="lname" class="detail-view">Staff Last Name</label>    
                                    <input type="text" name="lname" id='lname' class="form-control input-lg"/>
                                </div>
                                <div class="row">
                                    <input type='submit' name='action' value='Add New Tutor' class="btn btn-primary btn-lg menu-btn"/>
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
