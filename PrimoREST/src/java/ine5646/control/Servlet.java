package ine5646.control;

import com.google.gson.Gson;
import ine5646.view.GeradorHtml;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

@WebServlet(name = "ServletVerificador", urlPatterns = {"/verifique"})
public class Servlet extends HttpServlet
{

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println(numberProcess(request));
        } finally {
            out.close();
        }
    }

    private String numberProcess(HttpServletRequest request)
    {
        GeradorHtml html = new GeradorHtml();
        String numero = request.getParameter("numero");
        
        if (!numero.matches("\\d+")) {
            return html.naoEhNumero(numero);
        }
        
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(
                "http://" +
                request.getParameter("host") + ":" + 
                request.getParameter("porta") + "/" +
                request.getParameter("servico") + "/" + numero);

        String arrayDeDivisoresJSON = target.request().get().readEntity(String.class);
        int[] divisores = new Gson().fromJson(arrayDeDivisoresJSON, int[].class);
        
        if (divisores.length < 3) {
            return html.primo(numero);
        }

        return html.naoEhPrimo(numero);
    }
}
