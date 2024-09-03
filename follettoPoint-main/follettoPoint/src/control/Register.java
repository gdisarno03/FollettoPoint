package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.UserModel;
import model.UserBean;

@WebServlet("/Register")
public class Register extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	UserModel model = new UserModel();
	
	//il refactor non è necessario perchè serve a gestire il flusso di esecuzione
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String emailLiteral = "email";
		String redir = "";
		
		if(action.equalsIgnoreCase("checkEmail")) {
			
	        try {
				if (model.checkEmail(request.getParameter(emailLiteral))) {
				    response.getWriter().write("exists");
				} else {
				    response.getWriter().write("not_exists");
				}
			} catch (SQLException e) {	
				e.printStackTrace();
			}
				
		}
		
		else if(action.equalsIgnoreCase("register1")) {
			String email = request.getParameter("email");
			UserBean bean = new UserBean();
			
			try {
				bean = model.doRetrieveByKey(email);
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			if (bean != null) {
				redir="/userNotNew.html";
			}
			else {
				request.getSession().removeAttribute(emailLiteral);
				request.getSession().setAttribute(emailLiteral, email);
				redir="/registerComplete.jsp";
			}
			response.sendRedirect(request.getContextPath() + redir);
		

		}else if(action.equalsIgnoreCase("register2")) {
			
			String nome = request.getParameter("nome");
			String cognome = request.getParameter("cognome");
			String email = (String) request.getSession().getAttribute(emailLiteral);
			String password = request.getParameter("pwd");
			String metodo = request.getParameter("metodo");
			int tipo = Integer.parseInt(request.getParameter("tipo"));
			String numero = request.getParameter("numero");
			String indirizzo = request.getParameter("indirizzo");
			
	   

			UserBean bean = new UserBean();
			bean.setNome(nome);
			bean.setCognome(cognome);
			bean.setIndirizzo(indirizzo);
			bean.setNumero(numero);
			bean.setTipo(tipo);
			bean.setEmail(email);
			bean.setMetodo(metodo);
			bean.setPwd(password);
			
			try {
				model.doSave(bean);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			request.getSession().removeAttribute(emailLiteral);
			request.getSession().setAttribute("user", bean);
			
			if(request.getSession().getAttribute("source") != null) {
				redir = "/CartView.jsp";
				request.getSession().setAttribute("source", null);						
			}
				
			else
				redir = "/HomeView.jsp";
			response.sendRedirect(request.getContextPath() + redir);
		}		
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
