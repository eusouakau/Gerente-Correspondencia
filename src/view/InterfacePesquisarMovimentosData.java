package view;

import controle.Comando;
import controle.Gerenciador;
import controle.Processador;
import controle.TipoDado;
import model.Correspondencia;
import model.Destinatario;
import model.Movimento;

import javax.swing.*;
import java.util.*;


public class InterfacePesquisarMovimentosData implements Comando {
    @Override
    public void executar() {
        System.out.println("Esse é o resultado da funcionalidade 6");
        //instanciando calendario
        Calendar data = new GregorianCalendar();
        int dia = 0;
        int mes = 0;
        int ano = 0;
        boolean teste = true;

        // do while para pegar o dia, mes e ano para a pesquisa.
        // cada um deles tem testes para pegar valores dentro do limite.

        do {
            try {
                //loop enquanto dia for menor que 1 ou maior que 31
                while (dia < 1 || dia > 31)
                {
                    String sDia = leDados("Informe o dia: (dd) ", TipoDado.NUMERO);
                    dia = Integer.parseInt(sDia);
                    //verifica se o dia esta dentro do intervalo valido
                    if (dia < 1 || dia > 31)
                    {
                        JOptionPane.showConfirmDialog(
                                null,
                                "Esse numero não contepla os dias de um mês!",
                                "Pesquisa",
                                JOptionPane.CLOSED_OPTION);
                    }
                }
                teste = false;
            } catch (CampoVazioException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage() + " Digite algo!");
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, nfe.getMessage() + " Isso não é um número inteiro!");
            }
        } while (teste);

        teste = true;
        do {
            try {
                //loop enquanto mes for menor que 1 e maior que 12
                while (mes < 1 || mes > 12)
                {
                    String sMes = leDados("Informe o mês: (mm) ", TipoDado.NUMERO);
                    mes = Integer.parseInt(sMes);
                     //verifica se esta no intervalo de mes valido
                    if (mes < 1 || mes > 12)
                    {
                        JOptionPane.showConfirmDialog(
                                null,
                                "Esse numero não contepla os meses de um ano!",
                                "Pesquisa",
                                JOptionPane.CLOSED_OPTION);
                    }
                }
                teste = false;
            } catch (CampoVazioException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage() + " Digite algo!");
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, nfe.getMessage() + " Isso não é um número inteiro!");
            }
        } while (teste);

        teste = true;
        do {
            try {
                //loop enquanto ano for maior que 2000 e menor que 2100
                while (ano < 2000 || ano > 2100)
                {
                    String sAno = leDados("Informe o ano: (aaaa)", TipoDado.NUMERO);
                    ano = Integer.parseInt(sAno);
                    //verifica se esta no intervalo valido de anos
                    if (ano < 2000 || ano > 2100)
                    {
                        JOptionPane.showConfirmDialog(
                                null,
                                "Esse numero não contepla um ano!",
                                "Pesquisa",
                                JOptionPane.CLOSED_OPTION);
                    }
                }
                teste = false;
            } catch (CampoVazioException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage() + " Digite algo!");
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, nfe.getMessage() + " Isso não é um número inteiro!");
            }
        } while (teste);

        //montando a data na variável data
        data.set(ano,mes - 1,dia);

        //ArrayList de todos os movimento e outro para salvar somente os que tem a data que o usuário deu entrada.
        //Iteretor para percorrer o ArrayList movimentos.
        ArrayList<Movimento>  movimentos = Gerenciador.getMovDAO().getMovList();
        ArrayList<Movimento>  movimentosNaData = new ArrayList<Movimento>();
        Iterator<Movimento> iterator = movimentos.iterator();

        while(iterator.hasNext())
        {
            Movimento movimento = iterator.next();
            Calendar movData = movimento.getData();
            if (movData.get(Calendar.YEAR) == data.get(Calendar.YEAR))
                if (movData.get(Calendar.MONTH) == data.get(Calendar.MONTH))
                    if (movData.get(Calendar.DAY_OF_MONTH) == data.get(Calendar.DAY_OF_MONTH))
                    {
                        movimentosNaData.add(movimento);
                    }
        }

        try {
            //Se não tem registro de movimentos, exibem mensagem abaixo
            if (movimentosNaData.isEmpty()){
                int panel = JOptionPane.showConfirmDialog(null,"Nao ha registros de movimentos na data!", "Movimentos",JOptionPane.CLOSED_OPTION);
            }
            else {

                int aux = JOptionPane.showConfirmDialog(
                        null,
                        "Há "+ movimentosNaData.size() + " registros nessa data!",
                        "Pesquisar",
                        JOptionPane.CLOSED_OPTION);

                //Se tem registros, precisamos passar por cada um deles com o Iterator para pegar as informações.
                // contador vai exibir o numero do registro que tá sendo visto.
                // contador começa em 1 por que se entrou nesse else significa que tem pelo menos 1 registro.
                Iterator<Movimento> iteratorNaData = movimentosNaData.iterator();
                int contador = 1;

                while (iteratorNaData.hasNext()) {
                    // A correpondencia ainda não foi retirada, significa que é um regristro de entrada (entra no if)
                    // caso tenha sido retirada as informações exibidas são outras (entra no else)
                    // A condição chave aqui é 'nomeDest' que é o nome do destinatário da correpondencia
                    // ser igual a 'entradaNome' que o usuário coloca.
                    Movimento movimento = iteratorNaData.next();
                    String sData = movimento.verData();
                    String quemRegistrou = movimento.getQuemRegistra();
                    String quemRetirou = movimento.getQuemRetira();
                    Correspondencia correspondencia = movimento.getCorrespondencia();
                    boolean statusCorresp = correspondencia.getStatus();
                    Destinatario destinatario = correspondencia.getDestino();
                    String nomeDest = destinatario.getNome();
                    String numImovel = destinatario.getNumeroImovel();

                    if (statusCorresp == true && quemRetirou != null){
                        JOptionPane.showConfirmDialog(null,
                                "REGISTRO " + contador + " de " + movimentos.size() + " DO DATA " + sData + "\n" +
                                        "TIPO: SAIDA \n\n\n" +
                                        "Destinatario: " + nomeDest + "\n" +
                                        "Numero do Imovel: " + numImovel + "\n\n" +
                                        "MOVIMENTAÇAO\n" +
                                        "Quem registrou: " + quemRegistrou + "\n" +
                                        "Quem retira: " + quemRetirou + "\n\n",
                                "Movimentos",
                                JOptionPane.CLOSED_OPTION);
                    } else {
                        JOptionPane.showConfirmDialog(null,
                                "REGISTRO " + contador + " de " + movimentos.size() + " DO DATA " + sData + "\n" +
                                        "TIPO: ENTRADA \n\n\n" +
                                        "DESTINO\n" +
                                        "Destinatario: " + nomeDest + "\n" +
                                        "Numero do Imovel: " + numImovel + "\n\n" +
                                        "MOVIMENTAÇAO\n" +
                                        "Quem registrou: " + quemRegistrou + "\n\n",
                                "Movimentos",
                                JOptionPane.CLOSED_OPTION);
                    }
                    contador++;
                }

                //Depois de todos os registros exibidos, mostra a mensagem abaixo e direciona pra tela principal.
                JOptionPane.showConfirmDialog(null,
                        "TODOS OS REGISTROS DA DATA FORAM EXIBIDOS!",
                        "Movimentos",
                        JOptionPane.CLOSED_OPTION);
            }
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "Formato de data incorreto!");
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
