package ctrl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Customer;
import beans.PO;
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
		//---------------------------------------------------------------------
		HttpSession session = request.getSession();
		//----------------------------------------------------------------------
		
		//log the user out if logout button was pressed.
		if (request.getParameter("logout") != null && request.getParameter("logout").equals("true")) {
			session.removeAttribute("person");
			response.sendRedirect("Categories?show=1");
			return;
		}
		
		// log in button was pressed. redirect to Auth server
		if(request.getParameter("login")!=null) {
			String servName = request.getServerName();
			
			String l = "http://"+servName+":4413/Auth/OAuth.do?back="+request.getRequestURL().toString();
			response.sendRedirect(l);
			return;
		}
		
		
		/*
		 * if the Auth server redirects the client back, they have 
		 * authenticated. create a person object and store it in the
		 * session
		 */
		String user = request.getParameter("user");
		String name = request.getParameter("name");
		String hash = request.getParameter("hash");
		try {
			Engine brain = Engine.getInstance();
			
			
			//Creates a customer and stores it in session. signifies the customer has logged in. 
			if(user != null && session.getAttribute("person") == null) {
				//The user is now logged in
				Customer person = new Customer(user, name, hash);
				session.setAttribute("person", person);
			}
			
			// the user is logged in so show them their POs
			if (session.getAttribute("person") != null) {
				
				Customer person = (Customer) session.getAttribute("person");
				request.setAttribute("POs", brain.doAccount(person.getAccount()));

			}
			// show the PO the customer has clicked
			if(request.getParameter("view") != null) {
				
				
				@SuppressWarnings("unchecked")
				List<PO> p = (List<PO>) request.getAttribute("POs");
				String s = request.getParameter("view");
				
				PO po = brain.findPO(p, s);
				request.setAttribute("display", po);
//				request.setAttribute("items", po.getItems());
				
			}
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
		}
		
		
		request.setAttribute("person", (Customer) session.getAttribute("person"));
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
