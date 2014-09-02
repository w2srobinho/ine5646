/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ine5646.exemplorest;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import javax.websocket.server.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


/**
 * REST Web Service
 *
 * @author robinho
 */
@Path("divisores/{numero}")
public class DivisorREST {

    @GET
    @Produces("application/json")
    public String divisores(@PathParam("numero") int numero) {
        List<Integer> listaDeDivisores = new ArrayList<>();
        int metadeDoNumero = numero / 2;
        for (int i = 1; i <= metadeDoNumero; ++i) {
            if (numero % i == 0) {
                listaDeDivisores.add(i);
            }
        }
        listaDeDivisores.add(numero);
        return new Gson().toJson(listaDeDivisores);
    }
}
