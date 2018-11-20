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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String user = request.getParameter("user");
		String name = request.getParameter("name");
		String hash = request.getParameter("hash");

		if (user != null) {
			Writer out = response.getWriter();
			response.setContentType("text/json");
			response.setHeader("Access-Control-Allow-Origin", "*");
			// response.setContentType("text/html");
			String jsonReply = String.format(
					"{\"status\": true, \"data\": { \"user\": \"%s\", \"name\": \"%s\", \"hash\": \"%s\" } }", user,
					name, hash);
			// String html = String.format("<html><body><p>%s</p></body></html>", reply);
			// out.write(html);
			out.write(jsonReply);

		} else {
			this.getServletContext().getRequestDispatcher("/OAuth.html").forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = request.getRequestURL().toString().trim();
		response.sendRedirect(EECS_AUTH + url);
	}

}
