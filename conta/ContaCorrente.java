package conta;

import java.util.ArrayList;
import java.util.List;
import acoes.*;
import exception.SaldoInsuficienteException;

public class ContaCorrente extends ContaBancaria implements Tributavel {
    private static final double TAXA_SAQUE = 5.0;
    private static final double TRIBUTO = 0.01;
    private List<Transacao> historicoTransacoes = new ArrayList<>();

    public ContaCorrente(String ID) {
        super(ID);
    }

    @Override
    public void depositar(double valor) {
        saldo += valor;
        historicoTransacoes.add(new Transacao("Depósito", valor));
    }

    @Override
    public void sacar(double valor) throws Exception {
        double valorComTaxa = valor + TAXA_SAQUE;
        if (valorComTaxa > saldo) {
            throw new SaldoInsuficienteException("Saldo insuficiente para saque com taxa.");
        }
        saldo -= valorComTaxa;
        historicoTransacoes.add(new Transacao("Saque", valor));
    }

    @Override
    public double calcularTributo() {
        return saldo * TRIBUTO;
    }

    public void exibirHistorico() {
        System.out.println("Histórico de transações:");
        for (Transacao transacao : historicoTransacoes) {
            System.out.println(transacao);
        }
    }
}
