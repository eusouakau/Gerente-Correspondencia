package view;

import controle.Gerenciador;
import controle.Processador;

import javax.swing.*;
import java.util.Locale;


public class MainClass {

    public static void main (String[] args){

        Gerenciador.inicializarDAO();
        Gerenciador.InvocarTestes();
        Processador.direcionar("0");
    }
}
