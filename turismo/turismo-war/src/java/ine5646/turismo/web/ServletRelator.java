package ine5646.turismo.web;

import ine5646.turismo.logica.entidades.Cidade;
import ine5646.turismo.logica.entidades.Pais;
import ine5646.turismo.logica.entidades.Passageiro;
import ine5646.turismo.logica.entidades.Viagem;
import ine5646.turismo.logica.servicos.ServicosCRUD;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Leandro
 */
@WebServlet(name = "ServletRelator", urlPatterns = {"/ServletRelator"})
public class ServletRelator extends HttpServlet {

    @EJB
    ServicosCRUD s;

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>INE5646 - Turismo</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Dados Cadastrados no Banco</h1>");
            mostrePaises(s, out);
            mostreCidades(s, out);
            mostreViagens(s, out);
            mostrePassageiros(s, out);
            out.println("</body>");
            out.println("</html>");
        }
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

    private void mostrePaises(ServicosCRUD s, PrintWriter out) {
        List<Pais> paises = s.encontreTodosPaises();
        out.println("<h1>Paises</h1>");
        out.println("<ol>");
        for (Pais p : paises) {
            out.println("<li>" + p.getId() + " - " + p.getNome() + "</li>");
        }
        out.println("</ol>");
    }

    private void mostreCidades(ServicosCRUD s, PrintWriter out) {
        List<Cidade> cidades = s.encontreTodasCidades();
        out.println("<h1>Cidades</h1>");
        out.println("<ol>");
        for (Cidade c : cidades) {
            out.println("<li>" + c.getId() + " - " + c.getNome() + " - " + c.getPais().getNome() + "</li>");
        }
        out.println("</ol>");
    }

    private void mostreViagens(ServicosCRUD s, PrintWriter out) {
        List<Viagem> viagens = s.encontreTodasViagens();
        out.println("<h1>Viagens</h1>");
        out.println("<ol>");
        for (Viagem v : viagens) {
            out.println("<li>Id: " + v.getId() + " Nome: " + v.getCidade().getNome() + " (" + v.getCidade().getPais().getNome() + ") Num dias: " + v.getNumDias() + " Preço: " + v.getPreco() + "<br>");
            if (v.getPassageiros().isEmpty()) {
                out.println("<p>Sem passageiros.</p>");
            } else {
                out.println("<h3>Passageiros</h3>");
                out.println("<ol>");
                for (Passageiro p : v.getPassageiros()) {
                    out.println("<li>" + p.getId() + " - " + p.getNome() + "</li>");
                }
                out.println("</ol>");
                out.println("<br>");
            }
        }
        out.println("</ol>");
    }

    private void mostrePassageiros(ServicosCRUD s, PrintWriter out) {
        List<Passageiro> passageiros = s.encontreTodosPassageiros();
        out.println("<h1>Passageiros</h1>");
        out.println("<ol>");
        for (Passageiro p : passageiros) {
            out.println("<li>CPF: " + p.getId() + " Nome: " + p.getNome() + "<br>");
            if (p.getViagens().isEmpty()) {
                out.println("<p>Sem viagens.</p>");
            } else {
                out.println("<h3>Viagens</h3>");
                out.println("<ol>");
                for (Viagem v : p.getViagens()) {
                    out.println("<li>" + v.getId() + " - " + v.getCidade().getNome() + " (" + v.getCidade().getPais().getNome() + ") Num dias: " + v.getNumDias() + " Preço: " + v.getPreco() + "</li>");
                }
                out.println("</ol>");
                out.println("<br>");
            }
        }
        out.println("</ol>");
    }

}
