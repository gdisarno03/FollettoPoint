<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	ProductBean product = (ProductBean) request.getAttribute("product");
	
	if (product == null)
		response.sendRedirect("/ProductView.jsp");
	
	CartBean cart = (CartBean) request.getAttribute("cart");
	
	
%>

<!DOCTYPE html>
<html lang="it">

	<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,model.ProductBean,model.CartBean"%>

<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="css/detail.css" rel="stylesheet" type="text/css">
	<title><%=product.getName()%></title>
</head>

<body>
	<%@ include file="header.jsp"%>
		<div class="detailContainer">
			<img class="imgProd" src="imgs/<%=product.getCode()%>.png" alt="imgProd">
			<div class="infoProd">
				<p class="title"><%=product.getName()%></p>
				<p class="descr"><%=product.getDescription()%></p>
				<%if(product.getSconto() != 0){ %>
					<p class="priceDisc" style="text-decoration:line-through; ;">€<%=Math.round((product.getPrice()*(1+product.getSconto()/100))*100.0)/100.0%></p>
					<p class="priceFull">€<%=product.getPrice()%></p>
				<%}else{ %>
					<p class="priceFull">€<%=product.getPrice()%></p>
				<%} %>
				<form action="detail" method="post">
					<input type="hidden" name="action" value="addC">
					<input type="hidden" name="id" value="<%=product.getCode()%>">
			 
			<select name="q">
				<%
					int i;
					for(i = 1; i <= product.getQuantity(); i++){
				%>
       			<option value='<%= i %>'><%= i %></option>
      			
      			<%} %>
   			</select>
			<%if(user != null && user.getTipo() == 1 ){ %>
   			<a class="adminFunc" href="product?action=delete&id=<%=product.getCode()%>">ELIMINA PRODOTTO</a>
				
			<a class="adminFunc" href="EditView.jsp?&id=<%=product.getCode()%>">MODIFICA PRODOTTO</a>
   			<%}%>
   			<button type="submit">AGGIUNGI AL CARRELLO <img class="miniCart" alt="-" src="imgs/struct/miniCart.png"></button>
  		</form>
			</div>
		</div>

		
	
	
	<%@ include file="footer.jsp"%>
</body>
</html>