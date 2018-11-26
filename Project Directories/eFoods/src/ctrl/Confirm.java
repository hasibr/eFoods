package ctrl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.ShoppingCart;
import beans.Customer;
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
			
			ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
			
			
			/*
			 * if the Auth server redirects the client back, they have 
			 * authenticated. create a person object and store it in the
			 * session
			 */
			String user = request.getParameter("user");
			String name = request.getParameter("name");
			String hash = request.getParameter("hash");

			/*
			 * if this parameter "user" is not null, that means the authentication server
			 * redirected the user back with a username, name and hash.
			 * 
			 * NOTE: because we aren't using the hash, we are not verifying that the user
			 * is actually who he or she is saying they are.
			 * 
			 * - Someone can easily just put nonsense in the parameter for user or name or hash
			 * and gain access to the service under a nonsense credential.
			 * 
			 * - We are only trusting that they
			 * were redirected by the authentication server.
			 */
			if(hash != null && session.getAttribute("person") == null) {
				
				//The user is now logged in
				Customer person = brain.getNewCustomer(user, name, hash);
				session.setAttribute("person", person);
			}
			
			
			//client has not logged in yet. send them to authentication server
			if(session.getAttribute("person") == null) {
				String servName = request.getServerName();
				
				String l = "http://"+servName+":4413/Auth/OAuth.do?back="+request.getRequestURL().toString();
				response.sendRedirect(l);
				return;
			}
				
			
			/*
			 * confirm the order
			 */
			Customer cus = (Customer) session.getAttribute("person");
			cart = brain.doConfirm(cart, cus);
			session.setAttribute("cart", cart);
			
			
			// show them a confirmation page with a link to their account
			request.getServletContext().getRequestDispatcher("/ConfirmationPage.jspx").forward(request, response);
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
