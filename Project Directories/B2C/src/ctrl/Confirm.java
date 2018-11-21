package ctrl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.CartBean;
import beans.PersonBean;
import model.Engine;

/**
 * Servlet implementation class Confirm
 */
@WebServlet("/Confirm")
public class Confirm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Confirm() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		try {
			Engine brain = Engine.getInstance();
			HttpSession session = request.getSession();
			
			CartBean cart = (CartBean) session.getAttribute("cart");
			
			
			/*
			 * if the Auth server redirects the client back, they have 
			 * authenticated. create a person object and store it in the
			 * session
			 */
			String user = request.getParameter("user");
			String name = request.getParameter("name");
			String hash = request.getParameter("hash");

			
			if(user != null && session.getAttribute("person") == null) {
				//The user is now logged in
				PersonBean person = brain.doAccount(user, name, hash);
				session.setAttribute("person", person);
			}
			
			
			//client has not logged in yet. send them to authentication server
			if(session.getAttribute("person") == null) {
				
//				response.sendRedirect("Account");
//				return;
				
				
				String l = "http://localhost:4413/Auth/OAuth.do?back="+request.getRequestURL().toString();
				response.sendRedirect(l);
				return;
			}
				
							
			
			cart = brain.doConfirm(cart);
			session.setAttribute("cart", cart);
			
			//redirect the user to their account
			response.sendRedirect("Account");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
