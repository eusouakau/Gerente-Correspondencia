package view;

import controle.Comando;
import controle.Gerenciador;
import controle.Processador;
import controle.TipoDado;
import model.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;

public class InterfacePesquisarCorrespondencia implements Comando{

    @Override
    public void executar() {
        System.out.println("Esse e o resultado da funcionalidade 9");

        String entradaNome = null;
        ArrayList<Correspondencia> correspNEntregue = null;
        boolean teste = true;

        do {
            try {
                entradaNome = leDados("Nome do destinatario: ", TipoDado.NOME);
                teste = false;
            } catch (CampoVazioException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage() + " Digite um nome válido!");
            }
        } while (teste);
        //pesquisa destinatario para identificar correspondecia
        Destinatario destinatario = Gerenciador.getDestDAO().pesquisar(entradaNome);

        try {
            int aux = JOptionPane.showConfirmDialog(null, "Destinatario encontrado!\n" + "Nome: " + destinatario.getNome() +
                            "\nNumero do imovel: " + destinatario.getNumeroImovel(), "Pesquisar", JOptionPane.CLOSED_OPTION);
            //pesquisa a lista de correspondencias não entregues do destinatario
            correspNEntregue = Gerenciador.getCorrespDAO().pesquisarNEntregues(destinatario.getNome());
            Iterator<Correspondencia> itCorresp = correspNEntregue.iterator();
            int contCarta = 0;
            int contPacote = 0;
            while(itCorresp.hasNext()){
                //verifica o tipo de correspondencia e contabiliza
                if (itCorresp.next().getClass() == Carta.class) contCarta++;
                else contPacote++;
            }

            JOptionPane.showConfirmDialog(null, "O destinatario " + destinatario.getNome() + " possui: " +
                    contCarta + " cartas e " + contPacote + " pacotes, totalizando " + (contCarta + contPacote) + " correspondencias nao entregues.", "Pesquisa", JOptionPane.CLOSED_OPTION);


        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, " Destinatario nao encontrado!");
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
