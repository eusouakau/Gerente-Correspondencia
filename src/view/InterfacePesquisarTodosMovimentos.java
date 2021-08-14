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

public class InterfacePesquisarTodosMovimentos implements Comando {
    @Override
    public void executar() {
        System.out.println("Esse é o resultado da funcionalidade 7");

        // criando um array list de movimentos dando get nos movimentos registrados MovDAO.
        ArrayList<Movimento> movimentos = Gerenciador.getMovDAO().getMovList();

        //Se não tem registro de movimentos, exibem mensagem abaixo
        if (movimentos.isEmpty()){
            int panel = JOptionPane.showConfirmDialog(null,"Nao ha registros de movimentos!", "Movimentos",JOptionPane.CLOSED_OPTION);
        }
        else{
            //Se tem registros, precisamos passar por cada um deles com o Iterator para pegar as informações.
            // contador vai exibir o numero do registro que tá sendo visto.
            // contado começa em 1 por que se entrou nesse else significa que tem pelo menos 1 registro.
            Iterator<Movimento> iterator = movimentos.iterator();
            int contador = 1;

            while (iterator.hasNext())
            {
                // A correpondencia ainda não foi retirada, significa que é um regristro de entrada (entra no if)
                // caso tenha sido retirada as informações exibidas são outras (entra no else)
                Movimento movimento = iterator.next();
                String data = movimento.verData();
                String quemRegistrou = movimento.getQuemRegistra();
                Correspondencia correspondencia = movimento.getCorrespondencia();
                boolean statusCorresp = correspondencia.getStatus();
                Destinatario destinatario = correspondencia.getDestino();
                String nomeDest = destinatario.getNome();
                String numImovel = destinatario.getNumeroImovel();
                String quemRetirou = movimento.getQuemRetira();

                //se o status da correspondencia for false apresenta movimento de entrada
                if (statusCorresp == true && quemRetirou != null)
                {
                    JOptionPane.showConfirmDialog(null,
                            "REGISTRO " + contador + " de " + movimentos.size() + "\n" +
                                    "TIPO: SAIDA \n\n\n" +
                                    "Destinatario: " + nomeDest + "\n" +
                                    "Numero do Imovel: " + numImovel + "\n\n" +
                                    "MOVIMENTAÇAO\n" +
                                    "Quem registrou: " + quemRegistrou + "\n" +
                                    "Quem retira: " + quemRetirou + "\n\n" +
                                    "DATA DO REGISTRO: " + data + "\n",
                            "Movimentos",
                            JOptionPane.CLOSED_OPTION);
                }
                //senão apresenta saida
                else {
                    JOptionPane.showConfirmDialog(null,
                            "REGISTRO " + contador + " de " + movimentos.size() + "\n" +
                                    "TIPO: ENTRADA \n\n\n" +
                                    "DESTINO\n" +
                                    "Destinatario: " + nomeDest + "\n" +
                                    "Numero do Imovel: " + numImovel + "\n\n" +
                                    "MOVIMENTAÇAO\n" +
                                    "Quem registrou: " + quemRegistrou + "\n\n" +
                                    "DATA DO REGISTRO: " + data + "\n",
                            "Movimentos",
                            JOptionPane.CLOSED_OPTION);
                }
                contador++;
            }

            //Depois de todos os registros exibidos, mostra a mensagem abaixo e direciona pra tela principal.
            int panel = JOptionPane.showConfirmDialog(null,"TODOS REGISTROS EXIBIDOS!", "Movimentos",JOptionPane.CLOSED_OPTION);
            Processador.direcionar("0");
        }

    }

    public String leDados(String mensagem, TipoDado tipo) throws CampoVazioException {
        String dado = JOptionPane.showInputDialog(null, mensagem);
        //verificação para cancel voltar para principal ao invés de fechar o programa
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
