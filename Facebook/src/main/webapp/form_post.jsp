<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Cadastro de Post</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-5">
    <h1 class="mb-4">
        <c:choose>
            <c:when test="${post != null}">Editar Post</c:when>
            <c:otherwise>Novo Post</c:otherwise>
        </c:choose>
    </h1>

    <form action="/facebook/post/save" method="POST">
        <c:if test="${post != null}">
            <input type="hidden" name="post_id" value="${post.id}">
        </c:if>

        <div class="form-group">
            <label>Conteúdo</label>
            <textarea name="content" class="form-control" required>${post != null ? post.content : ''}</textarea>
        </div>

        <div class="form-group">
            <label>ID do Usuário</label>
            <input type="text" name="user_id" class="form-control" value="${post != null ? post.user.id : ''}" required>
        </div>

        <button type="submit" class="btn btn-success">Salvar</button>
        <a href="${pageContext.request.contextPath}/posts" class="btn btn-secondary">Cancelar</a>
    </form>

</div>

</body>
</html>
