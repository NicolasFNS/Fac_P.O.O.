package banco;

import java.util.ArrayList;
import java.util.List;
import cliente.*;
import conta.ContaBancaria;

import java.io.*;

public class Banco implements Serializable {
    private List<Cliente> clientes;

    public Banco() {
        this.clientes = new ArrayList<>();
    }

    public void cadastrarCliente(Cliente cliente) {
        clientes.add(cliente);
        System.out.println("Cliente " + cliente.getNome() + " cadastrado com sucesso.");
    }

    public void removerCliente(String cpf) {
        clientes.removeIf(cliente -> cliente.getCpf().equals(cpf));
        System.out.println("Cliente com CPF " + cpf + " removido.");
    }

    public Cliente login(String cpf, String senha) {
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf) && cliente.autenticar(senha)) {
                System.out.println("Login de "+ cliente.getNome() +" realizado com sucesso.");
                return cliente;
            }
        }
        System.out.println("CPF ou senha incorretos.");
        return null;
    }

    public void consultarSaldoTotal() {
        double saldoTotal = 0;
        for (Cliente cliente : clientes) {
            for (ContaBancaria conta : cliente.getContas()) {
                saldoTotal += conta.getSaldo();
            }
        }
        System.out.println("Saldo total no banco: R$" + saldoTotal);
    }
    

    public void salvarDados(String arquivo) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            out.writeObject(clientes);
        }
    }

    @SuppressWarnings("unchecked")
    public void carregarDados(String arquivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))) {
            Object obj = in.readObject();
            if (obj instanceof List<?>) {
                this.clientes = (List<Cliente>) obj;
            } else {
                throw new ClassNotFoundException("O objeto deserializado não é uma lista de clientes.");
            }
        }
    }

}
