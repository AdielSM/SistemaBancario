package ContaBanco;

import ContaBanco.exceptions.*;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Banco {
    private HashMap<Long, Conta> contas = new HashMap<>();

    public void criarConta(String nome, String email, String telefone, double saldoConta) throws ValorInvalido, NomeInvalido, EmailInvalido, TelefoneInvalido {

        long id = criarNumeroConta();

        if(validarInfo(nome, email, telefone, saldoConta)){
            Conta conta = new Conta(id, nome, email, telefone, saldoConta);
            contas.put(id, conta);
        }
    }

    public void criarConta(String nome, String email, String telefone) throws ValorInvalido, NomeInvalido, EmailInvalido, TelefoneInvalido {
        criarConta(nome, email, telefone, 0);
    }

    public void sacar(long numeroConta, double valor) throws ContaInvalida, ValorInvalido, SaldoInsuficiente {
        Conta conta = getConta(numeroConta);
        conta.sacar(valor);
    }

    public void depositar(long numeroConta, double valor) throws ContaInvalida, ValorInvalido {
        Conta conta = getConta(numeroConta);
        conta.depositar(valor);
    }

    public void transferir(long numeroContaRemetente, long numeroContaDestino, double valor) throws ContaInvalida, ValorInvalido, SaldoInsuficiente {
        Scanner in = new Scanner(System.in);

        Conta contaRemetente = getConta(numeroContaRemetente);
        Conta contaDestino = getConta(numeroContaDestino);

        if(validarInfo(valor)){
            System.out.println("Tem certeza que deseja transferir " + valor + "R$ para " + contaDestino.getNomeCliente() + "?");
            char opcao;
            do {
                System.out.print("Digite s para sim e n para não: ");
                opcao = in.next().toLowerCase().charAt(0);
            } while (opcao != 's' && opcao != 'n');

            if (opcao == 's'){
                contaRemetente.sacar(valor);
                contaDestino.depositar(valor);
            } else {
                System.out.println("Transferência cancelada.");
            }
        }

    }

    public void infoConta(long numeroConta) throws ContaInvalida {
        Conta conta = getConta(numeroConta);
        conta.infoConta();
    }

    private Conta getConta(long numeroConta) throws ContaInvalida {
        if (!contas.containsKey(numeroConta)){
            throw new ContaInvalida("Número de conta inexistente: " + numeroConta);
        }

        return contas.get(numeroConta);
    }

    private long criarNumeroConta(){
        Random rd = new Random();

        long min = 11111111111L;
        long max = 99999999999L;
        long id = rd.nextLong((max - min) + 1) + min;;

        while (contas.containsKey(id)) {
            id = rd.nextLong((max - min) + 1) + min;
        }

        return id;
    }

    private boolean validarInfo(String nome, String email, String telefone, double saldoConta) throws NomeInvalido, EmailInvalido, TelefoneInvalido, ValorInvalido {
        if (!nome.matches("^[a-zA-Z]*$")){
            throw new NomeInvalido("O nome deve conter apenas letras.");
        }

        if(!email.contains("@")){
            throw new EmailInvalido("Digite um email válido.");
        }

        if(!telefone.matches("^[0-9]*$")){
            throw new TelefoneInvalido("O número de telefone deve conter apenas números.");
        }

        if(telefone.length() != 11){
            throw  new TelefoneInvalido("O número de telefone deve possuir 11 dígitos");
        }

        if(saldoConta < 0){
            throw new ValorInvalido("O valor do saldo inicial não pode ser negativo.");
        }

        return true;
    }

    private boolean validarInfo(double valor) throws ValorInvalido {
        if (valor <= 0) {
            throw new ValorInvalido("O valor transferido não pode ser <= 0.");
        }

        return true;
    }

}
