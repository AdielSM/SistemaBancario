import ContaBanco.Conta;

import java.util.HashMap;
import java.util.Scanner;

public class Main {

    private boolean contaLogada = false;

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

                if (validarOpcao(opcao)) {

                } else {
                    System.out.println("Digite uma opção válida. (1-6)");
                }
            } else {
                System.out.println("Por favor, digite apenas números inteiros.");
            }
        }
    }


    public static boolean validarOpcao(int opcao) {
        return switch (opcao) {
            case 1, 2, 3, 4, 5, 6 -> true;
            default -> false;
        };
    }

    public static void displayMenu(boolean contaLogada) {
        if (contaLogada) {
            System.out.println();
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Criar conta");
            System.out.println("2 - Sair");
        }
    }
}

