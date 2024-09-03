package control;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import model.*;
/**
 * Servlet implementation class ProductControl
 */
@MultipartConfig(
		  fileSizeThreshold = 1024 * 1024 * 100, // 1 MB
		  maxFileSize = 1024 * 1024 * 100,      // 10 MB
		  maxRequestSize = 1024 * 1024 * 100   // 100 MB
		)
public class OrderDetailControl extends HttpServlet {
	private static final long serialVersionUID = 1L;


	static OrderDetailModel model = new OrderDetailModel();
	

	
	public OrderDetailControl() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		CartBean cart = (CartBean)request.getSession().getAttribute("cart");
		if(cart == null) {
			cart = new CartBean();
			request.getSession().setAttribute("cart", cart);
		}
		
		request.getSession().setAttribute("cart", cart);
		request.setAttribute("cart", cart);
		
		
		String sort = request.getParameter("sort");
		int idOrdine = Integer.parseInt(request.getParameter("id"));
		try {
			if(sort!=null) {
			request.removeAttribute("products");
			request.setAttribute("products", model.doRetrieveAll(sort,idOrdine));
			}
			else
			{
				request.removeAttribute("products");
				request.setAttribute("products", model.doRetrieveAll("idProdotto",idOrdine));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/OrderDetail.jsp");
		dispatcher.forward(request, response);
		
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}