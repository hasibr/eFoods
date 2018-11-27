package analytics;


/**
 * Application Lifecycle Listener implementation class MainAnalytics
 *
 */

import java.util.List;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Watches events in the application to calculate analytics for the admin.
 *
 */
@WebListener
public class MainAnalytics implements HttpSessionListener, HttpSessionAttributeListener, ServletRequestListener {

	public void sessionCreated(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		Long currentTime = System.currentTimeMillis();

		session.setAttribute("beginSession", currentTime);
		session.setAttribute("custAdded", false);
		session.setAttribute("custCheckedOut", false);
	}

	
	@SuppressWarnings("unchecked")
	public void requestInitialized(ServletRequestEvent sre) {
		HttpServletRequest httpReq = (HttpServletRequest) sre.getServletRequest();
		String pageVisted = httpReq.getRequestURL().toString();
		HttpSession session = httpReq.getSession();

		if (pageVisted.matches(".*Categories")) {

			boolean custAdded = (boolean) session.getAttribute("custAdded");
			if (httpReq.getParameter("add") != null && custAdded == false) {
				long timeTaken = getTimeTaken(session);

				List<Integer> timeBetweenAdd = (List<Integer>) httpReq.getServletContext()
						.getAttribute("timeBetweenAdd");
				timeBetweenAdd.add((int) timeTaken);
				session.setAttribute("custAdded", true);
			}

		} else if (pageVisted.matches(".*Checkout")) {

			boolean custCheckedOut = (boolean) session.getAttribute("custCheckedOut");
			if (httpReq.getParameter("confirmOrderBut") != null && custCheckedOut == false) {
				long timeTaken = getTimeTaken(session);

				List<Integer> timeBetweenCheckout = (List<Integer>) httpReq.getServletContext()
						.getAttribute("timeBetweenCheckout");
				timeBetweenCheckout.add((int) timeTaken);
				session.setAttribute("custCheckedOut", true);
			}
		}
	}

	// Calculates the seconds between the session start and the event.
	private long getTimeTaken(HttpSession session) {
		long currentTime = System.currentTimeMillis();
		long beginSession = (long) session.getAttribute("beginSession");

		long timeTaken = (currentTime - beginSession) / 1000;
		return timeTaken;
	}

}


