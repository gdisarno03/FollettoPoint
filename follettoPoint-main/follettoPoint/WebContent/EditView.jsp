<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
// Check user credentials
	UserBean usr = (UserBean) request.getSession().getAttribute("user");
	
	
	if ( usr == null || usr.getTipo() != 1){
		 response.sendRedirect("login-form.jsp");
	}
	
	ProductModel model = new ProductModel();
	ProductBean product = model.doRetrieveByKey(Integer.parseInt(request.getParameter("id")));
%>

<!DOCTYPE html>
<html lang="it">
	<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,model.ProductBean,model.CartBean"%>
	
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="css/insert.css" rel="stylesheet" type="text/css">
		<title>FollettoPoint</title>
	</head>
	
	<body>
		<%@ include file="header.jsp"%>
			<div class="Cont">
			<h2 class="title">MODIFICA PRODOTTO</h2>
			<form action="insert" method="post" enctype='multipart/form-data'>
				<input type="hidden" name="action" value="edit"> 
				<input type="hidden" name="id" value="<%=Integer.parseInt(request.getParameter("id"))%>"> 
				
				<label for="name">Nome</label>
				<input name="name" type="text" maxlength="20" required value="<%=product.getName()%>"><br> 
				
				<label for="descrArea">Descrizione</label>
				<textarea id="descrArea" name="description" maxlength="100" rows="3" required></textarea><br>
				
				<label for="price">Prezzo</label>
				<input name="price" type="number" min="0" required value="<%=product.getPrice()%>"><br>
				
				<label for="sconto">Sconto</label>
				<input name="sconto" type="number" min="0" required value="<%=product.getSconto()%>"><br>
				
				<label for="iva">Iva</label>
				<input name="iva" type="number" min="0" required value="<%=product.getIva()%>"><br>
		
				<label for="quantity">Quantità</label>
				<input name="quantity" type="number" min="1" value="<%=product.getQuantity()%>" required><br>
			
				<label for="categoria">Categoria:</label><br> 
				<select name="categoria" id="catSelector">
		        	<option value='1'>Elettrodomestici</option>
		        	<option value='2'>Folletto</option>
		        	<option value='3'>Caffè</option>
		    	</select> <br>
		    	
		    	<input type="file" name="img" required value="imgs/<%=product.getCode()%>.png" accept="image/png"> <br>
				
				<input type="submit" value="Edit">
		
			</form>
		</div>	
		<br>
		<script>
			document.getElementById("descrArea").value = "<%=product.getDescription()%>";
			var selector = document.getElementById("catSelector");
			switch(<%=product.getCategoria()%>){
				case 1:
					selector.selectedIndex = 0;
					break;
				case 2:
					selector.selectedIndex = 1;
					break;
				case 3:
					selector.selectedIndex = 2; 
					break;
				default:
					selector.selectedIndex = 0;
					break;
			}	
		</script>
		
		<%@ include file="footer.jsp"%>
	</body>
</html>