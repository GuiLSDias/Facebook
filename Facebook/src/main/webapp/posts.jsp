<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Posts</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-icons.css">
</head>
<body>

<div class="container mt-5">
	<a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-secondary"><i class="bi bi-house"></i></a>
    <h1 class="mb-4">Posts</h1>

    <a href="form_post.jsp" class="btn btn-primary mb-3">Novo Post</a>

    <table class="table table-bordered">
        <thead class="thead-dark">
            <tr>
                <th>ID</th>
                <th>Conteúdo</th>
                <th>Data do Post</th>
                <th>Usuário</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="post" items="${posts}">
            <tr>
                <td>${post.id}</td>
                <td>${post.content}</td>
                <td>${post.postDate}</td>
                <td>${post.user.name}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/post/update?postId=${post.getId()}" class="btn btn-warning btn-sm">Editar</a>
                    <a href="${pageContext.request.contextPath}/post/delete?postId=${post.getId()}" class="btn btn-danger btn-sm"
                       onclick="return confirm('Tem certeza que deseja deletar este post?');">Excluir</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
		
</div>

</body>
</html>
