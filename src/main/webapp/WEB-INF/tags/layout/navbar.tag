<%@ tag import="java.security.Principal" %>
<%@ tag import="org.wildfly.security.http.oidc.OidcPrincipal" %>
<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 10/12/2023
  Time: 1:37 pm
--%>
<%@ tag description="Application navbar tag component" pageEncoding="UTF-8" %>

<%
    String username;
    final Principal userPrincipal = request.getUserPrincipal();
    if (userPrincipal instanceof OidcPrincipal) {
        username = ((OidcPrincipal<?>) userPrincipal).getOidcSecurityContext().getIDToken().getEmail();
        request.setAttribute("username", username);
    } else {
        throw new ServletException(String.format("could not cast %s to %s", Principal.class, OidcPrincipal.class));
    }
    final String requestParam = request.getParameter("action");
    if (requestParam != null && requestParam.equals("logout")) {
        request.logout();
    }
%>

<!-- Start: Navbar Centered Links -->
<nav class="navbar navbar-expand bg-white shadow mb-4 topbar static-top navbar-light" id="navbar">
    <div class="container-fluid">
        <button class="btn btn-link d-md-none rounded-circle me-3" id="sidebarToggleTop" type="button"><i
                class="fas fa-bars"></i></button>
        <ul class="navbar-nav flex-nowrap ms-auto">
            <li class="nav-item dropdown d-sm-none no-arrow"><a class="dropdown-toggle nav-link" aria-expanded="false"
                                                                data-bs-toggle="dropdown" href="#"><i
                    class="fas fa-search"></i></a>
                <div class="dropdown-menu dropdown-menu-end p-3 animated--grow-in" aria-labelledby="searchDropdown">
                    <form id="navbar_searchBarForm" class="me-auto navbar-search w-100">
                        <div class="input-group"><input class="bg-light form-control border-0 small" type="text"
                                                        placeholder="Search for ...">
                            <div class="input-group-append">
                                <button class="btn btn-primary py-0" type="button"><i class="fas fa-search"></i>
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </li>
            <li class="nav-item dropdown no-arrow mx-1"></li>
            <li class="nav-item dropdown no-arrow mx-1">
                <div class="shadow dropdown-list dropdown-menu dropdown-menu-end"
                     aria-labelledby="alertsDropdown"></div>
            </li>
            <div class="d-none d-sm-block topbar-divider"></div>
            <li class="nav-item dropdown no-arrow">
                <div id="userNavItem" class="nav-item dropdown no-arrow"><a class="dropdown-toggle nav-link"
                                                                            aria-expanded="false"
                                                                            data-bs-toggle="dropdown" href="#"><span
                        class="d-none d-lg-inline me-2 text-gray-600 small"><%= username %></span>
                    <img
                            id="userAvatar"
                            class="border rounded-circle img-profile"
                            src="${pageContext.request.contextPath}/image?name=avatars/avatar5.jpeg" alt=""></a>

                    <div id="userNavItem_dropDownMenu" class="dropdown-menu shadow dropdown-menu-end animated--grow-in">
                        <!--
                        <div class="dropdown-divider"></div>
                        -->
                        <a id="logoutAnchor" class="dropdown-item" href="#"
                           onclick="logout('${pageContext.request.contextPath}?action=logout','${pageContext.request.contextPath}');"><i
                                class="fas fa-sign-out-alt fa-sm fa-fw me-2 text-gray-400"></i>&nbsp;Logout</a>
                    </div>
                </div>
            </li>
        </ul>
    </div>
</nav>
<!-- End: Navbar Centered Links -->