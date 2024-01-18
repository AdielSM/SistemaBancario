import ContaBanco.Conta;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        HashMap<Integer, Conta> contas = new HashMap<>();
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
                                while(true){
                                    System.out.print("Digite o número da sua conta: ");

                                    if(in.hasNextInt()) {
                                        int numeroConta = in.nextInt();

                                        if (contas.containsKey(numeroConta)) {
                                            System.out.println("Sua conta foi encontrada e logada com sucesso! ");
                                            contaLogada = true;
                                            break;
                                        }

                                        System.out.println("Conta não encontrada, por favor, tente novamente.");
                                    }
                                }
                            }

                            case 2 -> {
                                try {
                                    System.out.print("Digite o número da conta:");
                                    int numeroConta = in.nextInt();

                                    System.out.print("Digite o nome do cliente:");
                                    String nomeCliente = in.next();

                                    System.out.print("Digite o email do cliente:");
                                    String emailCliente = in.next();

                                    System.out.print("Digite o número de telefone do cliente:");
                                    int numeroTelefone = in.nextInt();

                                    Conta conta = criarConta(numeroConta, nomeCliente, emailCliente, numeroTelefone);
                                    contas.put(numeroConta, conta);

                                    contaLogada = true;
                                    System.out.println("Conta criada com sucesso!");
                                }

                                catch (InputMismatchException e){
                                    System.out.println("Por favor, digite apenas números inteiros.");
                                }

                                catch (Exception e){ //deixar mais específico, criar exceções para cada tipo de erro.
                                    System.out.println("Ocorreu um erro ao criar a conta.");
                                }

                            }
                            case 3 -> {
                                System.out.println("Obrigado por utilizar o Banco do Brasil!");
                                System.exit(0);
                            }
                        }


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
                case 1, 2, 3, 4, 5, 6 -> true;
                default -> false;
            };
        }

        return switch (opcao) {
            case 1, 2, 3-> true;
            default -> false;
        };
    }

    public static void displayMenu(boolean contaLogada) {
        if (contaLogada) {
            System.out.println();
            System.out.println("Escolha uma opção:");
            System.out.println("1- Sacar");
            System.out.println("2- Depositar");
            System.out.println("3- Transferir");
            System.out.println("4- Ver saldo");
            System.out.println("5- Sair");
        } else {
            System.out.println();
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Entrar em uma conta");
            System.out.println("2 - Criar conta");
            System.out.println("3 - Sair");
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

        // fazer verificação se já existe uma conta que possua o mesmo número.

        Conta conta = new Conta();
        conta.setNumeroConta(numeroConta);
        conta.setNomeCliente(nomeCliente);
        conta.setEmailCliente(emailCliente);
        conta.setNumeroTelefone(numeroTelefone);

        return conta;
    }
}

