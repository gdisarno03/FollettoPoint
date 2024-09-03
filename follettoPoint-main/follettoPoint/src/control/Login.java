package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.UserBean;
import model.UserModel;

@WebServlet("/Login")
public class Login extends HttpServlet {
	
	UserModel model = new UserModel();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		{
			String usrnm = request.getParameter("username");
			String pwd = request.getParameter("password");
			
			String redirectedPage = "";
			try {
				UserBean user = checkLogin(usrnm, pwd);
				
				if (user != null) {
					request.getSession().setAttribute("user", user);
					if(request.getSession().getAttribute("source") != null) {
						redirectedPage = "/CartView.jsp";
						request.getSession().setAttribute("source", null);						
					}
						
					else
						redirectedPage = "/HomeView.jsp";
				}
					
			} catch (Exception e) {
				
				redirectedPage = "/loginerror.jsp";
			}
			response.sendRedirect(request.getContextPath() + redirectedPage);
		}
	}
	
	
	private UserBean checkLogin(String email, String pwd) throws Exception {
		UserBean usr = model.doRetrieveByKey(email);
		
		if(usr != null) {
		
			if (usr.getEmail().equals(email) && usr.getPwd().equals(pwd)) {
				
			} else
				throw new Exception("A");
			
		}else {
			throw new Exception("B");
			
		}
		return usr;
	}
	
	private static final long serialVersionUID = 1L;

	public Login() {
		super();
	}	

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	

}
