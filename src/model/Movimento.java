package model;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 * Write a description of class model.Movimento here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Movimento
{
    private int chave; //para registrar a chave do banco de dados
    private Correspondencia correspondencia;
    private String quemRegistra; //funcionario do condominio
    private Calendar data;
    private String quemRetira; //Só para movimento de saída

    //model.Movimento de recebimento da correspondencia pela portaria
    public Movimento(Correspondencia correspondencia, String quemRegistra) {
        this.setCorrespondencia(correspondencia);
        this.setQuemRegistra(quemRegistra);
        setData(new GregorianCalendar());

    }

    //model.Movimento de entrega da correspondencia para o destinatário
    public Movimento(Correspondencia correspondencia, String quemRetira, String quemRegistra) {
        //Foi passado a correspondencia na saída por que ao registrar os movimentos de saída estava retornando
        //null no correspondencia de Movimento. A lógica utilizada necessitava desse dado.
        this.setCorrespondencia(correspondencia);

        //o status da correspondencia muda automaticamente no movimento de saida
        correspondencia.setStatus(true);
        this.setQuemRetira(quemRetira);
        this.setQuemRegistra(quemRegistra);
        setData(new GregorianCalendar());

    }

    public String verData(){
        return (data.get(Calendar.DAY_OF_MONTH) +"/"+ (data.get(Calendar.MONTH)+1) + "/" + data.get(Calendar.YEAR));
    }

    public Calendar getData(){
        return this.data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }

    public Correspondencia getCorrespondencia() {
        return correspondencia;
    }

    public void setCorrespondencia(Correspondencia correspondencia) {
        this.correspondencia = correspondencia;
    }

    public String getQuemRegistra() {
        return quemRegistra;
    }

    public void setQuemRegistra(String quemRegistra) {
        this.quemRegistra = quemRegistra;
    }

    public String getQuemRetira() {
        return quemRetira;
    }

    public void setQuemRetira(String quemRetira) {
        this.quemRetira = quemRetira;
    }

    public int getChave() {
        return chave;
    }

    public void setChave(int chave) {
        this.chave = chave;
    }

    @Override
    public String toString() {
        return "Movimento{" +
                "chave=" + chave +
                ", correspondencia=" + correspondencia +
                ", quemRegistra='" + quemRegistra + '\'' +
                ", data=" + verData() +
                ", quemRetira='" + quemRetira + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movimento movimento = (Movimento) o;
        return chave == movimento.chave && correspondencia.equals(movimento.correspondencia) && getData().equals(movimento.getData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(chave, correspondencia, getData());
    }


}
