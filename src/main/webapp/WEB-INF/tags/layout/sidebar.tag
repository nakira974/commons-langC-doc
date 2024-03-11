<%--
  Created by IntelliJ IDEA.
  User: maxim
  Date: 10/12/2023
  Time: 1:37 pm
--%>
<%@ tag description="Application sidebar tag component" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar align-items-start sidebar sidebar-dark accordion bg-gradient-primary p-0 navbar-dark">
    <div class="container-fluid d-flex flex-column p-0"><a
            class="navbar-brand d-flex justify-content-center align-items-center sidebar-brand m-0" href="#">
        <div class="sidebar-brand-icon rotate-n-15">
            <img src="${pageContext.servletContext.contextPath}/image?name=reno-system-logo.png" alt="Logo"
                 class="navbar-logo">
        </div>
        <div class="sidebar-brand-text mx-3"><span>reno-system</span></div>
    </a>
        <hr class="sidebar-divider my-0">
        <ul class="navbar-nav text-light" id="accordionSidebar">
            <li class="nav-item"><a class="nav-link active" href="${pageContext.request.contextPath}/"><i
                    class="fas fa-tachometer-alt"></i><span>Dashboard</span></a></li>
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/lookup/settings"><i
                    class="fas fa-cog"></i><span>réglages généraux</span></a></li>
            <li class="nav-item"></li>
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/lookup/contacts"><i
                    class="fas fa-users"></i><span>contacts</span></a></li>
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/lookup/products/general"><i
                    class="fas fa-table"></i><span>gestion des produits</span></a></li>
            <li class="nav-item"></li>
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/lookup/products"><i
                    class="fas fa-table"></i><span>références produit</span></a></li>
            <li class="nav-item"></li>
            <li class="nav-item"><a class="nav-link"
                                    href="${pageContext.request.contextPath}/lookup/products/references"><i
                    class="fa fa-tags"></i><span>références chantier</span></a></li>
            <li class="nav-item"></li>
        </ul>
        <div class="text-center d-none d-md-inline">
            <button class="btn rounded-circle border-0" id="sidebarToggle" type="button"></button>
        </div>
    </div>
    <script>
        window.onload = function () {
            var navItems = document.querySelectorAll('.nav-link');
            var currentPath = window.location.pathname;

            navItems.forEach(function (navItem) {
                if (navItem.getAttribute('href') === currentPath) {
                    navItem.classList.add('active');
                } else {
                    navItem.classList.remove('active');
                }
            });
        }
    </script>
    <style>
        .navbar-logo {
            height: auto;
            max-height: 50px; /* You can adjust this value according to your design */
            width: auto;
            max-width: 100%; /* This will make sure the logo will not overflow the navbar */
        }

        /* Mobile devices */
        @media only screen and (max-width: 600px) {
            .navbar-logo {
                max-height: 30px;
            }
        }

        /* Tablets and small laptops */
        @media only screen and (min-width: 601px) and (max-width: 1024px) {
            .navbar-logo {
                max-height: 40px;
            }
        }

        /* Desktops and large screens */
        @media only screen and (min-width: 1025px) {
            .navbar-logo {
                max-height: 50px;
            }
        }
    </style>
</nav>