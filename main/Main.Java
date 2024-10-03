package main;

import banco.Banco;
import cliente.Cliente;
import conta.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Banco banco = new Banco();
        Scanner scanner = new Scanner(System.in);

        try {
            banco.carregarDados("banco_dados.ser");
        } catch (Exception e) {
            System.out.println("Arquivo de dados não encontrado, iniciando novo banco.");
        }

        Cliente cliente = null;
        while (cliente == null) {

            System.out.println("Bem-vindo ao sistema bancário.");
            System.out.println("Você já tem uma conta? (0) não | (1) sim");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 0) {
                System.out.println("Digite seu nome:");
                String nome = scanner.nextLine();
                System.out.println("Digite seu CPF:");
                String cpf = scanner.nextLine();
                System.out.println("Digite uma senha:");
                String senha = scanner.nextLine();

                cliente = new Cliente(nome, cpf, senha);
                banco.cadastrarCliente(cliente);
                System.out.println("Cadastro realizado com sucesso.");

                ContaCorrente cc1 = new ContaCorrente("0001");
                ContaPoupanca cp1 = new ContaPoupanca("0002");

                cliente.abrirConta(cc1);
                cliente.abrirConta(cp1);

                cc1.depositar(1000);
                try {
                    cc1.sacar(200);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                cp1.depositar(500);
                cp1.aplicarJuros();

                cliente.listarContas();
                cc1.exibirHistorico();
                cp1.exibirHistorico();
                
            } else if (opcao == 1) {

                System.out.println("Digite seu CPF:");
                String cpf = scanner.nextLine();
                System.out.println("Digite sua senha:");
                String senha = scanner.nextLine();

                cliente = banco.login(cpf, senha);

                if (cliente == null) {
                    System.out.println("Falha no login. CPF ou senha incorretos.");
                }

                System.out.println("Digite o número equivalente ao que você quer fazer:");
                System.out.println("(1) nova conta poupança");
                System.out.println("(2) nova conta corrente");
                System.out.println("(3) depositar");
                System.out.println("(4) sacar");
                System.out.println("(5) listar contas");
                System.out.println("(6) exibir Histórico");
                System.out.println("(7) Excluir Conta");
                System.out.println("qualquer outro número para sair");
                
                int acao = scanner.nextInt();
                scanner.nextLine();

                if(acao == 1){
                    System.out.println("Digite o número da nova conta corrente:");
                    String numeroContaCorrente = scanner.nextLine();
                    ContaCorrente cc = new ContaCorrente(numeroContaCorrente);
                    cliente.abrirConta(cc);
                    System.out.println("Conta Corrente aberta com sucesso.");

                }else if(acao == 2){
                    System.out.println("Digite o número da nova conta poupança:");
                    String numeroContaPoupanca = scanner.nextLine();
                    ContaPoupanca cp = new ContaPoupanca(numeroContaPoupanca);
                    cliente.abrirConta(cp);
                    System.out.println("Conta Poupança aberta com sucesso.");
                
                }else if(acao == 3){
                    cliente.listarContas();
                    System.out.println("Digite o número da conta para depósito:");
                    String numeroContaDeposito = scanner.nextLine();
                    ContaBancaria contaDeposito = encontrarConta(cliente, numeroContaDeposito);
                    if (contaDeposito != null) {
                        System.out.println("Digite o valor a depositar:");
                        double valorDeposito = scanner.nextDouble();
                        scanner.nextLine();
                        contaDeposito.depositar(valorDeposito);
                    } else {
                        System.out.println("Conta não encontrada.");
                    }
                    
                }else if(acao == 4){
                    cliente.listarContas();
                    System.out.println("Digite o número da conta para saque:");
                    String numeroContaSaque = scanner.nextLine();
                    ContaBancaria contaSaque = encontrarConta(cliente, numeroContaSaque);
                    if (contaSaque != null) {
                        System.out.println("Digite o valor a sacar:");
                        double valorSaque = scanner.nextDouble();
                        scanner.nextLine();  
                        try {
                            contaSaque.sacar(valorSaque);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.out.println("Conta não encontrada.");
                    }
                    
                }else if(acao == 5){
                    cliente.listarContas();

                }else if(acao == 6){
                    System.out.println("Digite o número da conta para exibir o histórico:");
                    String numeroContaHistorico = scanner.nextLine();
                    ContaBancaria contaHistorico = encontrarConta(cliente, numeroContaHistorico);
                    if (contaHistorico instanceof ContaCorrente) {
                        ((ContaCorrente) contaHistorico).exibirHistorico();
                    } else if (contaHistorico instanceof ContaPoupanca) {
                        ((ContaPoupanca) contaHistorico).exibirHistorico();
                    } else {
                        System.out.println("Conta não encontrada ou não possui histórico.");
                    }

                } else if(acao == 7){
                    cliente.listarContas();
                    System.out.println("Digite o número da conta que deseja excluir:");
                    String numeroContaExcluir = scanner.nextLine();
                    cliente.excluirConta(numeroContaExcluir);
                    
                } else {
                        System.out.println("Saindo...");
                }
            }
        }

        try {
            banco.salvarDados("banco_dados.ser");
            System.out.println("Dados salvos com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro ao salvar dados.");
        }

        scanner.close();
    }

    private static ContaBancaria encontrarConta(Cliente cliente, String numeroConta) {
        for (ContaBancaria conta : cliente.getContas()) {
            if (conta.getNumeroConta().equals(numeroConta)) {
                return conta;
            }
        }
        return null;
    }
}

