<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    Collection<?> products = (Collection<?>) request.getAttribute("products");
	Collection<?> products2 = (Collection<?>) request.getAttribute("products2");

    if(products == null) {
        response.sendRedirect("./home");	
        return;
    }
%>

<!DOCTYPE html>
<html lang="it">
<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,model.*"%>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="css/home.css" rel="stylesheet" type="text/css">
    <title>FollettoPoint</title>
</head>

<body>
    <%@ include file="header.jsp"%>
    
	 <div class="carousel">
	    <img class="active" src="imgs/struct/slide1.png" alt="Immagine 1">
	    <img src="imgs/struct/slide2.png" alt="Immagine 2">
	    <img src="imgs/struct/slide3.png" alt="Immagine 3">
     </div>

  <script>
    var carousel = document.querySelector('.carousel');
    var images = carousel.querySelectorAll('img');
    var currentImageIndex = 0;
    var intervalTime = 3000; // 3 secondi

    function showNextImage() {
      // Nasconde l'immagine corrente
      images[currentImageIndex].classList.remove('active');

      // Incrementa l'indice dell'immagine corrente o riportalo a 0 se raggiunge l'ultimo indice
      currentImageIndex = (currentImageIndex + 1) % images.length;

      // Mostra l'immagine successiva
      images[currentImageIndex].classList.add('active');
    }

    setInterval(showNextImage, intervalTime);
  </script>
	    
	  
	    <p class="titleCat">Novità!</p>
	    
	    <div class="product-section">
	        <%
	            if (products != null && products.size() != 0) {
	                Iterator<?> it = products.iterator();
	                while (it.hasNext()) {
	                    ProductBean bean = (ProductBean) it.next();
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
					<strong class="priceProd" style="text-decoration: line-through;">€<%=Math.round((bean.getPrice()*(1+bean.getSconto()/100))*100.0)/100.0%></strong><strong class="priceProd"> €<%=bean.getPrice()%></strong></p>
				<%}else{ %>
					<p class="priceProd">€<%=bean.getPrice()%></p>
					
					
				<%} %> 
		                
		              <a class="addToCart" href="home?action=addC&id=<%=bean.getCode()%>">Aggiungi al carrello</a>
		    		        
		        </div>
	        
	        <%
	                }
	            } else {
	        %>
	        <p style="text-align: center;">Catalogo in allestimento</p>
	        <%
	            }
	        %>
	    </div>
	    
	    <%-- Section: Products sorted by discount --%>
	    <p class="titleCat">In sconto!</p> 
	    
	    <div class="product-section">
	        <%
	            if (products2 != null && products2.size() != 0) {
	                Iterator<?> it = products2.iterator();
	                while (it.hasNext()) {
	                    ProductBean bean = (ProductBean) it.next();
	        %>
	      
		        <div class="product">
		        	<a href="detail?action=read&id=<%=bean.getCode()%>">
		            	<img class="imgProd" src="imgs/<%=bean.getCode()%>.png" alt="Product Image">
		            </a>
		            <a href="detail?action=read&id=<%=bean.getCode()%>">
		            	<p class="nameProd"><%=bean.getName()%></p>
		            </a>
		            
		             
		             <p class="descrProd"><%=bean.getDescription()%></p>
		            
		            
		                
 				<%if(bean.getSconto() != 0){ %>
					<strong class="priceProd" style="text-decoration: line-through;">€<%=Math.round((bean.getPrice()*(1+bean.getSconto()/100))*100.0)/100.0%></strong><strong class="priceProd"> €<%=bean.getPrice()%></strong></p>
				<%}else{ %>
					<p class="priceProd">€<%=bean.getPrice()%></p>
					
					
				<%} %> 		                
		              <a class="addToCart" href="home?action=addC&id=<%=bean.getCode()%>">Aggiungi al carrello</a>
		    		        
		        </div>
	        
	        <%
	                }
	            } else {
	        %>
	        <p style="text-align: center;">Catalogo in allestimento</p>
	        <%
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