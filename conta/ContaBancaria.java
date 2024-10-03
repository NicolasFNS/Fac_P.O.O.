package conta;

import java.io.Serializable;

public abstract class ContaBancaria implements Serializable{
    private String ID;
    protected double saldo;

    public ContaBancaria(String ID) {
        this.ID = ID;
        this.saldo = 0.0;
    }

    public String getNumeroConta() {
        return ID;
    }

    public double getSaldo() {
        return saldo;
    }

    public abstract void depositar(double valor);
    public abstract void sacar(double valor) throws Exception;
}
