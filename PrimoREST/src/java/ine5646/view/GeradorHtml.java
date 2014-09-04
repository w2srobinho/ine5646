package ine5646.view;

/**
 *
 * @author robinho
 */
public class GeradorHtml 
{
    private final String COR_PRIMO = "green";
    private final String COR_NAO_PRIMO = "orange";
    private final String COR_ERRO_NAO_EH_NUMERO = "red";

    private String getHtmlPage(String primoMsg) 
    {
        return "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<title>INE5646 - primo</title>\n"
                + "</head>\n"
                + "<body>\n"
                + "<h1>INE5646 - primo</h1>\n"
                + primoMsg + "\n"
                + "</body>\n"
                + "</html>";
    }

    private String resultadoFormatado(String cor, String numero, String msg) 
    {
        StringBuilder sb = new StringBuilder("");
        return getHtmlPage(sb.append("<h2 style='color : ").append(cor).append("'>")
                .append(numero).append(" : ").append(msg)
                .append("</h2>").toString());
    }

    public String naoEhNumero(String numero) 
    {
        return resultadoFormatado(
                COR_ERRO_NAO_EH_NUMERO,
                numero,
                "Não é um número!");
    }

    public String naoEhPrimo(String numero) 
    {
        return resultadoFormatado(
                COR_NAO_PRIMO,
                numero,
                "Não é um número primo.");
    }

    public String primo(String numero) 
    {
        return resultadoFormatado(
                COR_PRIMO,
                numero,
                "É um número primo.");
    }
}
