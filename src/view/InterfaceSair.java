/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controle.Comando;

import javax.swing.*;

/**
 *
 * @author karenborges
 */
public class InterfaceSair implements Comando {

    @Override
    public void executar() {
        int n = JOptionPane.showConfirmDialog(
                null, "Deseja encerrar o programa?",
                "Sair",
                JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, "Encerrando...");
            System.exit(0);
        } else if (n == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(null, "Então escolha outra opção...");
        }
    }
}
