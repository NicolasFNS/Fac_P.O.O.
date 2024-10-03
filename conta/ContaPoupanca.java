package conta;

import java.util.ArrayList;
import java.util.List;
import acoes.*;
import exception.SaldoInsuficienteException;

public class ContaPoupanca extends ContaBancaria {
    private static final double JUROS = 0.02;  // 2% de juros ao mês
    private List<Transacao> historicoTransacoes = new ArrayList<>();

    public ContaPoupanca(String ID) {
        super(ID);
    }

    @Override
    public void depositar(double valor) {
        saldo += valor;
        historicoTransacoes.add(new Transacao("Depósito", valor));
    }

    @Override
    public void sacar(double valor) throws Exception {
        if (valor > saldo) {
            throw new SaldoInsuficienteException("Saldo insuficiente para saque.");
        }
        saldo -= valor;
        historicoTransacoes.add(new Transacao("Saque", valor));
    }

    public void aplicarJuros() {
        saldo += saldo * JUROS;
        historicoTransacoes.add(new Transacao("Juros Aplicados", saldo * JUROS));
        System.out.println("Juros aplicados. Novo saldo: R$" + saldo);
    }

    public void exibirHistorico() {
        System.out.println("Histórico de transações:");
        for (Transacao transacao : historicoTransacoes) {
            System.out.println(transacao);
        }
    }
}