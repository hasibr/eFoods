package ctrl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.CartBEAN;
import model.Engine;

/**
 * Servlet implementation class CheckOut
 */
@WebServlet("/CheckOut")
public class CheckOut extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckOut() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			
		HttpSession session = request.getSession();
		CartBEAN cart = (CartBEAN) session.getAttribute("cart");
			
		if(cart.getItems().isEmpty()) {
			response.sendRedirect("Cart");
		}
		else {
			
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
				
				request.getServletContext().getRequestDispatcher("/CheckOut.jspx").forward(request, response);
			}
			catch(Exception e) {
				request.setAttribute("error", e.getMessage());
			}
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
