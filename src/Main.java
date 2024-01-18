import ContaBanco.Conta;

import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        HashMap<Integer, Conta> contas = new HashMap<Integer, Conta>();
        boolean contaLogada = false;

        System.out.println("==================================");
        System.out.println("Ben-vindo ao Banco do Brasil!");
        System.out.println("==================================");

        while (true) {
            displayMenu(contaLogada);

            if (in.hasNextInt()) {

                int opcao = in.nextInt();

                if (validarOpcao(opcao, contaLogada)) {

                    if(contaLogada){
                        System.out.println("Em desenvolvimento...");
                    }

                    else {
                        switch (opcao) {
                            case 1 -> {
                                System.out.println("Digite o número da conta:");
                                int numeroConta = in.nextInt();

                                System.out.println("Digite o nome do cliente:");
                                String nomeCliente = in.next();

                                System.out.println("Digite o email do cliente:");
                                String emailCliente = in.next();

                                System.out.println("Digite o número de telefone do cliente:");
                                int numeroTelefone = in.nextInt();

                                Conta conta = criarConta(numeroConta, nomeCliente, emailCliente, numeroTelefone);
                                contas.put(numeroConta, conta);

                                System.out.println("Conta criada com sucesso!");
                            }
                            case 2 -> {
                                System.out.println("Obrigado por utilizar o Banco do Brasil!");
                                System.exit(0);
                            }
                        }

                        contaLogada = true;
                    }

                } else {

                    if(contaLogada){
                        System.out.println("Digite um número válido. (1-5)");
                    }

                    else{
                        System.out.println("Digite um número válido. (1-3)");
                    }

                }
            } else {
                System.out.println("Por favor, digite apenas números inteiros.");
            }
        }
    }

    public static boolean validarOpcao(int opcao, boolean contaLogada) {
        if(contaLogada){
            return switch (opcao) {
                case 1, 2, 3 -> true;
                default -> false;
            };
        }

        return switch (opcao) {
            case 1, 2, 3, 4, 5, 6 -> true;
            default -> false;
        };
    }

    public static void displayMenu(boolean contaLogada) {
        if (contaLogada) {
            System.out.println();
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Entrar em uma conta");
            System.out.println("2 - Criar conta");
            System.out.println("3 - Sair");
        } else {
            System.out.println();
            System.out.println("Escolha uma opção:");
            System.out.println("1- Sacar");
            System.out.println("2- Depositar");
            System.out.println("3- Transferir");
            System.out.println("4- Ver saldo");
            System.out.println("5- Sair");
        }
    }

//    public static void acaoUsuario(int opcao){
//        switch (opcao){
//            case 1:
//
//        }
//    }
//
//    public static void acaoUsuario(int opcao, boolean contaLogada){
//        if(contaLogada) {
//            acaoUsuario(opcao);
//        }
//
//    }

    public static Conta criarConta(int numeroConta, String nomeCliente, String emailCliente, int numeroTelefone){
        Conta conta = new Conta();
        conta.setNumeroConta(numeroConta);
        conta.setNomeCliente(nomeCliente);
        conta.setEmailCliente(emailCliente);
        conta.setNumeroTelefone(numeroTelefone);

        return conta;
    }
}

