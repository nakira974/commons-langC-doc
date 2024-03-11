<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 12/11/2023
  Time: 20:25
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="lkhsoft" uri="/WEB-INF/taglib.tld" %>

<lkhsoft:mainLayout pageName="Erreur Serveur - Reno-System" viewName="404-error-view">
    <div class="text-center mt-5">
        <div class="error mx-auto" data-text="404">
            <p class="m-0">404 🙄</p>
        </div>
        <p class="text-dark mb-5 lead" style="margin-top: 3rem;">Page not found</p>
        <p class="text-black-50 mb-0">Like you want to escape the matrix...</p><a
            href="${pageContext.request.contextPath}/">← Return on main page</a>
    </div>
</lkhsoft:mainLayout>