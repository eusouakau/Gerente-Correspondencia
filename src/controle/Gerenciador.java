package controle;

import model.*;
import model.dao.CorrespondenciaDAO;
import model.dao.DestinatarioDAO;
import model.dao.MovimentoDAO;

/* Gerenciador de DAOs*/
import java.util.ArrayList;

public class Gerenciador {

    private static DestinatarioDAO destDAO;
    private static CorrespondenciaDAO correspDAO;
    private static MovimentoDAO movDAO;

    public static void inicializarDAO() {
        ArrayList<Destinatario> destList = new ArrayList<Destinatario>();
        destDAO = new DestinatarioDAO(destList);

        ArrayList<Correspondencia> correspList = new ArrayList<Correspondencia>();
        correspDAO = new CorrespondenciaDAO(correspList);

        ArrayList<Movimento> movList = new ArrayList<Movimento>();
        movDAO = new MovimentoDAO(movList);

    }

    public static DestinatarioDAO getDestDAO() {
        return destDAO;
    }
    public static CorrespondenciaDAO getCorrespDAO() {
        return correspDAO;
    }
    public static MovimentoDAO getMovDAO() {
        return movDAO;
    }

// testes automatizados

    public static void InvocarTestes()
    {
        Destinatario destinatario;
        Correspondencia correspondencia;
        Movimento movimento;

       destinatario = new Destinatario("KAU", "21");
       destDAO.inserir(destinatario);
       destinatario = new Destinatario("LIDI", "489");
       destDAO.inserir(destinatario);

       correspondencia = new Carta(destDAO.pesquisar("KAU"), true);
       correspDAO.inserir(correspondencia);
       movimento = new Movimento(correspondencia, "ADMIR");
       movDAO.inserir(movimento);

       correspondencia = new Pacote(destDAO.pesquisar("LIDI"), "AABB");
       correspDAO.inserir(correspondencia);
       movimento = new Movimento(correspondencia, "ALEX");
       movDAO.inserir(movimento);

       correspondencia = new Pacote(destDAO.pesquisar("KAU"), "TOPS");
       correspDAO.inserir(correspondencia);
       movimento = new Movimento(correspondencia, "ADMIR");
       movDAO.inserir(movimento);

       correspondencia = new Carta(destDAO.pesquisar("LIDI"), false);
       correspDAO.inserir(correspondencia);
       movimento = new Movimento(correspondencia, "ALEX");
       movDAO.inserir(movimento);

       correspondencia = new Carta(destDAO.pesquisar("KAU"), true);
       correspDAO.inserir(correspondencia);
       movimento = new Movimento(correspondencia, "ADMIR");
       movDAO.inserir(movimento);
    }
}
