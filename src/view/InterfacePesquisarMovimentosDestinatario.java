package view;

import controle.*;
import model.Correspondencia;
import model.Destinatario;
import model.Movimento;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;


public class InterfacePesquisarMovimentosDestinatario implements Comando{
    @Override
    public void executar() {
        System.out.println("Esse é o resultado da funcionalidade 5");

        String entradaNome = null;
        boolean teste = true;

        do {
            try {
                entradaNome = leDados("Nome do destinatario: ", TipoDado.NOME);
                teste = false;
            } catch (CampoVazioException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage() + " Digite um nome válido!");
            }
        } while (teste);
        //pesquisa destinatario pelo nome
        Destinatario destinatario = Gerenciador.getDestDAO().pesquisar(entradaNome);

        try {
            int aux = JOptionPane.showConfirmDialog(null, "Destinatario encontrado!\n"
                            + "Nome: " + destinatario.getNome() +
                            "\nNumero do imovel: " + destinatario.getNumeroImovel(),
                    "Pesquisar", JOptionPane.CLOSED_OPTION);

            // criando um arraylist de movimentos dando get nos movimentos registrados com o nome do Destinatario.
            ArrayList<Movimento> movimentos = Gerenciador.getMovDAO().pesquisarLista(destinatario.getNome());


            //Se não tem registro de movimentos, exibem mensagem abaixo
            if (movimentos.isEmpty()){
                int panel = JOptionPane.showConfirmDialog(null,"Nao ha registros de movimentos!", "Movimentos",JOptionPane.CLOSED_OPTION);
            }
            else {

                //Se tem registros, precisamos passar por cada um deles com o Iterator para pegar as informações.
                // contador vai exibir o numero do registro que tá sendo visto.
                // contador começa em 1 por que se entrou nesse else significa que tem pelo menos 1 registro.
                Iterator<Movimento> iterator = movimentos.iterator();
                int contador = 1;

                while (iterator.hasNext()) {
                    // A correpondencia ainda não foi retirada, significa que é um regristro de entrada (entra no if)
                    // caso tenha sido retirada as informações exibidas são outras (entra no else)
                    // A condição chave aqui é 'nomeDest' que é o nome do destinatário da correpondencia
                    // ser igual a 'entradaNome' que o usuário coloca.
                    Movimento movimento = iterator.next();
                    String data = movimento.verData();
                    String quemRegistrou = movimento.getQuemRegistra();
                    String quemRetirou = movimento.getQuemRetira();
                    Correspondencia correspondencia = movimento.getCorrespondencia();
                    boolean statusCorresp = correspondencia.getStatus();
                    destinatario = correspondencia.getDestino();
                    String nomeDest = destinatario.getNome();

                    if (statusCorresp == true && quemRetirou != null){
                        JOptionPane.showConfirmDialog(null,
                                "REGISTRO " + contador + " de " + movimentos.size() + " DO DESTINATARIO " + nomeDest + "\n" +
                                        "TIPO: SAIDA \n\n\n" +
                                        "MOVIMENTAÇAO\n" +
                                        "Quem registrou: " + quemRegistrou + "\n" +
                                        "Quem retira: " + quemRetirou + "\n\n" +
                                        "DATA DO REGISTRO: " + data + "\n",
                                "Movimentos",
                                JOptionPane.CLOSED_OPTION);
                    } else {
                        JOptionPane.showConfirmDialog(null,
                                "REGISTRO " + contador + " de " + movimentos.size() + " DO DESTINATARIO " + nomeDest + "\n" +
                                        "TIPO: ENTRADA \n\n\n" +

                                        "MOVIMENTAÇAO\n" +
                                        "Quem registrou: " + quemRegistrou + "\n\n" +
                                        "DATA DO REGISTRO: " + data + "\n",
                                "Movimentos",
                                JOptionPane.CLOSED_OPTION);
                    }
                    contador++;
                }

                //Depois de todos os registros exibidos, mostra a mensagem abaixo e direciona pra tela principal.
               JOptionPane.showConfirmDialog(null,
                        "TODOS OS REGISTROS DO DESTINATARIO " + entradaNome + " FORAM EXIBIDOS!",
                        "Movimentos",
                        JOptionPane.CLOSED_OPTION);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, " Destinatario nao encontrado!");
        }

        Processador.direcionar("0");

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
