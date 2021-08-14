package model;

import java.util.Objects;

/**
 * Write a description of class model.Correspondencia here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Correspondencia //Generalização ou classe mãe
{
    private int chave; //para guardar a chave do banco de dados
    private Destinatario destino;
    private boolean status; //TRUE é porque já foi retirado
    
    public Correspondencia (Destinatario destino){
        this.setDestino(destino);
        // por default status é inicializado com false.
    }    
     
    public void setStatus(boolean status){
        this.status = status;
    }

    public boolean getStatus() { return status; }
      
    public String toString() {
        String saida = "Chave = "+ getChave() + "\n" + getDestino().toString();
        if (status) saida += "Objeto já foi retirado";
        else saida += "Objeto ainda não foi retirado";
        return saida;        
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Correspondencia that = (Correspondencia) o;
        return (this.getChave() == that.getChave());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getChave(), getDestino(), status);
    }

    public int getChave() {
        return chave;
    }

    public void setChave(int chave) {
        this.chave = chave;
    }

    public Destinatario getDestino() {
        return destino;
    }

    public void setDestino(Destinatario destino) {
        this.destino = destino;
    }
}
