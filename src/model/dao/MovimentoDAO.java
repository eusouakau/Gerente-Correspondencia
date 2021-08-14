package model.dao;

import model.Correspondencia;
import model.Destinatario;
import model.Movimento;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class MovimentoDAO implements OperacoesDAO {
    private final ArrayList<Movimento> movList; //lista de movimentos
    public static MovimentoDAO movDAO = null; //objeto da classe

    public MovimentoDAO(ArrayList<Movimento> movList) {
        // singleton
        this.movList = movList;
        //se destinatarioDAO não existe, ele recebe ele mesmo. se ele já existe, não podera ser instanciado outro
        if (movDAO == null) {
            movDAO = this;
        } else {
            movDAO = null;
        }
    }
   //pesquisa o movimento
    public Movimento pesquisar(Movimento obj)
    {
        Movimento movimento;
        Iterator<Movimento> itColecao = getMovList().iterator();

        while(itColecao.hasNext())
        {
            Movimento aux = itColecao.next();
            if(aux.equals(obj))
            {
                movimento = aux;
                return  movimento;
            }
        }

        return null;
    }

    // recebe como parametro o nome do destinatario e retorna os movimentos  referentes ao destinatario
    public ArrayList<Movimento> pesquisarLista(String destinatario)
    {
        ArrayList<Movimento> movimentos = new ArrayList<Movimento>();
        Movimento movimento;
        Iterator<Movimento> itColecao = getMovList().iterator();

        while(itColecao.hasNext())
        {
            Movimento aux = itColecao.next();
            Correspondencia corresp = aux.getCorrespondencia();
            Destinatario dest = corresp.getDestino();
            if(dest.getNome().equals(destinatario))
            {
                movimento = aux;
                movimentos.add(movimento);
            }
        }
        return  movimentos;
    }

    //insere movimento na lista
    @Override
    public void inserir(Object obj) {
        getMovList().add((Movimento) obj);
    }

    //remove movimento da lista
    @Override
    public void excluir(Object obj) {

        Iterator<Movimento> itColecao = getMovList().iterator();
        while(itColecao.hasNext())
        {
            if (itColecao.next().equals((Movimento) obj)) {
                itColecao.remove();
            }

        }
    }

    @Override
    public void editar(Object newObj) {
        Movimento novo = (Movimento) newObj;
        Iterator<Movimento> itColecao = getMovList().iterator();

        while(itColecao.hasNext())
        {
            Movimento movimento = (Movimento) itColecao;

            if (movimento.getChave() == novo.getChave())
            {
                movimento = novo;
                //editar(novo);
            }
        }
    }
    //retorna a lista de movimentos
    @Override
    public List pesquisar()
    {
        return movList;
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
        Iterator<Movimento> iterator = movList.iterator();
        while (iterator.hasNext())
        {
            Movimento movimento = iterator.next();
            String status = movimento.getCorrespondencia().getStatus() == false ? "Não entregue!" : "Entregue!";
            String elemento = "Data do movimento: " + movimento.verData() +
                    "\nStatus da correpondencia: " + status +
                    "\nDestinatario: " + movimento.getCorrespondencia().getDestino().getNome() + " Numero do imovel: "
                    + movimento.getCorrespondencia().getDestino().getNumeroImovel() + "\n";
            mensagem += elemento;
        }
        return mensagem;
    }

    @Override
    public int hashCode() {
        return Objects.hash(movList);
    }

    public ArrayList<Movimento> getMovList()
    {
        return movList;
    }

}