package ine5646.primo.control;

import ine5646.primo.model.PrimeNumber;
import ine5646.primo.view.GeradorHtml;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ServletVerificador", urlPatterns = {"/verifique"})
public class ServletVerificador extends HttpServlet 
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
            out.println(numberProcess(request.getParameter("numero")));
        } finally {
            out.close();
        }
    }

    private String numberProcess(String numero) 
    {
        GeradorHtml html = new GeradorHtml();

        if (!numero.matches("\\d+")) {
            return html.naoEhNumero(numero);
        }

        if (new PrimeNumber().ehPrimo(Long.parseLong(numero))) {
            return html.primo(numero);
        }

        return html.naoEhPrimo(numero);
    }
}
