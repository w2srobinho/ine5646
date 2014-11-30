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
@WebServlet(name = "ServletPovoador", urlPatterns = {"/ServletPovoador"})
public class ServletPovoador extends HttpServlet {

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
        povoePaises(s);
        povoeCidades(s);
        povoeViagens(s);
        povoePassageiros(s);
        agendeViagens(s);

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>INE5646 - Turismo</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Banco Povoado!</h1>");
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

    private void povoePaises(ServicosCRUD s) {
        s.cadastrePais(new Pais("BRA", "Brasil"));
        s.cadastrePais(new Pais("FRA", "França"));
        s.cadastrePais(new Pais("EUA", "Estados Unidos da América"));
    }

    private void povoeCidades(ServicosCRUD s) {
        Pais brasil = s.encontrePaisPorCodigo("BRA");
        Pais franca = s.encontrePaisPorCodigo("FRA");
        Pais eua = s.encontrePaisPorCodigo("EUA");
        Cidade c;
        c = new Cidade("Rio de Janeiro");
        brasil.adicioneCidade(c);
        s.cadastreCidade(c);

        c = new Cidade("Paris");
        franca.adicioneCidade(c);
        s.cadastreCidade(c);

        c = new Cidade("Erexim");
        brasil.adicioneCidade(c);
        s.cadastreCidade(c);

        c = new Cidade("Nova Iorque");
        eua.adicioneCidade(c);
        s.cadastreCidade(c);
    }

    private void povoeViagens(ServicosCRUD s) {
        Cidade rio = s.encontreCidadePorNome("Rio de Janeiro");
        Cidade erexim = s.encontreCidadePorNome("Erexim");
        Viagem v;

        v = new Viagem();
        v.setCidade(rio);
        v.setNumDias(7);
        v.setPreco(5000);
        s.cadastreViagem(v);

        v = new Viagem();
        v.setCidade(rio);
        v.setNumDias(3);
        v.setPreco(4000);
        s.cadastreViagem(v);

        v = new Viagem();
        v.setCidade(erexim);
        v.setNumDias(10);
        v.setPreco(500);
        s.cadastreViagem(v);
    }

    private void povoePassageiros(ServicosCRUD s) {
        Passageiro p;

        p = new Passageiro();
        p.setId(1010L);
        p.setNome("Fulano de Tal");
        s.cadastrePassageiro(p);

        p = new Passageiro();
        p.setId(2010L);
        p.setNome("Irmão do Fulano de Tal");
        s.cadastrePassageiro(p);

        p = new Passageiro();
        p.setId(3010L);
        p.setNome("Beltrando Amarento");
        s.cadastrePassageiro(p);
    }

    private void agendeViagens(ServicosCRUD s) {
        List<Viagem> viagens = s.encontreTodasViagens();
        Long idViagem1 = viagens.get(0).getId();
        Long idViagem2 = viagens.get(1).getId();
        s.agendeViagem(1010L, idViagem1);
        s.agendeViagem(1010L, idViagem2);
        s.agendeViagem(2010L, idViagem1);
    }
}
