<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,model.*"%>
<%
// Check user credentials
	
	UserBean user =  (UserBean) request.getSession().getAttribute("user");
	
%>

<!DOCTYPE html>
<html lang="it">
	
	<head>
		<title>header</title>
		<link rel="preconnect" href="https://fonts.googleapis.com">
		<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
		<link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,400;0,700;1,300&display=swap" rel="stylesheet">
		<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<link href="css/header.css" rel="stylesheet" type="text/css">
		<link rel="icon" type="image/x-icon" href="imgs/struct/logo.png">

	</head>
	<body>
		<div id="containerNavBar">
			<div id="centralcontainer">
				<img src="imgs/struct/ico.png" alt="menu" id="toggleBtn"  onclick="toggleSidebar()">
				<a id="logoCont" href="HomeView.jsp"><img alt="logo" id="logo" src="imgs/struct/logo.png"></a>
				<div id="searchbar">
					<input type="text" id="searchInput" placeholder="cerca qui..."></input>
		  			<div id="suggerimenti"></div>
		  			<span id="searchButton">X</span>
			  	</div>
				
				
				  
				
				<div id="iconDash">
					<% 
						if(user != null){
				  	%>
						<div class="icon"><a href="CartView.jsp"><img alt="cart" src="imgs/struct/cart.png"></a></div>
						<div class="icon"><a href="orders?sort=importo"><img alt="user" src="imgs/struct/user.png"></a></div>	  	
				  	<%}else{
				     %>
				     	
					  	<div class="icon"><a href="CartView.jsp"><img alt="cart" src="imgs/struct/cart.png"></a></div>
						<div class="icon"><a href="login-form.jsp"><img alt="user" src="imgs/struct/user.png"></a></div>
					<%} 
					 %>
					
				</div>
				<br>
				
			</div>
			
			<div id="bottomNav">	
				<div id="sidebar" style="left: -200px;">
					  <ul>
					  	  <li id="toggleBtn"  onclick="toggleSidebar()">Chiudi</li>
						  <li><a href="ProductView.jsp">Catalogo</a></li> 
					  </ul>
				</div>
			</div>
		</div>
		
		<script>
		$(document).ready(function() {
			  var searchBar = $("#searchInput");
			  var suggestions = $("#suggerimenti");
				
			  searchBar.on("input", function() {
			    var searchTerm = searchBar.val();
			    $("#suggerimenti").show();
			    

			    $.ajax({
			      url: "suggests", // URL del server per la gestione dei suggerimenti
			      method: "GET",
			      data: { 
			    	  term: searchTerm },
			      success: function(response) { 
			    	if(response == "null"){
			    		$("#suggerimenti").hide();
			    
			    		
			    	}else{
			    		suggestions.html(response);
			    
			    	}
			    		
			      }
			    });
			  });
			});

		</script>
	
		<script>
		function toggleSidebar() {
		    var sidebar = document.getElementById("sidebar");
		    if (sidebar.style.left === "-200px") {
		        sidebar.style.left = "0";
		    } else {
		        sidebar.style.left = "-200px";
		    }
		}


		</script>
		
		<script>
		$(document).ready(function() {
			  // Quando viene fatto clic sul div
			  $('#searchButton').click(function() {
			    // Resetta il contenuto del textarea
			    $('#searchInput').val('');
			    $('#suggerimenti').hide();
			  });
			});

		</script>
	</body>
</html>