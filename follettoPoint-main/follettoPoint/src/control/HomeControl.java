package control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.Part;

import model.*;
/**
 * Servlet implementation class ProductControl
 */
@MultipartConfig(
		  fileSizeThreshold = 1024 * 1024 * 100, // 1 MB
		  maxFileSize = 1024 * 1024 * 100,      // 10 MB
		  maxRequestSize = 1024 * 1024 * 100   // 100 MB
		)
public class HomeControl extends HttpServlet {
	private static final long serialVersionUID = 1L;


	static HomeModel model = new HomeModel();
	

	
	public HomeControl() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		CartBean cart = (CartBean)request.getSession().getAttribute("cart");
		if(cart == null) {
			cart = new CartBean();
			request.getSession().setAttribute("cart", cart);
		}
		
		String action = request.getParameter("action");

		try {
			//L'attuale struttura del metodo ci consente di gestire il flusso di esecuzione
			if (action != null) {
				if (action.equalsIgnoreCase("addC")) {
					int id = Integer.parseInt(request.getParameter("id"));
					cart.addProduct(model.doRetrieveByKey(id), 1);
				} else if (action.equalsIgnoreCase("deleteC")) {
					int id = Integer.parseInt(request.getParameter("id"));
					cart.deleteProduct(model.doRetrieveByKey(id));
				}
				else if (action.equalsIgnoreCase("read")) {
					int id = Integer.parseInt(request.getParameter("id"));
					request.removeAttribute("product");
					request.setAttribute("product", model.doRetrieveByKey(id));
				}
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		request.getSession().setAttribute("cart", cart);
		request.setAttribute("cart", cart);
		
		String products = "products";
		String products2 = "products2";
		
		
		
		String sort = request.getParameter("sort");
		try {
			if(sort != null && sort.equals("nome")) {
				request.removeAttribute(products);
				request.setAttribute(products, model.doRetrieveAll("nome"));
				request.removeAttribute(products2);
				request.setAttribute(products2, model.doRetrieveAll("nome"));
			}
			//per nome e prezzo preferiamo usare i literals
			else if(sort != null && sort.equals("prezzo")) {
				request.removeAttribute(products);
				request.setAttribute(products, model.doRetrieveAll("prezzo"));
				request.removeAttribute(products2);
				request.setAttribute(products2, model.doRetrieveAll("prezzo"));
			}
			else {
			request.removeAttribute(products2);
			request.setAttribute(products2, model.doRetrieveAll("sconto"));
			request.removeAttribute(products);
			request.setAttribute(products, model.doRetrieveAll("id"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/HomeView.jsp");
		dispatcher.forward(request, response);
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}