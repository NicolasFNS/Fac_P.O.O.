package banco;

import cliente.Cliente;
import conta.ContaBancaria;
import utils.ValidarCPF;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Banco implements Serializable {
    private List<Cliente> clientes;
    private Map<String, Integer> tentativasLogin;
    private static final int MAX_TENTATIVAS = 5;

    public Banco() {
        this.clientes = new ArrayList<>();
        this.tentativasLogin = new HashMap<>();
    }

    public void cadastrarCliente(Cliente cliente) {
        if(ValidarCPF.validarCPF(cliente.getCpf())){
            clientes.add(cliente);
            System.out.println("Cliente " + cliente.getNome() + " cadastrado com sucesso.");
        }else{
            System.out.println("Tentativa com CPF inválido: " + cliente.getNome());
        }
    }

    public void removerCliente(String cpf) {
        clientes.removeIf(cliente -> cliente.getCpf().equals(cpf));
        System.out.println("Cliente com CPF " + cpf + " removido.");
    }

    public Cliente login(String cpf, String senha) {
        if (tentativasLogin.containsKey(cpf) && tentativasLogin.get(cpf) >= MAX_TENTATIVAS) {
            System.out.println("Conta temporariamente bloqueada. Tente novamente mais tarde.");
            return null;
        }

        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf) && cliente.autenticar(senha)) {
                tentativasLogin.remove(cpf);
                System.out.println("Login de " + cliente.getNome() + " realizado com sucesso.");
                return cliente;
            }
        }

        tentativasLogin.put(cpf, tentativasLogin.getOrDefault(cpf, 0) + 1);
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
