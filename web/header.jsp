<%-- 
    Document   : header
    Created on : 2015. 2. 11, ?? 11:49:33
    Author     : Hajin
--%>


    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css"  href="${pageContext.request.contextPath}/resources/css/bootstrap.css"  media="screen">
        <link rel="stylesheet" type="text/css"  href="${pageContext.request.contextPath}/resources/css/style.css"  media="screen">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
        <title>Whitley Application</title>
    </head>
    
    <body>
        <nav class="navbar navbar-default navbar-fixed-top container-fluid">
            <div class="row">
                <div class="nav-header">
                    <header>
                        <a class="navbar-brand" href="${pageContext.request.contextPath}/main.jsp">Whitley Tutorial Application</a>
                    </header>
                    <button type="button" class="navbar-toggle toggle-nav btn-nav-toggle" data-toggle="collapse" data-target=".nav-container">
                        <span class="nav-toggler">Go To</span>
                    </button>
                </div>
                <div class="nav-container">
                    <ul class="nav navbar-nav navbar-right nav-pills collapse-nav">
                        <li><a href="${pageContext.request.contextPath}/import/main.jsp">Data Manager</a></li>
                        <li><a href="${pageContext.request.contextPath}/tutorial/main.jsp">Tutorial Manager</a></li>
                        <li><a href="${pageContext.request.contextPath}/student/main.jsp">Student Manager</a></li>
                        <li><a href ="${pageContext.request.contextPath}/subject/main.jsp">Subject Manager</a></li>
                        <li><a href="${pageContext.request.contextPath}/staff/main.jsp">Tutor Manager</a></li>
                        <li><a href="${pageContext.request.contextPath}/room/main.jsp">Room Manager</a></li>
                    </ul>
                 </div>
            </div>
        </nav>

        <div class="container-fluid main-body">