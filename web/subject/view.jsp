<%-- 
    Document   : view
    Created on : 2015. 2. 2, 오전 1:31:09
    Author     : Hajin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <%@ include file="/header.jsp" %>
    <div class='row'>
        <header class="page-header col-xs-12">
            <h1>List of Subject</h1>
        </header>
    </div>
    <div class="error row well">
        <c:forEach var="error" items="${requestScope.errors}">
            <p><c:out value="${error}"></c:out></p>
        </c:forEach>  
    </div>
    <div class="col-xs-12">
        <div class="row">
            <div class="col-xs-8 table-responsive">
                <table class = "table-striped">
                    <tr class="table-header">
                        <th class="col-xs-2">Subject Code</th>
                        <th class="col-xs-2">Subject Name</th>
                        <th class ="col-xs-2">Subject Size</th>
                        <th class="col-xs-2">Action</th>
                    </tr>
                    <c:forEach var="subject" items="${requestScope.result}">
                        <tr class="table-row">
                            <form action='${pageContext.request.contextPath}/SubjectHandler' method='POST'>
                                <td class="col-xs-2"><input size="8" type='text' name='subjectcode' value = <c:out value="${subject.subjectCode}"></c:out> readonly='readonly' /></td>
                                <td class="col-xs-2"><c:out value="${subject.subjectName}"></c:out></td>
                                <td class="col-xs-2"><c:out value="${subject.size}"></c:out></td>
                                <td class="col-xs-2"><input type='submit' name='action' value='View Detail' class="btn btn-lg btn-info"/></td>
                            </form>
                        </tr>

                    </c:forEach>        
                </table>
            </div>
            <aside class="col-xs-3 well col-xs-offset-1">
                <header class="section-header">
                    <h2>Filter Out Subjects</h2>
                </header>
                <form action='${pageContext.request.contextPath}/SubjectHandler' method='POST'>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-xs-7">
                                <select name = "semester" id ="semester" class ="form-control selectClass input-lg">
                                    <option value="1">Semester One</option>
                                    <option value="2">Semester Two</option>
                                </select>
                            </div>
                        </div>
                        <div class="checkbox">
                            <label class="hugefy-no-margin"><input type="checkbox" name="level" value="1" class ="checkbox checkboxl">Level One</input></label><br/>
                            <label class="hugefy"><input type="checkbox" name="level" value="2" class ="checkbox checkboxl">Level Two</input></label><br/>
                            <label class="hugefy"><input type="checkbox" name="level" value="3" class ="checkbox checkboxl">Level Three</input></label><br/>
                            <label class="hugefy"><input type="checkbox" name="level" value="4" class ="checkbox checkboxl">Level Four</input></label><br/>
                            <label class="hugefy"><input type="checkbox" name="level" value="9" class ="checkbox checkboxl">Level Nine</input></label><br/>
                        </div>

                        <input type='submit' name='action' value='Filter' class="btn btn-info btn-lg"/>
                    </div>
                    
                </form>
            </aside>
        </div>
    </div>
    <%@ include file="/footer.jsp" %>
</html>