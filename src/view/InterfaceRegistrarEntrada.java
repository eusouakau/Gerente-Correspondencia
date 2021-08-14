package view;

import javax.swing.*;
import controle.Comando;
import controle.Gerenciador;
import controle.Processador;
import controle.TipoDado;
import model.*;

public class InterfaceRegistrarEntrada implements Comando {

    public void executar() {
        String quemRegistra = null;
        String nomeDest = null;
        String tipoCorrespondencia = null;
        Destinatario destinatario = null;
        Correspondencia correspondencia = null;
        Movimento movimento = null;
        // a data do movimento é registrada automaticamente quando o movimento é registrado
        boolean teste = true;

        do {
            try {
                nomeDest = leDados("Informe o nome do destinatario: ", TipoDado.NOME);
            } catch (CampoVazioException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage() + " Destinatario invalido!");
            }

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

        teste = true;
        do {
            try {
                quemRegistra = leDados("Informe o nome de quem esta registrando a correspondencia:", TipoDado.NOME);
                teste = false;
            } catch (CampoVazioException ex) {
                JOptionPane.showMessageDialog(null, " Digite algo!");
            }
        } while (teste);


            System.out.println("Esse é o resultado da funcionalidade 2");

        teste = true;
        do {
            try {
                tipoCorrespondencia = leDados("Tipo de correspondencia: \n 1 - Carta \n 2 - Pacote", TipoDado.NUMERO);
                int i = Integer.parseInt(tipoCorrespondencia);
                //verifica o tipo de correspondencia
                if (i == 1) {
                    boolean recibo = false;
                    correspondencia = null;

                    teste = true;
                    do {
                        //confirmação de recebimento de recibo
                        int painel = JOptionPane.showConfirmDialog(null, "A carta possui recibo? ", "Recibo", JOptionPane.YES_NO_OPTION);
                        if (painel == JOptionPane.YES_OPTION)
                        {
                            recibo = true;
                        }
                        else if(painel == JOptionPane.NO_OPTION)
                        {
                            recibo = false;
                        }

                        teste = false;

                    } while (teste);

                    correspondencia = new Carta(destinatario, recibo);
                }
                if (i == 2) {
                    String empresa = null;
                    teste = true;
                    do {
                        try {
                            empresa = leDados("Informe a empresa que esta entregando o pacote: ", TipoDado.NOME);
                            teste = false;
                        } catch (CampoVazioException ex) {
                            JOptionPane.showMessageDialog(null, " Digite algo");
                        }
                    } while (teste);
                    correspondencia = new Pacote(destinatario, empresa);
                }
                teste = false;
            } catch (CampoVazioException ex) {
                JOptionPane.showMessageDialog(null,  "Digite algo!");
            } catch (NumberFormatException nfe){
                JOptionPane.showMessageDialog(null,  " Isso não é um número válido!");
            }
        } while (teste);
        //adiciona correspondencia a lista de correspondencias
        Gerenciador.getCorrespDAO().inserir(correspondencia);
        //cria novo movimento
        movimento = new Movimento(correspondencia, quemRegistra);
        //adiciona movimento na lista de movimentos
        Gerenciador.getMovDAO().inserir(movimento);
        JOptionPane.showConfirmDialog(null,"Movimento de entrada de correspondencia registrado com sucesso!","Entrada", JOptionPane.CLOSED_OPTION);

        System.out.println(Gerenciador.getMovDAO().pesquisar(movimento).getQuemRegistra());

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
                } else if(!dado.matches("^[0-9]*$") ){  // verifica se são numeros
                    throw new NumberFormatException(mensagem);
                } else {
                    return dado;
                }
            }
        }
        return null;
    }
}
