package view;

import controle.Comando;
import controle.Gerenciador;
import controle.Processador;
import controle.TipoDado;
import model.Correspondencia;
import model.Destinatario;
import model.Movimento;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;

public class InterfaceExcluirDestinatario implements Comando {

    @Override
    public void executar() {
        System.out.println("Esse é o resultado da funcionalidade 10");

        String entradaNome = null;
        boolean teste = true;
        // entrada
        do {
            try {
                entradaNome = leDados("Nome do destinatario: ", TipoDado.NOME);
                teste = false;
            } catch (CampoVazioException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage() + " Digite um nome válido!");
            }
        } while (teste);



        Destinatario destinatario = Gerenciador.getDestDAO().pesquisar(entradaNome);

        try {
            int aux = JOptionPane.showConfirmDialog(null, "Destinatario a ser excuído:\n"
                            + "Nome: " + destinatario.getNome() +
                            "\nNumero do imovel: " + destinatario.getNumeroImovel(),
                    "Excluir", JOptionPane.CLOSED_OPTION);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, " Destinatario nao encontrado!");
        }
        //removendo movimentos do destinatario que será excluido na sequencia
        try {
            ArrayList<Movimento> movimentos = new ArrayList<Movimento>();
            movimentos = Gerenciador.getMovDAO().getMovList();
            Iterator<Movimento> iterator = movimentos.iterator();
            while(iterator.hasNext()){
                Movimento mov = iterator.next();
                Correspondencia corresp = mov.getCorrespondencia();
                Destinatario dest = corresp.getDestino();
                String nomeDest = dest.getNome();
                //verifica se entrada é igual  a um nome de destinatario existente, se for remove o movimento da lista
                if(nomeDest.equals(destinatario.getNome())){
                    iterator.remove();
                }
            }
            //apos remover movimentos do destinatario, remove o destinatario
            Gerenciador.getDestDAO().excluir(destinatario);
            int aux = JOptionPane.showConfirmDialog(null, "Destinatario excluído!\n",
                    "Excluir", JOptionPane.CLOSED_OPTION);
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
                if (dado.matches("^[0-9]*$") || dado.length() == 0) { // verifica se são letras e se o campo não está vazio
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
