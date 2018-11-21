package ctrl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.PersonBean;
import model.Engine;

/**
 * Servlet implementation class Account
 */
@WebServlet("/Account")
public class Account extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Account() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//----------------------------------------------------------------------
		request.setAttribute("back", request.getRequestURL().toString());
		HttpSession session = request.getSession();
		//----------------------------------------------------------------------
		
		//log the user out if logout button was pressed.
		if (request.getParameter("logout") != null && request.getParameter("logout").equals("true")) {
			session.removeAttribute("person");
			response.sendRedirect("Categories?show=1");
			return;
		}
		
		String user = request.getParameter("user");
		String name = request.getParameter("name");
		String hash = request.getParameter("hash");
		
		String from = request.getParameter("from");
		
		try {
			Engine brain = Engine.getInstance();
			PersonBean person = brain.doAccount(user, name, hash);
			
			//log the user in
			if(user != null && session.getAttribute("person") == null) {
							
				session.setAttribute("person", person);
			}
			
			/*
			 * if the user was sent from Checkout page to log in,
			 * send them back to the Checkout page after they have
			 * authenticated.
			 */
//			if(from != null && from.equals("Checkout")) {
////				session.setAttribute("person", person);
//				System.out.println(person.toString());
//				response.sendRedirect(from);
//				return;
//			}
		}
		catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
		}
		
		
		request.setAttribute("person", (PersonBean) session.getAttribute("person"));
		
		
		request.getServletContext().getRequestDispatcher("/Account.jspx").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
