<%-- 
    Document   : main
    Created on : 2015. 2. 4, 오후 4:53:46
    Author     : Hajin
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <%@ include file="/header.jsp" %>
    
    <div class="row">
        <header class="page-header lead col-xs-12">
            <h1>Tutorial Manager</h1>
        </header>  
    </div>
    <nav class='menu nav navbar-nav col-xs-2'>
        <ul class="list-unstyled">
            <li class="menu-item"><a href="${pageContext.request.contextPath}/TutorialHandler" class="btn btn-default btn-primary btn-block"> View Timestreams </a></li>
            <li class="menu-item"><a href="${pageContext.request.contextPath}/tutorial/set_constraints.jsp" class="btn btn-default btn-primary btn-block"> Set Constraints </a></li>
            <li class="menu-item">
                <form action="${pageContext.request.contextPath}/TutorialHandler" method="POST">
                    <a href="javascript:;" onclick="parentNode.submit();" class="btn btn-default btn-primary btn-block">Edit Tutorials</a>
                    <input type="hidden" name="action" value="Edit Tutorials" />
                </form>
            </li>
            <li class="menu-item">
                <form action="${pageContext.request.contextPath}/TutorialHandler" method="POST">
                    <a href="javascript:;" onclick="parentNode.submit();" class="btn btn-default btn-primary btn-block">Generate Tutorial Timetable</a>
                    <input type="hidden" name="action" value="Preset Timetable"/>
                </form>
            </li>
            <li class="menu-item"><a href="${pageContext.request.contextPath}/main.jsp" class="btn btn-default btn-primary btn-block"> Back to Main </a></li>
        </ul>
    </nav>

    <%@ include file="/footer.jsp" %>
</html>
