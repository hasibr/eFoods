package ctrl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Engine;

/**
 * Servlet implementation class Browse
 */
@WebServlet("/Browse")
public class Browse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Browse() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (request.getParameter("search") != null && request.getParameter("search").equals("1")) {
			
			
			try {
				// gather information from the form on this page
				String catID = request.getParameter("catID");
				String name = request.getParameter("name");
				String sortBy = request.getParameter("sortBy");
				Engine brain = Engine.getInstance();
				
				request.setAttribute("name", name);
				request.setAttribute("catID", catID);
				request.setAttribute("sortBy", sortBy);
				
				// returns a table with all the items that match the client's description
				request.setAttribute("result", brain.doBrowse(name, sortBy, catID));
			}
			catch(Exception e) {
				request.setAttribute("error", e.getMessage());
			}
			
		}
		request.getServletContext().getRequestDispatcher("/Browse.jspx").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
