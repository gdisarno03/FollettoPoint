<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>



<!DOCTYPE html>
<html lang="it">
	<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,model.ProductBean,model.CartBean"%>
	
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="css/register.css" rel="stylesheet" type="text/css">
		<title>FollettoPoint</title>
	</head>
	
	<body>
		<%@ include file="header.jsp"%>
		
		<div class="registerCont">
			<div class="title">REGISTRATI</div>
			<input type="hidden" name="action" value="register1">
	
			 
			<input id="emailInput" onclick="disalert()" name="email" type="email" required placeholder="Email"><br>
	
			<br>
			<p id="alert" style="display:none; color: red"></p>
			<input id="checkEmailButton" onclick="controllaEmail()" type="submit" value="Registrati">
			<br>
		</div>
		<%@ include file="footer.jsp"%>
		<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
		
<script>

	function disalert(){
		$("#alert").hide();
	}


    function verificaEmail(email) {
      var regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      return regex.test(email);
    }

    function controllaEmail() {
      var emailInput = document.getElementById("emailInput");
      var email = emailInput.value;
      
      var risultato = document.getElementById("alert");
      
      if (verificaEmail(email)) {
    	  
    	  $(document).ready(function() {
    	       
    	            var email = $("#emailInput").val();
    	            
    	            $.ajax({
    	                url: "Register", // Indirizzo della tua servlet
    	                method: "GET",
    	                data: {
    	                	email: email,
    	                	action: "checkEmail"
    	                },
    	                success: function(response) {
    	                    // Gestisci la risposta dal server
    	                    if (response == "exists") {
    	                        
    	                    	risultato.innerHTML = "L'email è stata già utilizzata!";
    	                        $("#alert").show();
    	                        
    	                    } else {
    	                        
    	                        window.location.href = "Register?action=register1&email="+email;
    	                        
    	                    }
    	                },
    	                error: function(xhr, status, error) {
    	                    // Gestisci eventuali errori
    	                    alert("Errore AJAX:", error);
    	                }
    	            });
    	        
    	    });
        
      } else {
        risultato.innerHTML = "L'email non è corretta!";
        $("#alert").show();
      }
    }
  </script>

    



	</body>
</html>