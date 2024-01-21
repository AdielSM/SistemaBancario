import ContaBanco.Banco;
import ContaBanco.CarregarContas;
import ContaBanco.exceptions.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        in.useLocale(java.util.Locale.US);

        Banco banco = new Banco();
        boolean contaLogada = false;
        long contaAtual = 0;

        // Carregar contas do banco
        try{
            CarregarContas.carregarContas(banco);
        } catch (ValorInvalido | NomeInvalido | EmailInvalido | TelefoneInvalido | SenhaInvalida e){
            System.out.println(e.getMessage());
        }

        System.out.println("==================================");
        System.out.println("Ben-vindo ao Banco do Brasil!");
        System.out.println("==================================");

        while (true) {
            displayMenu(contaLogada);

            if (in.hasNextInt()) {

                int opcao = in.nextInt();
                in.nextLine();

                if (validarOpcao(opcao, contaLogada)) {
                    if (contaLogada) {
                        switch (opcao) {
                            case 1 ->{
                                while (true) {
                                    try{
                                        System.out.println("Digite o valor que você deseja sacar em R$. " +
                                                "\n(Saldo disponível: " + banco.getSaldoConta(contaAtual) + "R$ )");
                                        System.out.println("Caso queira cancelar o saque, digite 0.");

                                        while (!in.hasNextDouble()) {
                                            System.out.println("Digite apenas valores numéricos.");
                                            in.next();
                                        }

                                        double valor = in.nextDouble();
                                        in.nextLine();

                                        if (valor == 0){
                                            System.out.println("Saque cancelado.");
                                            break;
                                        }

                                        banco.sacar(contaAtual, valor);
                                        double novoSaldo = banco.getSaldoConta(contaAtual);

                                        System.out.println("Saque no valor de " + valor + "R$ realizado com sucesso." +
                                                "\nNovo saldo: " + novoSaldo + "R$");
                                        break;

                                    } catch (ValorInvalido | SaldoInsuficiente| ContaInvalida e) {
                                        System.out.println(e.getMessage() + "\n");
                                    }
                                }
                            }

                            case 2 -> {
                                while (true) {

                                    System.out.println("Digite o valor que você deseja depositar em R$." +
                                            "\n(Digite 0 para cancelar o depósito.)");

                                    while (!in.hasNextDouble()) {
                                        System.out.println("Digite apenas valores numéricos.");
                                        in.next();
                                    }

                                    double valor = in.nextDouble();
                                    in.nextLine();

                                    if (valor == 0){
                                        System.out.println("Depósito cancelado.");
                                        break;
                                    }

                                    try {
                                        banco.depositar(contaAtual, valor);
                                        System.out.println("Depósito no valor de " + valor + "R$ realizado com sucesso." +
                                                "\nNovo saldo: " + banco.getSaldoConta(contaAtual) + "R$");
                                        break;
                                    } catch ( ContaInvalida | ValorInvalido e) {
                                        System.out.println(e.getMessage());
                                    }

                                }
                            }

                            case 3 -> {
                                while(true){
                                    try {
                                        System.out.println("\nDigite o email da conta para qual você deseja transferir em R$. " +
                                                "\n(Saldo disponível: " + banco.getSaldoConta(contaAtual) + "R$ )");

                                        banco.listarContas();

                                        String email = in.nextLine();

                                        if (email.equals(banco.getEmailCliente(contaAtual))){
                                            System.out.println("Você não pode transferir para a sua própria conta.");
                                            break;
                                        }

                                        System.out.println("Digite o valor que você deseja transferir para " +
                                                banco.getNomeCliente(email));
                                        System.out.println("Caso queira cancelar a transferência, digite 0.");

                                        while (!in.hasNextDouble()) {
                                            System.out.println("Digite apenas números.");
                                            in.next();
                                        }

                                        double valor = in.nextDouble();
                                        in.nextLine();

                                        if (valor == 0){
                                            System.out.println("Transferência cancelada.");
                                            break;
                                        }

                                        double saldo = banco.getSaldoConta(contaAtual);

                                        banco.transferir(in, contaAtual, email, valor);
                                        double novoSaldo = banco.getSaldoConta(contaAtual);

                                        if (saldo == novoSaldo){
                                            System.out.println("Transferência cancelada.");
                                            break;
                                        }

                                        System.out.println("O valor de " + valor + "R$ foi transferido com " +
                                                "sucesso para " + banco.getNomeCliente(email) +
                                                "\nSeu novo saldo: " + banco.getSaldoConta(contaAtual) + "R$");
                                        break;
                                    }

                                catch (ValorInvalido | SaldoInsuficiente | ContaInvalida e){
                                    System.out.println(e.getMessage());
                                }

                                }
                            }

                            case 4 -> {
                                try {
                                    banco.infoConta(contaAtual);
                                } catch(ContaInvalida e){
                                    System.out.println(e.getMessage());
                                }
                            }

                            case 5 -> {
                                contaLogada = false;
                                contaAtual = 0;
                                System.out.println("Conta deslogada com sucesso.");
                            }

                            default ->{
                                System.out.println("Obrigado por utilizar o Banco do Brasil!");
                                in.close();
                                System.exit(0);
                            }

                        }

                    } else {
                        switch (opcao) {
                            case 1 -> {
                                while (true) {
                                    System.out.print("Digite o email da sua conta: ");
                                    String email = in.nextLine();
                                    System.out.print("Digite a senha da sua conta: ");
                                    String senha = in.nextLine();

                                    try {
                                        banco.logarConta(email, senha);
                                        contaLogada = true;
                                        contaAtual = banco.getNumeroConta(email);
                                        System.out.println("Conta logada com sucesso! " +
                                                "Seja bem-vindo " + banco.getNomeCliente(contaAtual) + "!");
                                        break;
                                    }

                                    catch (ContaInvalida | SenhaInvalida e){
                                        System.out.println(e.getMessage());
                                    }
                                }
                            }

                            case 2 -> {
                                try {
                                    System.out.print("Digite o seu nome: ");
                                    String nomeCliente = in.nextLine();

                                    System.out.print("Digite o seu email: ");
                                    String emailCliente = in.nextLine();

                                    System.out.print("Digite o seu número de telefone(com DDD): ");
                                    String numeroTelefone = in.nextLine();

                                    System.out.print("Digite a sua senha: ");
                                    String senha = in.nextLine();

                                    char escolha;
                                    do {
                                        System.out.print("Você deseja adicionar um saldo inicial na sua conta? (s/n) ");
                                        escolha = in.nextLine().toLowerCase().charAt(0);
                                    } while (escolha != 's' && escolha != 'n');

                                    if(escolha == 's'){
                                        while (true) {
                                            System.out.print("Digite o saldo inicial da conta: ");

                                            if (in.hasNextDouble()) {
                                                double saldoInicial = in.nextDouble();
                                                banco.criarConta(nomeCliente, emailCliente, numeroTelefone, senha, saldoInicial);
                                                break;
                                            }

                                            else {
                                                System.out.println("Digite apenas valores numéricos.");
                                                in.next();
                                            }
                                        }
                                    } else {
                                         banco.criarConta(nomeCliente, emailCliente, numeroTelefone, senha);
                                    }

                                    contaLogada = true;
                                    contaAtual = banco.getNumeroConta(emailCliente);
                                    System.out.println("Conta criada com sucesso!");

                                } catch (ValorInvalido | ContaInvalida | NomeInvalido | EmailInvalido | TelefoneInvalido | SenhaInvalida e) {
                                    System.out.println(e.getMessage());
                                }

                            }
                            case 3 -> {
                                System.out.println("Obrigado por utilizar o Banco do Brasil!");
                                in.close();
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

    /**
     * Valida a opção selecionada pelo usuário.
     * 
     * @param opcao a opção selecionada pelo usuário
     * @param contaLogada indica se o usuário está logado em uma conta
     * @return true se a opção for válida, false caso contrário
     */
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

    /**
     * Exibe o menu de opções para o usuário de acordo com o estado da conta.
     * 
     * @param contaLogada indica se o usuário está logado em uma conta
     * 
     */
    public static void displayMenu(boolean contaLogada) {
        if (contaLogada) {
        System.out.println("""
                
                Escolha uma opção:
                1- Sacar
                2- Depositar
                3- Transferir
                4- Ver dados da conta
                5- Sair da conta atual
                6- Sair do programa""");
    } else {
        System.out.println("""

                Escolha uma opção:
                1 - Entrar em uma conta
                2 - Criar conta
                3 - Sair do programa""");
    }
    }
}

