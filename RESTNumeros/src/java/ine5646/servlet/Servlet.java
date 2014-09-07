package ine5646.servlet;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.io.IOException;
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
        String urlPath = request.getPathInfo().substring(1);
        JsonObject json = new JsonObject();

        try {
            double[] numeros = strToDouble(urlPath.split("/"));
            double media = calcMediaRest(numeros);

            json.addProperty("cod", "OK");
            json.addProperty("qtd", numeros.length);
            json.addProperty("media", media);
            json.add("acimaDaMedia", jsonAcimaDaMedia(numeros, media));
        } catch (NumberFormatException e) {
            json.addProperty("cod", "NOK");
            json.addProperty("erro", 2);
            json.addProperty("motivo", e.getMessage());
        } catch (IllegalArgumentException e) {
            json.addProperty("cod", "NOK");
            json.addProperty("erro", 1);
            json.addProperty("motivo", e.getMessage());
        }

        response.getWriter().println(json);
    }

    double[] strToDouble(String[] numeros)
            throws IllegalArgumentException, NumberFormatException
    {
        if (numeros[0].equals("")) {
            throw new IllegalArgumentException("tem que ter pelo menos um número");
        }

        double[] numerosConvertidos = new double[numeros.length];
        for (int i = 0; i < numeros.length; ++i) {
            if (!numeros[i].matches("\\d+\\.*\\d*")) {
                throw new NumberFormatException("argumento " + numeros[i] + " não é número.");
            }
            numerosConvertidos[i] = Double.parseDouble(numeros[i]);
        }

        return numerosConvertidos;
    }

    private JsonArray jsonAcimaDaMedia(double[] numeros, double media)
    {
        JsonArray acimaDaMedia = new JsonArray();
        for (int i = 0; i < numeros.length; ++i) {
            if (numeros[i] > media) {
                acimaDaMedia.add(new JsonPrimitive(numeros[i]));
            }
        }
        return acimaDaMedia;
    }

    private double calcMediaRest(double[] numeros)
    {
        double media = 0;
        for (int i = 0; i < numeros.length; ++i) {
            media += numeros[i];
        }

        return media / numeros.length;
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
            throws ServletException, IOException
    {
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
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>

}
