/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controle.Comando;
import controle.Gerenciador;
import controle.Processador;
import controle.TipoDado;
import model.Movimento;

import javax.swing.*;
import java.util.Locale;

import static controle.TipoDado.*;

/**
 *
 * @author Karen
 */
public class InterfacePrincipal implements Comando{

    public void executar() {

        String opcao = null;

        do {
            try{
                opcao = leDados("Escolha a opção:"
                        + "\n1 - Sair"
                        + "\n2 - Registrar Movimento de Entrada de Correspondência"
                        + "\n3 - Registrar Movimento de Saida de Correspondência"
                        + "\n4 - Pesquisar Se Existem Correspondências para Serem Entregues para um Determinado Destinatário"
                        + "\n5 - Listar todos os Movimentos de um Determinado Destinatário"
                        + "\n6 - Listar todos os Movimentos de um Determinada Data"
                        + "\n7 - Listar todos os Movimentos Registrados no Sistema"
                        + "\n8 - Inserir um Determinado Destinatário"
                        + "\n9 - Pesquisar Dados de um Determinado Destinatário"
                        + "\n10 - Excluir um Determinado Destinatário",
                        TipoDado.NUMERO);
                int op = Integer.parseInt(opcao);
                if(op < 1 || op > 10){
                    throw new NumberFormatException(opcao);
                }
                Processador.direcionar(opcao);
            } catch(CampoVazioException cve){
                JOptionPane.showMessageDialog(null,cve.getMessage() + " Digite novamente");
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, nfe.getMessage() + " Digite números validos!");
            }
        } while (opcao==null || !opcao.equals("0"));
    }
    
    public static String leDados(String mensagem, TipoDado tipo) throws CampoVazioException {
        String dado = JOptionPane.showInputDialog(null, mensagem);
        //verificação para cancel não fechar o programa
        if (dado == null)
        {
            JOptionPane.showMessageDialog(null, "Encerrando...");
            System.exit(0);
        }
        switch (tipo){

            case VAZIO -> {
                return null;
            }
            case NOME -> {
                if (dado.matches("^[0-9]*$") || dado.length() == 0) { // verifica se são letras e se o campo não esta vazio
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
