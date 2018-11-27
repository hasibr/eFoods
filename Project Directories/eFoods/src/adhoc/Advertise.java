package adhoc;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import beans.Item;
import model.Engine;

/**
 * Servlet Filter implementation class Advertise
 */
@WebFilter("/Cart")
public class Advertise implements Filter {

    /**
     * Default constructor. 
     */
    public Advertise() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		
		
		
		if(request.getParameter("add")!=null && request.getParameter("id").equals("1409S413")) {
			
			Engine brain = Engine.getInstance();
			
			try {
				
				Item ord = brain.doExpress(request.getParameter("id"));
				Item ad = brain.doExpress("2002H712");
				
				request.setAttribute("suggest", ord.getName());
				request.setAttribute("ad", ad);
			
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(request.getParameter("add")!=null && request.getParameter("id").equals("2002H712")) {
			
			Engine brain = Engine.getInstance();
			
			try {
				
				Item ord = brain.doExpress(request.getParameter("id"));
				Item ad = brain.doExpress("1409S413");
				
				request.setAttribute("suggest", ord.getName());
				request.setAttribute("ad", ad);
			
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// pass the request along the filter chain
		chain.doFilter(request, response);
		
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
