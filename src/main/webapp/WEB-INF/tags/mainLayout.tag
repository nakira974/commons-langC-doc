<%@ tag import="coffee.lkh.editor.EditorApplication" %>
<%@ tag import="java.io.InputStream" %>
<%@ tag import="java.nio.file.Path" %>
<%@ tag import="java.nio.file.Files" %>
<%@ tag import="java.io.IOException" %>
<%@ tag import="java.nio.file.StandardCopyOption" %>
<%@ tag import="java.util.Base64" %><%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 10/12/2023
  Time: 1:37 pm
--%>

<%@ tag description="Application layout tag component" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="lkhsoft" uri="/WEB-INF/taglib.tld" %>

<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageName" required="true" type="java.lang.String" %>
<%@ attribute name="viewName" required="true" type="java.lang.String" %>

<html data-bs-theme="light" lang="fr">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>${pageName}</title>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.12.0/css/all.css">
    <link rel="icon" type="image/png" href="data:image/png;base64,${faviconBase64Image}">
    <style>
        <%@include file="/assets/css/Nunito.css" %>
    </style>
    <style>
        <%@include file="/assets/css/styles.css" %>
    </style>
    <style>
        <%@include file="/assets/bootstrap/css/bootstrap.min.css" %>
    </style>
</head>
<body id="page-top">

<div id="wrapper">
    <lkhsoft:sidebar/>
    <div class="d-flex flex-column" id="content-wrapper">
        <div id="content">
            <lkhsoft:navbar/>
            <div class="container-fluid jsp-view" id="${viewName}">
                <jsp:doBody/>
            </div>
        </div>
        <lkhsoft:footer/>
    </div>
    <a class="border rounded d-inline scroll-to-top" href="#page-top"><i class="fas fa-angle-up"></i></a>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.bundle.min.js"></script>
<!--
<script src="${pageContext.request.contextPath}/ts/bs-init.js"></script>
<script src="${pageContext.request.contextPath}/ts/theme.js"></script>
<script src="${pageContext.request.contextPath}/ts/dist/bundle.js"></script>
-->
</body>
</html>

