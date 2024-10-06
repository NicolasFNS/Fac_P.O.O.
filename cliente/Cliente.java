package cliente;

import conta.ContaBancaria;

import utils.HashUtils;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cliente implements Serializable {
    private String nome;
    private String cpf;
    private String senha;
    private List<ContaBancaria> contas;

    public Cliente(String nome, String cpf, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        try {
            this.senha = HashUtils.hashSenha(senha);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao gerar hash da senha.");
        }
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
        try {
            return this.senha.equals(HashUtils.hashSenha(senha));
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
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