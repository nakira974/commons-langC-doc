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
        <p class="text-dark mb-5 lead" style="margin-top: 3rem;">Cette méthode n'a aucune implémentation sur le
            serveur</p>
        <p class="text-black-50 mb-0">Réésayer votre recherche, sinon tentez de :</p><a
            href="${pageContext.request.contextPath}/">← Retourner sur la page principale</a>
    </div>
</lkhsoft:mainLayout>