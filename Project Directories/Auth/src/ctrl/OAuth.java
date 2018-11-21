package ctrl;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class OAuth
 */
@WebServlet("/OAuth.do")
public class OAuth extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String EECS_AUTH = "https://www.eecs.yorku.ca/~roumani/servers/auth/oauth.cgi?back=";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OAuth() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String user = request.getParameter("user");
		String name = request.getParameter("name");
		String hash = request.getParameter("hash");
		
		if(user == null) {
			
			request.getSession().setAttribute("back", request.getParameter("back"));
			
			String here = request.getRequestURL().toString().trim();
			response.sendRedirect(EECS_AUTH + here);
		}
		
		if (user != null) {
			
			String back = (String) request.getSession().getAttribute("back");
			response.sendRedirect(back+"?user="+user+"&name="+name+"&hash="+hash);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
