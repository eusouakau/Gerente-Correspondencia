package view;

import controle.Comando;
import controle.Gerenciador;
import controle.Processador;
import controle.TipoDado;
import model.Destinatario;

import javax.swing.*;

public class InterfaceInserirDestinatario implements Comando {
    @Override
    public void executar() {
        System.out.println("Esse é o resultado da funcionalidade 8");

            String entradaNome = null;
            String entradaNum = null;
            boolean teste = true;

            do {
                try {
                    entradaNome = leDados("Nome do destinatario: ", TipoDado.NOME);
                    teste = false;
                } catch (CampoVazioException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() + " Digite um nome válido!");
                }
            } while (teste);

            teste = true;
            do {
                try {
                    entradaNum = leDados("Numero do imovel (Destinatario): ", TipoDado.NUMERO);
                    teste = false;
                } catch (CampoVazioException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() + " Digite algo!");
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, nfe.getMessage() + " Digite apenas números!");
                }

            } while (teste);


            Destinatario destinatario = new Destinatario(entradaNome, entradaNum);
            if(destinatario.equals(Gerenciador.getDestDAO().pesquisar(destinatario))){
                JOptionPane.showConfirmDialog(null,
                        "O destinatário já existe!",
                        "Cadastro",
                        JOptionPane.CLOSED_OPTION);
            }else{
                Gerenciador.getDestDAO().inserir(destinatario);
                JOptionPane.showConfirmDialog(null,
                        "Destinatario cadastrado com sucesso!",
                        "Cadastro",
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
