<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html lang="it">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="Content-Type" content="text/html">
		<title>FollettoPoint</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="css/errorLogin.css">	
	</head>
	
	
	<body>
		<main>
			<div class="Container">
					<img style="margin: auto; margin-bottom: 0;margin-top: 0; width: 30%;" alt="logo" src="imgs/struct/logo.png">
           	 		<p>Oops!</p>
           	 		<p>Si è verificato un problema...</p>
           	 		<% if(response.getStatus() == 500){ %>
           	 			<p>Errore: <%=exception.getMessage()%></p>
           	 		<%}else {%>
            			<p>Codice Errore: <%=response.getStatus() %> </p>
            		<%} %>
            		<a href="HomeView.jsp">Torna alla Home</a>
    		</div>		
		</main> 	
	</body>
</html>