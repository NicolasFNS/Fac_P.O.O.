package acoes;

import java.io.Serializable;
import java.util.Date;

public class Transacao implements Serializable {
    private String tipo;
    private double valor;
    private Date data;

    public Transacao(String tipo, double valor) {
        this.tipo = tipo;
        this.valor = valor;
        this.data = new Date();
    }

    @Override
    public String toString() {
        return tipo + " de R$" + valor + " em " + data.toString();
    }
}

