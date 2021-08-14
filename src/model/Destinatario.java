package model;

import java.util.Objects;

/**
 * Write a description of class model.Destinatario here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Destinatario
{
    private int chave; //para guardar a chave do banco
    private String nome; 
    private String numeroImovel;

    public Destinatario(String nome, String numeroImovel)
    {
            this.nome = nome;
            this.numeroImovel = numeroImovel;
    }
    
    public String getNome(){
        return nome;
    }
        
    public String getNumeroImovel(){
        return numeroImovel;
    }
    
    public String toString(){
        return ("Chave = " + chave + "\nNome = "+ nome + "\nNumero do Imóvel = "+ numeroImovel);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Destinatario that = (Destinatario) o;
        return this.nome.equals(that.nome) && numeroImovel.equals(that.numeroImovel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, numeroImovel);
    }

    public int getChave() {
        return chave;
    }

    public void setChave(int chave) {
        this.chave = chave;
    }
}
