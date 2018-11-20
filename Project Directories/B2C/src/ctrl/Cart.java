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

import beans.CartBean;
import beans.ItemBean;
import model.Engine;

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

		
		try {
			Engine brain= Engine.getInstance();
			HttpSession session = request.getSession();
			
			CartBean cart;
			if(session.getAttribute("cart") == null) {
				cart = new CartBean(new HashMap<String,ItemBean>(), "$0.00");
				session.setAttribute("cart", cart);
			}else {
				cart = (CartBean) session.getAttribute("cart");
			}
			
			
			// collect the data from the client's request.
			String name = request.getParameter("name");
			String id = request.getParameter("id");
			String price = request.getParameter("price");
			String qty = request.getParameter("qty");
			
			String add = request.getParameter("add");
			String update = request.getParameter("update");
			Map<String, String[]> parameters = request.getParameterMap();
						
			// create new item bean with that data
			if(id == null) {
				name = "N/A";
				id = "N/A";
				price = "$0.00";
				qty = "0";
			}
			
			ItemBean item = new ItemBean(id, name, price, qty);
			
			CartBean result = brain.doCart(item, cart, add, update, parameters);
			request.setAttribute("cart", result);
		
		}
		catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
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

}
