package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.*;
@WebServlet("/suggests")
//questo nome è calzante con ciò che fa questa classe.
public class suggerimenti extends HttpServlet {
	private static final long serialVersionUID = 1L;


	static ProductModel model = new ProductModel();
	

	
	public suggerimenti() {
		super();
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

			Collection<SearchResult> sr = null;
			try {
				sr = (Collection<SearchResult>) model.doRetrieveByName(request.getParameter("term"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
			if(sr != null) {
				Iterator<SearchResult> it = sr.iterator();
				while(it.hasNext()) {
					SearchResult r = it.next();
					response.getWriter().println("<a id=\"suggestItem\"  href=\"detail?action=read&id="+r.getId()+"\">"+r.getNome()+"</a><br>");
										
				}
			}
			
	}

	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
