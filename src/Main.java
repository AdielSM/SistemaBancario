import ContaBanco.Conta;
import ContaBanco.SaldoInsuficiente;
import ContaBanco.ValorInvalido;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        in.useLocale(java.util.Locale.US);

        HashMap<Long, Conta> contas = new HashMap<>();
        boolean contaLogada = false;
        Conta contaAtual = null;

        //teste
        Conta contaTeste = new Conta(123456789, "adiel melo", "Teste", 3287432, 1000);
        contas.put(contaTeste.getNumeroConta(), contaTeste);


        System.out.println("==================================");
        System.out.println("Ben-vindo ao Banco do Brasil!");
        System.out.println("==================================");

        while (true) {
            displayMenu(contaLogada);

            if (in.hasNextInt()) {

                int opcao = in.nextInt();

                if (validarOpcao(opcao, contaLogada)) {

                    if (contaLogada) {
                        //colocar opção de histórico em dados da conta
//                        1- Sacar
//                        2- Depositar
//                        3- Transferir
//                        4- Ver dados da conta
//                        5- Sair da conta atual
//                        6- Sair do programa
                        switch (opcao) {
                            case 1:
                                while (true) {
                                    System.out.println("Digite o valor que você deseja sacar em R$. " +
                                            "\n(Saldo disponível: " + contaAtual.getSaldoConta() + "R$ )");

                                    if (in.hasNextDouble()) {
                                        double valor = in.nextDouble();

                                        try {
                                            contaAtual.sacar(valor);
                                            System.out.println("Saque no valor de " + valor + "R$ realizado com sucesso." +
                                                    "\nNovo saldo: " + contaAtual.getSaldoConta() + "R$");
                                            break;
                                        } catch (SaldoInsuficiente | ValorInvalido e) {
                                            System.out.println(e.getMessage());
                                        }
                                    } else System.out.println("Digite apenas valores numéricos. \n");
                                    in.next();

                                }
                                break;

                            case 2:
                                while (true) {
                                    System.out.println("Digite o valor que você deseja depositar em R$.");

                                    if (in.hasNextDouble()) {
                                        double valor = in.nextDouble();

                                        try {
                                            contaAtual.depositar(valor);
                                            System.out.println("Depósito no valor de " + valor + "R$ realizado com sucesso." +
                                                    "\nNovo saldo: " + contaAtual.getSaldoConta() + "R$");
                                            break;
                                        } catch (ValorInvalido e) {
                                            System.out.println(e.getMessage());
                                        }
                                    }

                                    System.out.println("Digite apenas valores numéricos.");
                                    in.next();
                                }
                                break;

                            case 3:

                            case 6:
                                System.out.println("Obrigado por utilizar o Banco do Brasil!");
                                System.exit(0);

                            default:
                                System.out.println("Em desenvolvimento...");

                        }

                    } else {

                        // 1 - Entrar em uma conta
//                        2 - Criar conta
//                        3 - Sair do programa

                        switch (opcao) {

                            case 1 -> {
                                while (true) {
                                    System.out.print("Digite o número da sua conta: ");

                                    if (in.hasNextInt()) {
                                        long numeroConta = in.nextInt();

                                        if (contas.containsKey(numeroConta)) {
                                            System.out.println("Sua conta foi encontrada e logada com sucesso! ");
                                            contaLogada = true;
                                            contaAtual = contas.get(numeroConta);
                                            break;
                                        }

                                        System.out.println("Conta não encontrada, por favor, tente novamente.");
                                    }
                                }
                            }

                            case 2 -> {
                                try {
                                    // colocar verificação nos inputs
                                    System.out.print("Digite o número da conta: ");
                                    long numeroConta = in.nextLong();
                                    in.nextLine();

                                    System.out.print("Digite o nome do cliente: ");
                                    String nomeCliente = in.nextLine();

                                    System.out.print("Digite o email do cliente: ");
                                    String emailCliente = in.next();

                                    System.out.print("Digite o número de telefone do cliente: ");
                                    int numeroTelefone = in.nextInt();

                                    // verificar inputs
                                    System.out.print("Você deseja adicionar um saldo inicial na sua conta? (s/n)");
                                    char escolha = in.next().toLowerCase().charAt(0);
                                    Conta conta;

                                    if(escolha == 's'){
                                        System.out.print("Digite o valor do saldo inicial: ");
                                        double saldoInicial = in.nextDouble();
                                        conta = criarConta(numeroConta, nomeCliente, emailCliente, numeroTelefone, saldoInicial);
                                    } else {
                                         conta = criarConta(numeroConta, nomeCliente, emailCliente, numeroTelefone);
                                    }

                                    contas.put(numeroConta, conta);
                                    contaLogada = true;
                                    contaAtual = conta;
                                    System.out.println("Conta criada com sucesso!");

                                } catch (InputMismatchException e) {
                                    System.out.println("Por favor, digite apenas números inteiros.");
                                } catch (Exception e) { //deixar mais específico, criar exceções para cada tipo de erro.
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

                    if (contaLogada) {
                        System.out.println("Digite um número válido. (1-6)");
                    } else {
                        System.out.println("Digite um número válido. (1-3)");
                    }

                }
            } else {
                System.out.println("Por favor, digite apenas números inteiros.");
                in.next();
            }
        }
    }

    public static boolean validarOpcao(int opcao, boolean contaLogada) {
        if (contaLogada) {
            return switch (opcao) {
                case 1, 2, 3, 4, 5, 6 -> true;
                default -> false;
            };
        }

        return switch (opcao) {
            case 1, 2, 3 -> true;
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
            System.out.println("4- Ver dados da conta");
            System.out.println("5- Sair da conta atual");
            System.out.println("6- Sair do programa");
        } else {
            System.out.println();
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Entrar em uma conta");
            System.out.println("2 - Criar conta");
            System.out.println("3 - Sair do programa");
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

    public static Conta criarConta(long numeroConta, String nomeCliente, String emailCliente, int numeroTelefone) {
        // fazer chamada da classe Banco para gerar o número da conta
        return new Conta(numeroConta, nomeCliente, emailCliente, numeroTelefone);
    }

    public static Conta criarConta(long numeroConta, String nomeCliente, String emailCliente, int numeroTelefone, double saldoConta) {
        // fazer chamada da classe Banco para gerar o número da conta
        return new Conta(numeroConta, nomeCliente, emailCliente, numeroTelefone, saldoConta);
    }
}

