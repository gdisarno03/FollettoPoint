<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="it">
<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,model.ProductBean,model.CartBean, model.ProductCartBean"%>
<head>
	<meta charset="ISO-8859-1">
	<link href="css/cart.css" rel="stylesheet" type="text/css">
	<title>Cart</title>
</head>
<body class="main">
	<%@ include file="header.jsp"%>
	<% 
		CartBean cart = (CartBean) request.getSession().getAttribute("cart");
	
		if (cart == null)
			cart = new CartBean();
		
		if(cart.getProducts().size() != 0) {
	%>
		<p class="titleCat">Carrello</p>
	<div>
		<!-- descrizione tabella rovina lo stile -->
		<table class="cart">
		
		<tr>
			
			
			<th></th>
			<th>Nome<br>Prodotto</th>
			<th>Prezzo</th>
			<th>Quantità<th>
			<th>Totale</th>
			
			
		</tr>
		
			<% List<ProductCartBean> prodcart = cart.getProducts(); 	
			double totale = 0;   
			for(ProductCartBean beancart: prodcart) {
				   totale = totale +  (beancart.getProduct().getPrice() * beancart.getQuantityCart());
			%>
			
			
				<tr>
				<td><img class="imgProd" src="imgs/<%=beancart.getProduct().getCode()%>.png" alt="Product Image"></td>
				
					<td><a href="detail?action=read&id=<%=beancart.getProduct().getCode()%>">
						<p><%=beancart.getProduct().getName()%></p>
					</a> 
					</td>
					
					
					
					 <td><p>€<%=beancart.getProduct().getPrice()%></p> </td>
					
						<td><form action="cart" method="post">
							<input type="hidden" name="action" value="aggiornaQuantita">
		        			<input type="hidden" name="id" value="<%= beancart.getProduct().getCode() %>">
		        
		        			<button type="submit" name="operazione" value="decrementa">-</button>
		        			<p><%=beancart.getQuantityCart()%></p>
		        			<button type="submit" name="operazione" value="incrementa">+</button>
		    			</form>
					</td>
					<td><p>€<%=beancart.getProduct().getPrice() * beancart.getQuantityCart() %></p></td>
					
					
					
					<td><a class="adminFunc" href="cart?action=deleteC&id=<%=beancart.getProduct().getCode()%>">Rimuovi</a></td>
				</tr>
			
			<%} %>
	</table>
	</div>
	<div class="dashboard">
	
	<% if(totale > 0) %>
		<a class="adminFunc" href="orders?action=insert&source=cart&totale=<%=totale%>">Concludi ordine</a>		
	<p class="adminFunc">Totale:  €<%= totale %></p>
	<% 
	}else{ %>
		<h2 style="text-align: center; padding: 13% 0%; margin-bottom: 500px;">Carrello vuoto</h2> 
	<% } %>
	</div>
	<%@ include file="footer.jsp"%>
	
	
</body>
</html>