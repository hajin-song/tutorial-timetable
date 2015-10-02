<%-- 
    Document   : index
    Created on : 2015. 1. 31, 오후 4:31:42
    Author     : Hajin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css"  href="${pageContext.request.contextPath}/resources/css/bootstrap.css"  media="screen">
        <title>Whitley Application</title>
    </head>
    <body>
        <main class="container">
            <header class="page-header lead well">
                <h1>Whitley Application</h1>
            </header>
            <section class="well">
                <h2>Please Log In using Admin Credentials</h2>
                <div>
                    <form method="POST" action='${pageContext.request.contextPath}/LoginHandler'>
                        <div class="row">
                            <div class="form-group col-xs-4">
                                <h3><label for="uid" class="label label-default">Username</label></h3>
                                <input type="text" class="form-control input-lg" id="uid" name="uid">
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group col-xs-4">
                                <h3><label for="pwd" class="label label-default">Password</label></h3>
                                <input type="password" class="form-control input-lg" id="pwd" name="pwd">
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group col-xs-4">
                                <input type="submit" name="action" value="login" class="btn btn-default btn-primary btn-lg"/>
                            </div>
                        </div>
                    </form>
                </div>
            </section>
        </main>
    </body>
</html>
