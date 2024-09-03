<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
// Check user credentials
	
	if (request.getSession().getAttribute("user") != null){
		 response.sendRedirect("HomeView.jsp");
	}
%>

<!DOCTYPE html>
<html lang="it">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/login.css" rel="stylesheet" type="text/css">
<title>Login</title>
</head>
<%@ include file="header.jsp"%>
<body>

<form action="Login" method="post"> 

     <div id="loginCont">
     	
     	<img alt="userLogo" src="imgs/struct/user.png">
     	
     	
     		
    		
	     <input id="username" type="text" name="username" placeholder="Email"> 
	     
	     <input id="password" type="password" name="password" placeholder="Password"> 
	     
	     <input type="submit" value="Accedi"/>
		 
		 <a href="register.jsp">Prima volta? Registrati!</a>     
     </div>

</form> 

</body>
<%@ include file="footer.jsp"%>
</html>
