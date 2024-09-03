<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	
	Collection<?> products = (Collection<?>) request.getAttribute("products");
	if(products == null) {
		response.sendRedirect("./product");	
		return;
	}
%>



<!DOCTYPE html>
<html lang="it">
<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,model.*"%>

<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="css/product.css" rel="stylesheet" type="text/css">
	<title>FollettoPoint</title>
</head>

<body>
	<%@ include file="header.jsp"%>
	<p class="titleCat">Catalogo</p>
	
		
			
			<a class="adminFunc" href="product?sort=nome">ORDINA PER NOME</a>
			<a class="adminFunc" href="product?sort=prezzo">ORDINE PER PREZZO</a>
			
		<div class="product-section">
			<%
				if (products != null && products.size() != 0) {
					Iterator<?> it = products.iterator();
					while (it.hasNext()) {
						ProductBean bean = (ProductBean) it.next();
						if(user == null || user.getTipo() != 1){
							if(bean.getQuantity() > 0){
			%>
			<div class="product">
		        	<a href="detail?action=read&id=<%=bean.getCode()%>">
		            	<img class="imgProd" src="imgs/<%=bean.getCode()%>.png" alt="Product Image">
		            </a>
		            <a class="nameProd" href="detail?action=read&id=<%=bean.getCode()%>">
		            	<p class="nameProd"><%=bean.getName()%></p>
		            </a>
		            
		             
		             <p class="descrProd"><%=bean.getDescription()%></p>
		             
		            
		                
		         <%if(bean.getSconto() != 0){ %>
					<strong class="priceProd" style="text-decoration: line-through;">€<%=Math.round((bean.getPrice()*(1+bean.getSconto()/100))*100.0)/100.0%></strong><strong class="priceProd"> €<%=bean.getPrice()%></strong>
				<%}else{ %>
					<p class="priceProd">€<%=bean.getPrice()%></p>
					
					
				<%} %> 
		                
		              <a class="addToCart" href="home?action=addC&id=<%=bean.getCode()%>">Aggiungi al carrello</a>
		    		        
		        </div>
			<%
				}
			}else{%>
				<div class="product">
		        	<a href="detail?action=read&id=<%=bean.getCode()%>">
		            	<img class="imgProd" src="imgs/<%=bean.getCode()%>.png" alt="Product Image">
		            </a>
		            <a class="nameProd" href="detail?action=read&id=<%=bean.getCode()%>">
		            	<p class="nameProd"><%=bean.getName()%></p>
		            </a>
		            
		             
		             <p class="descrProd"><%=bean.getDescription()%></p>
		             
		            
		                
		         <%if(bean.getSconto() != 0){ %>
					<strong class="priceProd" style="text-decoration: line-through;">€<%=Math.round((bean.getPrice()*(1+bean.getSconto()/100))*100.0)/100.0%></strong><strong class="priceProd"> €<%=bean.getPrice()%></strong>
				<%}else{ %>
					<p class="priceProd">€<%=bean.getPrice()%></p>
					
					
				<%} %> 
		                
		              <a class="addToCart" href="home?action=addC&id=<%=bean.getCode()%>">Aggiungi al carrello</a>
		    		        
		        </div>
		        <%
					}
						}
				}
			%>
	</div>
	
	
	
	 <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
 	<script>
 	$(document).ready(function() {
 	      $(".product").on("mouseenter", function() {
 	        $(this).css("transform", "scale(1.1)");
 	      }).on("mouseleave", function() {
 	        $(this).css("transform", "scale(1.0)");
 	      });
 	    });
  </script>
	<%@ include file="footer.jsp"%>
</body>
</html>