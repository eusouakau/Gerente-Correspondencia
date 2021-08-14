package model.dao;

import model.Correspondencia;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class CorrespondenciaDAO implements OperacoesDAO {
    private final ArrayList<Correspondencia> correspList; //criando a lista
    public static CorrespondenciaDAO correspDAO = null; //criando objeto da classe

    public CorrespondenciaDAO(ArrayList<Correspondencia> correspList) {
        // singleton
        this.correspList = correspList;
        //se destinatarioDAO não existe, ele recebe ele mesmo. se ele já existe, não podera ser instanciado outro
        if (correspDAO == null) {
            correspDAO = this;
        } else {
            correspDAO = null;
        }
    }

    // adiciona correspondencia na lista
    @Override
    public void inserir(Object obj) {
        getCorrespList().add((Correspondencia) obj);
    }

    //remove correspondencia da lista
    @Override
    public void excluir(Object obj) {

        Iterator<Correspondencia> itColecao = getCorrespList().iterator();
        while(itColecao.hasNext())
        {
            if (itColecao.next().equals((Correspondencia) obj)) {
                itColecao.remove();
            }

        }
    }

    @Override
    public void editar(Object newObj) {
        Correspondencia novo = (Correspondencia) newObj;
        Iterator<Correspondencia> itColecao = getCorrespList().iterator();

        while(itColecao.hasNext())
        {
            Correspondencia correspondencia = (Correspondencia) itColecao;

            if (correspondencia.getChave() == novo.getChave())
            {
                correspondencia = novo;
                //editar(novo);
            }
        }
    }
    //pesquisa lista de correspondencia
    @Override
    public List pesquisar()
    {
        return correspList; //retorna a lista
    }

    //recebe o nome do destinatario e retorna lista de correspondencias do  destinatario
    public Correspondencia pesquisar(String obj)
    {
        Correspondencia correspondencia;
        Iterator<Correspondencia> itColecao = getCorrespList().iterator();
        while(itColecao.hasNext())
        {
            Correspondencia aux = itColecao.next();
            if(aux.getDestino().getNome().equals(obj))
            {
                correspondencia = aux;
                return  correspondencia;
            }
        }

        return null;
    }
    //recebe o nome do destinatario e retorna lista de correspondencias não entregues para este destinatario
    public ArrayList<Correspondencia> pesquisarNEntregues(String obj)
    {
        ArrayList<Correspondencia> correspondencia = new ArrayList<Correspondencia>();
        Iterator<Correspondencia> itColecao = getCorrespList().iterator();
        // verifica se ha correspondencias não entregues para o destinatario indicado
        while(itColecao.hasNext())
        {
            Correspondencia aux = itColecao.next();
            if(aux.getDestino().getNome().equals(obj))
            {
                if(aux.getStatus() == false) correspondencia.add(aux);
            }
        }
        // retorna correspondencia caso seja diferente de vazio
        if(!correspondencia.isEmpty()) return  correspondencia;

        return new ArrayList<Correspondencia>();
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
        String mensagem = "Lista de correspondencia: \n";
        Iterator<Correspondencia> iterator = correspList.iterator();
        while (iterator.hasNext())
        {
            Correspondencia correspondencia = iterator.next();
            String status = correspondencia.getStatus() == false ? "Não entregue!" : "Entregue!";
            String elemento = "Status da correpondencia: " + status +
                    "\nDestinatario: " + correspondencia.getDestino().getNome() + " Numero do imovel: "
                    + correspondencia.getDestino().getNumeroImovel() + "\n";
            mensagem += elemento;
        }
        return mensagem;
    }

    @Override
    public int hashCode() {
        return Objects.hash(correspList);
    }

    public ArrayList<Correspondencia> getCorrespList()
    {
        return correspList;
    }

}
