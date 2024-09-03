<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>



<!DOCTYPE html>
<html lang="it">
 <%@ page contentType="text/html; charset=UTF-8" import="java.util.*,model.ProductBean,model.CartBean"%>
 
 <head>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link href="css/registerComplete.css" rel="stylesheet" type="text/css">
  <title>FollettoPoint</title>
 </head>
 
 <body>
  <%@ include file="header.jsp"%>
  <div class="registerCont">
   <div class="title">REGISTRATI</div>
   <form  action="Register" method="post">
     <div class="registerField">
     <input type="hidden" name="action" value="register2">
     <input type="hidden" name="tipo" value="0">
     
    <div class="registerColumn">
     <input class="registerColumnItem" name="nome" type="text" required placeholder="Nome">
    
     <input class="registerColumnItem" name="indirizzo" type="text" required placeholder="Indirizzo"> 
     
     <input class="registerColumnItem" name="pwd" type="password" required placeholder="Password">
     
    </div>
    <div class="registerColumn">
     <input class="registerColumnItem"name="cognome" type="text" required placeholder="Cognome">
    
     <input class="registerColumnItem" name="numero" type="text" required placeholder="Recapito Telefonico">
    
     <input class="registerColumnItem" name="metodo" type="text" pattern="[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}" required placeholder="Metodo di Pagamento">
     
    </div>
    </div>
   <div class="buttonDash">
     <input class="registerButton" type="submit" value="Registrati">
     
     <input class="registerButton" type="reset">
    </div> 
   </form> 
  </div>
  
  <%@ include file="footer.jsp"%>
 </body>
</html>