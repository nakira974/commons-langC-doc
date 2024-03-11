<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 10/12/2023
  Time: 1:37 pm
--%>
<%@ tag description="Application footer tag component" pageEncoding="UTF-8" %>

<%
    java.util.Date currentDate = new java.util.Date();
    java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy");
    String formattedYear = dateFormat.format(currentDate);
%>

<footer class="bg-white sticky-footer">
    <div class="container my-auto">
        <div class="text-center my-auto copyright"><span>Copyright © Reno-System <%= formattedYear %></span></div>
    </div>
</footer>
