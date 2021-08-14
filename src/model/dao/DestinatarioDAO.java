package model.dao;

import model.Destinatario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class DestinatarioDAO implements OperacoesDAO {
    private final ArrayList<Destinatario> destList; // lista de  destinatario
    public static DestinatarioDAO destDAO = null; //objeto da classe

    public DestinatarioDAO(ArrayList<Destinatario> destList) {
        // singleton
        this.destList = destList;
        //se destinatarioDAO não existe, ele recebe ele mesmo. se ele já existe, não podera ser instanciado outro
        if (destDAO == null) {
            destDAO = this;
        } else {
            destDAO = null;
        }
    }

    //insere novo destinatario
    @Override
    public void inserir(Object obj) {
        getDestList().add((Destinatario) obj);
    }
    //remove destinatario
    @Override
    public void excluir(Object obj) {

        Iterator<Destinatario> itColecao = getDestList().iterator();
        while(itColecao.hasNext())
        {
            if (itColecao.next().equals((Destinatario) obj)) {
                itColecao.remove();
            }

        }
    }

    @Override
    public void editar(Object newObj) {
        Destinatario novo = (Destinatario) newObj;
        Iterator<Destinatario> itColecao = getDestList().iterator();

        while(itColecao.hasNext())
        {
            Destinatario destinatario = (Destinatario) itColecao;

            if (destinatario.getChave() == novo.getChave())
            {
                destinatario = novo;
                        //editar(novo);
            }
        }
    }
    //pesquisa lista de destinatario
    @Override
    public List pesquisar()
    {
        return destList; //retorna a lista
    }
    //recebe destinatario e retorna destinatario
    public Destinatario pesquisar(Destinatario obj)
    {
        Iterator<Destinatario> itColecao = getDestList().iterator();
        //verifica se corresponde a algum destinatario cadastrado no sistema
        while(itColecao.hasNext())
        {
            Destinatario destinatario = itColecao.next();
            if(destinatario.equals(obj))
            {
                return  destinatario;
            }
        }

        return null;
    }

    //recebe como parametro o nome do destinatario e retorna o objeto destinatario correspondente
    public Destinatario pesquisar(String obj)
    {
        Destinatario destinatario;
        Iterator<Destinatario> itColecao = getDestList().iterator();
        //verifica se o nome digitado corresponde a algum destinatario cadastrado no sistema
        while(itColecao.hasNext())
        {
            Destinatario aux = itColecao.next();
            if(aux.getNome().equals(obj))
            {
                destinatario = aux;
                return  destinatario;
            }
        }

        return null;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }

        return true;
    }

    @Override
    public String toString(){
        String mensagem = "Lista de destinatários: \n";
        Iterator<Destinatario> iterator = destList.iterator();
        while (iterator.hasNext())
        {
            Destinatario destinatario = iterator.next();
            String elemento = "Destinatario: " + destinatario.getNome() + " Numero do imovel: " + destinatario.getNumeroImovel() + "\n";
            mensagem += elemento;
        }
        return mensagem;
    }

    @Override
    public int hashCode() {
        return Objects.hash(destList);
    }

    public ArrayList<Destinatario> getDestList()
    {
        return destList;
    }

}
