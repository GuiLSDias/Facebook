package filters;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebFilter("/*")
public class LoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest httpReq = (HttpServletRequest) req;
		HttpServletResponse httpRes = (HttpServletResponse) res;
		
		boolean userLogged = httpReq.getSession().getAttribute("usuario_logado") != null;
		
		String url = httpReq.getRequestURI();
		boolean isPublicPage = url.endsWith("login.jsp") || url.endsWith("login");
		
		if(userLogged || isPublicPage)
			chain.doFilter(req, res);
		else 
			httpRes.sendRedirect("/facebook/login.jsp");
	}
	
}
