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
public class InsertControl extends HttpServlet {
	private static final long serialVersionUID = 1L;


	
	static ProductModel model = new ProductModel();
	
	
	
	public InsertControl() {
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
			if (action != null) {
				if (action.equalsIgnoreCase("delete")) {
					int id = Integer.parseInt(request.getParameter("id"));
					model.doDelete(id);
					
					
				} else if (action.equalsIgnoreCase("insert")) {
					String name = request.getParameter("name");
					String description = request.getParameter("description");
					double price = Double.parseDouble(request.getParameter("price"));
					int quantity = Integer.parseInt(request.getParameter("quantity"));
					int categoria = Integer.parseInt(request.getParameter("categoria"));
					double sconto = Double.parseDouble(request.getParameter("sconto"));
					double iva = Double.parseDouble(request.getParameter("iva"));
					
					ProductBean bean = new ProductBean();
					bean.setName(name);
					bean.setDescription(description);
					bean.setSconto(sconto);
					bean.setIva(iva);
					bean.setPrice(price);
					bean.setQuantity(quantity);
					bean.setCategoria(categoria);
					int id = model.doSave(bean);
					
					//meglio tenere questo blocco locale alla funzione poichè 
					//è importante il path che altrimente non funzionerebbe bene
					try {
						//prende immagine dal form nel campo file che ho chiamato img
						Part filePart = request.getPart("img"); 
						//imposto la dir facendo path percorso + percorso dir
						String path = getServletContext().getRealPath("/")+"imgs\\" + id +".png";
						
						
						//scrivo il file nella cartella
					    filePart.write(path);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				
				
				else if (action.equalsIgnoreCase("edit")) {
					int id = Integer.parseInt(request.getParameter("id"));
					

					String name = request.getParameter("name");
					String description = request.getParameter("description");
					double price = Double.parseDouble(request.getParameter("price"));
					int quantity = Integer.parseInt(request.getParameter("quantity"));
					int categoria = Integer.parseInt(request.getParameter("categoria"));
					double sconto = Double.parseDouble(request.getParameter("sconto"));
					double iva = Double.parseDouble(request.getParameter("iva"));
			   

					ProductBean bean = new ProductBean();
					bean.setCode(id);
					bean.setName(name);
					bean.setDescription(description);
					bean.setSconto(sconto);
					bean.setIva(iva);
					bean.setPrice(price);
					bean.setQuantity(quantity);
					bean.setCategoria(categoria);
					model.doEdit(bean);
					
					//meglio tenere questo blocco locale alla funzione poichè 
					//è importante il path che altrimente non funzionerebbe bene
					try {
						//prende immagine dal form nel campo file che ho chiamato img
						Part filePart = request.getPart("img"); 
						//imposto la dir facendo path percorso + percorso dir
						String path = getServletContext().getRealPath("/")+"imgs\\" + id +".png";
						
						
						//scrivo il file nella cartella
					    filePart.write(path);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			} 		
		} catch (SQLException e) {
			e.printStackTrace();
		}

		request.getSession().setAttribute("cart", cart);
		request.setAttribute("cart", cart);
		//usiamo i literal per mantenere una sintassi visiva migliore
		try {
			request.setAttribute("products", model.doRetrieveAll("id"));
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		
		
		String sort = request.getParameter("sort");

		try {
			request.removeAttribute("products");
			request.setAttribute("products", model.doRetrieveAll(sort));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/ProductView.jsp");
		dispatcher.forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}