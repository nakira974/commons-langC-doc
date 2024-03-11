<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 11/01/2024
  Time: 5:26 pm
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="lkhsoft" uri="/WEB-INF/taglib.tld" %>

<lkhsoft:mainLayout pageName="Erreur Serveur - Reno-System" viewName="501-error-view">
    <div class="text-center mt-5">
        <div class="error mx-auto" data-text="501">
            <p class="m-0">501 😵</p>
        </div>
        <p class="text-dark mb-5 lead" style="margin-top: 3rem;">No implementation for this method on the server!</p>
        <p class="text-black-50 mb-0">Retry your search, otherwise :</p><a
            href="${pageContext.request.contextPath}/">← Go back to the main page</a>
    </div>
</lkhsoft:mainLayout>