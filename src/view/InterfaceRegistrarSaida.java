package view;

import javax.swing.*;
import controle.Comando;
import controle.Gerenciador;
import controle.Processador;
import controle.TipoDado;
import model.*;

import java.util.ArrayList;
import java.util.Iterator;

public class InterfaceRegistrarSaida implements Comando {

    public void executar() {
        String quemRegistra = null;
        String quemRetira = null;
        String nomeDest = null;
        Destinatario destinatario = null;
        Movimento movimento = null;
        // a data do movimento é registrada automaticamente quando o movimento é registrado

        boolean teste = true;

        teste = true;
        do {
            try {
                quemRetira = leDados("Nome de quem esta retirando a correspondencia: ", TipoDado.NOME);
                teste = false;
            } catch (CampoVazioException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage() + " Digite algo!");
            }
        } while (teste);

        teste = true;
        do {
            try {
                quemRegistra = leDados("Informe o nome de quem esta registrando: ", TipoDado.NOME);
                teste = false;
            } catch (CampoVazioException ex) {
                JOptionPane.showMessageDialog(null, " Digite algo!");
            }
        } while (teste);

        teste = true;
        do {
            try {
                nomeDest = leDados("Informe o nome do destinatario: ", TipoDado.NOME);
            } catch (CampoVazioException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage() + " Destinatario invalido!");
            }
            //pesquisa destinatario
            destinatario = Gerenciador.getDestDAO().pesquisar(nomeDest);

            try {
                int aux = JOptionPane.showConfirmDialog(null, "Destinatario encontrado!\n"
                                + "Nome: " + destinatario.getNome() +
                                "\nNumero do imovel: " + destinatario.getNumeroImovel(),
                        "Pesquisar", JOptionPane.CLOSED_OPTION);
                teste = false;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, " Destinatario nao encontrado!");
            }
        } while (teste);

        System.out.println("Esse é o resultado da funcionalidade 2");
        //lista correspondencias não entregues do destinatario pesquisado
        ArrayList<Correspondencia> correspondencias = Gerenciador.getCorrespDAO().pesquisarNEntregues(destinatario.getNome());
         //se a lista estiver vazia
        if (correspondencias.isEmpty())
        {
            JOptionPane.showConfirmDialog(null,
                    "Nao ha correspondendias a serem retiradas para esse destinatario!",
                    "Saida",
                    JOptionPane.CLOSED_OPTION);
        }
        else{
            //apresenta a quantidade de corrrespondencias para o destinatario
            JOptionPane.showConfirmDialog(null,
                    "Ha " + correspondencias.size() + " correspondencias pra esse destinatario",
                    "Saida",
                    JOptionPane.CLOSED_OPTION);
            //verifica correspondencias por correspondencia
            Iterator<Correspondencia> iterator = correspondencias.iterator();
            while (iterator.hasNext())
            {
                Correspondencia correspondencia = iterator.next();
                movimento = new Movimento(correspondencia, quemRetira, quemRegistra);
                Gerenciador.getMovDAO().inserir(movimento);

                //DEBUG
                String destAux = movimento.getCorrespondencia().getDestino().getNome();
                String debug = Gerenciador.getMovDAO().pesquisar(movimento).getQuemRetira();
                System.out.println(debug);

            }

            JOptionPane.showConfirmDialog(null,
                    "Movimento de saida de correspondencia registrado com sucesso!",
                    "Saida",
                    JOptionPane.CLOSED_OPTION);
        }
    }

    public String leDados(String mensagem, TipoDado tipo) throws CampoVazioException {
        String dado = JOptionPane.showInputDialog(null, mensagem);

        if (dado == null)
        {
            Processador.direcionar("0");
        }

        switch (tipo){

            case VAZIO -> {
                return null;
            }
            case NOME -> {
                if (dado.matches("^[0-9]*$") || dado.length() == 0) { // verifica se são letras
                    throw new CampoVazioException(mensagem);
                } else {
                    dado = dado.toUpperCase(); //padronização dos nomes
                    return dado;
                }
            }
            case NUMERO -> {
                if (dado.length() == 0) {
                    throw new CampoVazioException(mensagem);
                } else if(!dado.matches("^[0-9]*$") ){ // verifica se são numeros
                    throw new NumberFormatException(mensagem);
                } else {
                    return dado;
                }
            }
        }
        return null;
    }
}

