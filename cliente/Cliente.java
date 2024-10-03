package cliente;

import java.util.Iterator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import conta.ContaBancaria;

public class Cliente implements Serializable {
    private String nome;
    private String cpf;
    private String senha;
    private List<ContaBancaria> contas;

    public Cliente(String nome, String cpf, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
        this.contas = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public List<ContaBancaria> getContas() {
        return contas;
    }

    public boolean autenticar(String senha) {
        return this.senha.equals(senha);
    }

    public void abrirConta(ContaBancaria conta) {
        contas.add(conta);
        System.out.println("Conta aberta para o cliente " + nome);
    }

    public void listarContas() {
        System.out.println("Contas do cliente " + nome + ":");
        for (ContaBancaria conta : contas) {
            System.out.println("- Conta: " + conta.getNumeroConta() + " | Saldo: R$" + conta.getSaldo());
        }
    }


    public void excluirConta(String numeroConta) {
        Iterator<ContaBancaria> iterator = contas.iterator();

        while (iterator.hasNext()) {
            ContaBancaria conta = iterator.next();
            if (conta.getNumeroConta().equals(numeroConta)) {
                if (conta.getSaldo() == 0) {
                    iterator.remove();
                    System.out.println("Conta " + numeroConta + " removida com sucesso.");
                } else {
                    System.out.println("Conta não pode ser removida. Saldo deve ser zero.");
                }
                return;
            }
        }
        System.out.println("Conta não encontrada.");
    }
    
}