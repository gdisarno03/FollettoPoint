<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
// Check user credentials
	UserBean usr = (UserBean) request.getSession().getAttribute("user");
	
	if ( usr == null || usr.getTipo() != 1){
		response.sendRedirect("login-form.jsp");
	}
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
			<h2 class="title">INSERISCI PRODOTTO</h2>
			<form action="insert" method="post" enctype='multipart/form-data'>
				<input type="hidden" name="action" value="insert"> 
				
				
				<input name="name" type="text" maxlength="20" required placeholder="Nome Prodotto"><br> 
				
				
				<textarea name="description" maxlength="100" rows="3" required placeholder="Descrizione Prodotto"></textarea><br>
				
				
				<input name="price" type="number" min="0" required placeholder="Prezzo"><br>
				
				
				<input name="sconto" type="number" min="0"  placeholder="Sconto" required><br>
				
				
				<input name="iva" type="number" min="0" required placeholder="Iva"><br>
		
				
				<input name="quantity" type="number" min="1" placeholder="Quantità" required><br>
			
				<label for="categoria">Categoria</label><br> 
				<select name="categoria">
		        	<option value='1'>Elettrodomestici</option>
		        	<option value='2'>Folletto</option>
		        	<option value='3'>Caffè</option>
		    	</select> <br>
		    	
		    	<input type="file" name="img" required accept="image/png"> <br>
				
				<input type="submit" value="Add"><input type="reset" value="Reset">
		
			</form>	
		</div>
		<br>
		<%@ include file="footer.jsp"%>
	</body>
</html>