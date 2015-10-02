<%-- 
    Document   : import
    Created on : 2015. 2. 1, 오후 1:23:35
    Author     : Hajin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <%@ include file="/header.jsp" %>
    <section>
        <div class="row">
            <header class="page-header col-xs-12">
                <h1>Import Data</h1>
            </header>
        </div>  
        <div class="col-xs-4">
            <form method="POST" action="${pageContext.request.contextPath}/ImportHandler" enctype="multipart/form-data">
                <input type="file" name="filetoupload" class="form-control form-group-lg input-lg"/>
                <input type="submit" name='action' value="Upload File" class="btn btn-primary btn-lg menu-btn"/>
            </form>
            <a href="${pageContext.request.contextPath}/import/main.jsp" class="btn btn-info btn-lg menu-btn">Back to Data Manager</a>
        </div>
    </section>
    <%@ include file="/footer.jsp" %>
</html>
