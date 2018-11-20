package ctrl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.CartBean;
import model.Engine;
import model.ItemBean;

/**
 * Servlet implementation class Cart
 */
@WebServlet("/Cart")
public class Cart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Cart() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		if(request.getParameter("update") != null) { // update the cart
			
			CartBean c = (CartBean) request.getSession().getAttribute("cart");
			
			if(c == null || c.getItems().isEmpty()) {
				showTheCart(request, response);
			}else {
				updateCart(request, response);
			}
			
			
		}
		else if(request.getParameter("add") == null) { // show the cart if nothing was added
			
			showTheCart(request, response);
		}
		else if(request.getParameter("add") != null) { // add new item to the cart and display it
			
			showTheCartWithItem(request, response);
			
		}
		request.getServletContext().getRequestDispatcher("/Cart.jspx").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	
	//--------------------helpers for readability ----------------------------
	
	/**
	 * shows the item(s) in the client's cart.
	 * @param request
	 * @param response
	 */
	private void showTheCart(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			
			HttpSession session = request.getSession();
			
			// create new empty cart if this is the first visit
			if(session.getAttribute("cart") == null) {
				// total value of the cart is $0.00 cause it's empty
				session.setAttribute("cart",
						new CartBean(new HashMap<String,ItemBean>(), "$0.00"));
			}
			
			// get the empty cart
			CartBean cart = (CartBean) session.getAttribute("cart");
			
			//show the empty cart on the page
			request.setAttribute("cart", cart);
			request.setAttribute("hst", "$0.00");
			request.setAttribute("shipping", "$0.00");
			request.setAttribute("total", "$0.00");
		}
		catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
		}
		
	}
	
	
	/**
	 * adds the item(s) to the client's cart and shows them on the client's page. stores the cart in the 
	 * client's session.
	 * @param request
	 * @param response
	 */
	private void showTheCartWithItem(HttpServletRequest request, HttpServletResponse response) {
		try {
			Engine brain = Engine.getInstance();
			
			HttpSession session = request.getSession();
			
			if(session.getAttribute("cart") == null) {// create new empty cart
				session.setAttribute("cart",
						new CartBean(new HashMap<String,ItemBean>(), "$0.00"));
			}
			
			// get the client's cart
			CartBean cart = (CartBean) session.getAttribute("cart");
			
			
			// collect the data from the client's request.
			String name = request.getParameter("name");
			String id = request.getParameter("id");
			String price = request.getParameter("price");
			String qty = request.getParameter("qty");
			
			// create new item bean with that data
			ItemBean item = new ItemBean(id, name, price, qty);
					
			//add the item to the cart and store it in the client's session
			cart = brain.addToCart(cart, item);
			session.setAttribute("cart", cart);
			
			// show the cart on the client's page.
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
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
		}
	}
	
	
	private void updateCart(HttpServletRequest request, HttpServletResponse response) {
		
		
		try {
			Engine brain = Engine.getInstance();
			HttpSession session = request.getSession();
			
			
			// get the client's cart
			CartBean cart = (CartBean) session.getAttribute("cart");
			
			/*
			 * get the items the parameters from the url. these are the items that
			 * are to be updated.
			 * 
			 *  the key is the product number
			 *  
			 *  the value is the number qty the item should be updated to
			 */
			Map<String, String[]> parameters = request.getParameterMap();
			
			
			//update the items in the cart and store it in the client's session
			cart = brain.updateCart(cart, parameters);
			session.setAttribute("cart", cart);
			
			// show the cart on the client's page.
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
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
		}
	
	}

}
