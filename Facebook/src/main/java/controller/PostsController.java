package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelException;
import model.Post;
import model.User;
import model.dao.DAOFactory;
import model.dao.PostDAO;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/posts", "/post/save", "/post/update", "/post/delete"})
public class PostsController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {

        String action = req.getRequestURI();

        switch (action) {
            case "/facebook/posts": {
                loadPosts(req);
                RequestDispatcher rd = req.getRequestDispatcher("posts.jsp");
                rd.forward(req, resp);
                break;
            }
            case "/facebook/post/update": {
                loadPost(req);
                RequestDispatcher rd = req.getRequestDispatcher("/form_post.jsp");
                rd.forward(req, resp);
                break;
            }
            case "/facebook/post/delete": {
                deletePost(req);
                resp.sendRedirect("/facebook/posts");
                break;
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + action);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {

        String action = req.getRequestURI();

        if ("/facebook/post/save".equals(action)) {
            String postId = req.getParameter("post_id");
            String userId = req.getParameter("user_id");

            if (postId != null && !postId.equals("") && userId != null && !userId.equals("")) {
                updatePost(req);
            } else {
                insertPost(req);
            }

            resp.sendRedirect("/facebook/posts");
        }
    }

    private void deletePost(HttpServletRequest req) {
        String postIdString = req.getParameter("postId");
        int postId = Integer.parseInt(postIdString);
        
        Post post = new Post(postId);
        
        PostDAO dao = DAOFactory.createDAO(PostDAO.class);
        
        try {
            dao.delete(post);
        } catch (ModelException e) {
            e.printStackTrace();
        }
    }

    private void updatePost(HttpServletRequest req) {
        Post post = createPost(req);
        PostDAO dao = DAOFactory.createDAO(PostDAO.class);

        try {
            dao.update(post);
        } catch (ModelException e) {
            e.printStackTrace();
        }
    }

    private Post createPost(HttpServletRequest req) {
        String postId = req.getParameter("post_id");
        String content = req.getParameter("content");
        String userId = req.getParameter("user_id");

        Post post;
        if (postId == null || postId.equals(""))
            post = new Post();
        else 
            post = new Post(Integer.parseInt(postId));
        
        post.setContent(content);
        if(userId != null && !userId.isEmpty()) {
            int userInt = Integer.parseInt(userId);
            User user = new User(userInt);
            post.setUser(user);
        }
        return post;
    }

    private void loadPost(HttpServletRequest req) {
        String postIdParameter = req.getParameter("postId");

        int postId = Integer.parseInt(postIdParameter);

        PostDAO dao = DAOFactory.createDAO(PostDAO.class);

        try {
            Post post = dao.findById(postId);

            if (post == null)
                throw new ModelException("Post não encontrado para alteração");
            
            req.setAttribute("post", post);
        } catch (ModelException e) {
            e.printStackTrace();
        }
    }

    private void insertPost(HttpServletRequest req) {
        Post post = createPost(req);

        PostDAO dao = DAOFactory.createDAO(PostDAO.class);

        try {
            dao.save(post);
        } catch (ModelException e) {
            e.printStackTrace();
        }
    }

    private void loadPosts(HttpServletRequest req) {
        PostDAO dao = DAOFactory.createDAO(PostDAO.class);

        List<Post> posts = null;
        try {
            posts = dao.listAll();
        } catch (ModelException e) {
            e.printStackTrace();
        }

        if (posts != null)
            req.setAttribute("posts", posts);
    }
}
