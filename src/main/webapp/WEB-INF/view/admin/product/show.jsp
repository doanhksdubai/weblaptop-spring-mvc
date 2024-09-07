<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
            <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
                <!DOCTYPE html>
                <html lang="en">

                <head>
                    <meta charset="UTF-8" />
                    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                    <meta name="description" content="Hỏi Dân IT - Dự án laptopshop" />
                    <meta name="author" content="Hỏi Dân IT" />
                    <title>Dashboard - Hỏi Dân IT</title>
                    <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css"
                        rel="stylesheet" />
                    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js"
                        crossorigin="anonymous"></script>
                    <link href="/css/styles.css" rel="stylesheet" />
                </head>

                <body class="sb-nav-fixed">
                    <jsp:include page="../layout/header.jsp" />
                    <div id="layoutSidenav">
                        <jsp:include page="../layout/sidebar.jsp" />
                        <div id="layoutSidenav_content">
                            <main>
                                <div class="container-fluid px-4">
                                    <h1 class="mt-4">Manage Products</h1>
                                    <ol class="breadcrumb mb-4">
                                        <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
                                        <li class="breadcrumb-item active">Product</li>
                                    </ol>
                                    <div class="mt-5">
                                        <div class="row">
                                            <div class="col-md-12 col-12 mx-auto">
                                                <div class="d-flex justify-content-between">
                                                    <h3>Table products</h3>
                                                    <a class="btn btn-primary" href="/admin/product/create">Create a
                                                        product</a>
                                                </div>
                                                <hr>
                                                <table class="table table-bordered table-hover">
                                                    <tr>
                                                        <th>ID</th>
                                                        <th>Name</th>
                                                        <th>Price</th>
                                                        <th>Factory</th>
                                                        <th>Action</th>
                                                    </tr>
                                                    <c:forEach var="product" items="${products}">
                                                        <tr>
                                                            <td>${product.id}</td>
                                                            <td>${product.name}</td>
                                                            <td>
                                                                <fmt:formatNumber type="number"
                                                                    value="${product.price}" />đ
                                                            </td>
                                                            <td>${product.factory}</td>
                                                            <td>
                                                                <a class="btn btn-success"
                                                                    href="/admin/product/${product.id}">View</a>
                                                                <a class="btn btn-warning"
                                                                    href="/admin/product/update/${product.id}">Update</a>
                                                                <a class="btn btn-danger"
                                                                    href="/admin/product/delete/${product.id}">Delete</a>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </table>
                                                <nav aria-label="Page navigation example">
                                                    <ul class="pagination justify-content-center">
                                                        <li class="page-item">
                                                            <a class="${1 eq currentPage ? 'disabled page-link' : 'page-link'}"
                                                                href="?page=${currentPage - 1}" aria-label="Previous">
                                                                <span aria-hidden="true">&laquo;</span>
                                                            </a>
                                                        </li>
                                                        <c:forEach begin="0" end="${totalPage-1}" varStatus="loop">
                                                            <li class="page-item"><a
                                                                    class="${(loop.index+1) eq currentPage ? 'active page-link' : 'page-link'}"
                                                                    href="?page=${loop.index+1}">${loop.index+1}</a>
                                                            </li>
                                                        </c:forEach>
                                                        <li class="page-item">
                                                            <a class="${totalPage eq currentPage ? 'disabled page-link' : 'page-link'}"
                                                                href="?page=${currentPage + 1}" aria-label="Next">
                                                                <span aria-hidden="true">&raquo;</span>
                                                            </a>
                                                        </li>
                                                    </ul>
                                                </nav>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </main>
                            <jsp:include page="../layout/footer.jsp" />
                        </div>
                    </div>
                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                        crossorigin="anonymous"></script>
                    <script src="/js/scripts.js"></script>
                </body>

                </html>