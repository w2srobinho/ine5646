<%-- 
    Document   : index
    Created on : 04/09/2014, 00:33:07
    Author     : Robinho
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>INE5646 - primoREST</title>
    </head>
    <body>
        <h1>INE5646 - primo</h1>
        <form action="verifique" method="get">
            Número <input type="text" name="numero" size="30"><br>
            Host <input type="text" name="host" size="30"><br>
            Porta <input type="text" name="porta" size="30"><br>
            Nome do serviço <input type="text" name="servico" size="30"><br>
            <button type="submit">Verifique</button>
        </form>
    </body>
</html>
