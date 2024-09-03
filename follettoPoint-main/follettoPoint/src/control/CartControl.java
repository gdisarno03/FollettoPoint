package control;

import java.io.IOException; 
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.*;
/**
 * Servlet implementation class ProductControl
 */
public class CartControl extends HttpServlet {
 private static final long serialVersionUID = 1L;

 
 ProductModel model = new ProductModel();
 
 public CartControl() {
  super();
 }
//per sonarCloud: l'attuale factoring del codice ci ha permesso di meglio seguire eventuali problemi 
 //scaturite dall'esecuzione di questo metodo
 protected void doGet(HttpServletRequest request, HttpServletResponse response)
   throws ServletException, IOException {

  CartBean cart = (CartBean)request.getSession().getAttribute("cart");
  if(cart == null)
	  cart = new CartBean();
  List<ProductCartBean> products = cart.getProducts();
  
  String action = request.getParameter("action");
  
  
  int id = Integer.parseInt(request.getParameter("id"));

  try {
   if (action != null) {
    
	    if (action.equalsIgnoreCase("deleteC")) {
	    	id = Integer.parseInt(request.getParameter("id"));
	    	cart.deleteProduct(model.doRetrieveByKey(id));
	    }
	    
	    else if (action.equalsIgnoreCase("aggiornaQuantita")) {
	        id = Integer.parseInt(request.getParameter("id"));
	        String operazione = request.getParameter("operazione");
		   	if (operazione.equalsIgnoreCase("incrementa")) {
		   		for(ProductCartBean p : products)
		   			if (p.getProduct().getCode() == id && p.getQuantityCart() < p.getProduct().getQuantity())
		   					p.setQuantityCart(p.getQuantityCart()+1);
		   	}
		   	else if (operazione.equalsIgnoreCase("decrementa")) { 
		   		for(ProductCartBean p : products)
		   			if (p.getProduct().getCode() == id && p.getQuantityCart() > 1)
		   					p.setQuantityCart(p.getQuantityCart()-1);
		   }
	        
	   }
    

   }  
  
  } catch (SQLException e) {
	  e.printStackTrace();
  }
  
  request.getSession().removeAttribute("cart");
  request.getSession().setAttribute("cart", cart);
  
  
  
  String sort = request.getParameter("sort");

  try {
   request.removeAttribute("products");
   request.setAttribute("products", model.doRetrieveAll(sort));
  } catch (SQLException e) {
	  e.printStackTrace();
  }

  RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/CartView.jsp");
  dispatcher.forward(request, response);
 }

 protected void doPost(HttpServletRequest request, HttpServletResponse response)
   throws ServletException, IOException {
  doGet(request, response);
 }

}