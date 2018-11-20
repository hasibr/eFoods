package ctrl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.CartBean;
import model.Engine;

/**
 * Servlet implementation class Checkout
 */
@WebServlet("/Checkout")
public class Checkout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Checkout() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			
		HttpSession session = request.getSession();
		CartBean cart = (CartBean) session.getAttribute("cart");
		
		/*
		 * the cart is empty so there's no reason to show the checkout page
		 */
		if(cart.getItems().isEmpty()) {
			response.sendRedirect("Cart");
			return;
		}
		
		/*
		 * the client canceled their order
		 */
		if(request.getParameter("calc")!= null &&
				request.getParameter("calc").equalsIgnoreCase("Cancel Order")) {
			
			try {
				
				Engine e = Engine.getInstance();
				cart = e.emptyCart(cart);
				session.setAttribute("cart", cart);
				
				response.sendRedirect("Cart");
				return;
			}
			catch(Exception e) {
				request.setAttribute("error", e.getMessage());
			}
			
		}
		
		/*
		 * the client confirmed their order
		 */
		else if(request.getParameter("calc")!= null &&
				request.getParameter("calc").equalsIgnoreCase("Confirm Order")) {
			
			try {
				
				Engine e = Engine.getInstance();
				
				e.confirmOrder(cart);
				
				cart = e.emptyCart(cart);
				session.setAttribute("cart", cart);
				
				response.sendRedirect("Account");
				return;
			}
			catch(Exception e) {
				request.setAttribute("error", e.getMessage());
			}
			
		}
		else {
			updateSummary(request, cart);
		}
		
		
		
		request.getServletContext().getRequestDispatcher("/Checkout.jspx").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	
	/*
	 * makes the above method more readable
	 */
	private void updateSummary(HttpServletRequest request, CartBean cart) {
		
		try {
			Engine brain = Engine.getInstance();
				
			request.setAttribute("cart", cart);	
			request.setAttribute("hst", brain.calcHst(cart.getSubTotal()));
				
			if(brain.over100(cart.getSubTotal())) {
				request.setAttribute("shipping", "$0.00");
				request.setAttribute("total", brain.addTax(cart.getSubTotal()));
			}
			else {
				request.setAttribute("shipping", "$5.00");
				request.setAttribute("total", brain.addTaxAndShipping(cart.getSubTotal()));
			}
			
			
		}
		catch(Exception e) {
			request.setAttribute("error", e.getMessage());
		}
	}

}
