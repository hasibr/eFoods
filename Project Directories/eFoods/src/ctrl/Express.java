package ctrl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Item;
import model.Engine;

/**
 * Servlet implementation class Express
 */
@WebServlet("/Express")
public class Express extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Express() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		if(request.getParameter("calc") != null) {
			
			
			Engine brain = Engine.getInstance();
			
			try {
				String id = request.getParameter("id");
				Item item = brain.doExpress(id);
				
				String name = item.getName();
				String price = item.getPrice();
				String qty = request.getParameter("qty");
				
				
				response.sendRedirect(String.format("Cart?add=true&name=%s&price=%s&qty=%s&id=%s", name, price, qty, id));
				return;
			}
			catch(Exception e) {
				request.setAttribute("error", e.getMessage());
			}
		}

		request.getServletContext().getRequestDispatcher("/Express.jspx").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
