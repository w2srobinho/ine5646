/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ine5646.control;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Robinho
 */
@WebServlet(name = "Servlet", urlPatterns = {"/*"})
public class Servlet extends HttpServlet
{
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("application/json;charset=UTF-8");
        JsonArray json;
        String pathInfo = request.getPathInfo().substring(1);
        String[] splitPath = pathInfo.split("/");

        try {
            double result = verifyTokens(splitPath);
            json = jsonResult("ok", ""+result);
        } catch (ArithmeticException e) {
            json = jsonResult("erro", "divisão por zero");
        } catch (NumberFormatException e) {
            json = jsonResult("erro", "'" +
                    splitPath[2] +
                    "' e/ou '" +
                    splitPath[3] +
                    "' não é número");
        } catch (IllegalArgumentException e) {
            json = jsonResult("erro", e.getMessage());
        }
        
        PrintWriter out = response.getWriter();
        out.println(json);
        out.close();
    }

    private JsonArray jsonResult(final String status, final String msg)
    {
        JsonArray jSonResult = new JsonArray();
        jSonResult.add(new JsonPrimitive(status));
        jSonResult.add(new JsonPrimitive(msg));
        return jSonResult;
    }

    /**
     * Returns a JSON calcule result.
     *
     * @param tokens a tokens group for verify
     * @return {@link JsonArray} calcule result
     */
    private double verifyTokens(String[] tokens) 
            throws IllegalArgumentException
    {
        if (tokens.length < 1) {
            throw new IllegalArgumentException("tem que ter pelo menos um número");
        }
        return calcRest(tokens);
    }

    private double calcRest(String[] tokens)
            throws NumberFormatException
    {
        Double firstNumber = Double.parseDouble(tokens[2]);
        Double secondNumber = Double.parseDouble(tokens[3]);
        if (tokens[1].equals("soma"))
            return soma(firstNumber, secondNumber);
        if (tokens[1].equals("subt"))
            return subt(firstNumber, secondNumber);
        if (tokens[1].equals("mult"))
            return mult(firstNumber, secondNumber);
        if (tokens[1].equals("divi"))
            return divi(firstNumber, secondNumber);
        throw new IllegalArgumentException("Operação deve ser uma destas: soma, subt, mult, divi");
    }
    
    private double soma(double num1, double num2)
    {
        return num1 + num2;
    }
    
    private double subt(double num1, double num2)
    {
        return num1 - num2;
    }
    
    private double mult(double num1, double num2)
    {
        return num1 * num2;
    }
    
    private double divi(double num1, double num2)
    {
        if (num1 == 0 || num2 == 0)
            throw new ArithmeticException();
        return num1 / num2;
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
