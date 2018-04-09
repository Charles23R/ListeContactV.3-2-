import java.io.Serializable;

/**
 * Created by RenCh1732786 on 2018-01-22.
 */
public class Telephone implements Serializable {

    private String info=" ";
    private String numero=" ";

    //Getters
    public String getInfo() {
        return info;
    }

    public String getNumero() {
        return numero;
    }


    //Setters
    public void setInfo(String info) {
        this.info = info;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
