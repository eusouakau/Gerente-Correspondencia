package model.dao;

import java.util.List;
/*
 * OperacoesDAO.java
 *
 * Created on 16 de Outubro de 2006, 21:21
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author kborges
 */
public interface OperacoesDAO {
    
    void inserir (Object obj);
    
    void excluir (Object obj);
    
    void editar (Object newObj);

    List pesquisar();
    
}
