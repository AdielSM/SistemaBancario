package ContaBanco;

import ContaBanco.exceptions.*;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Banco {
    private final HashMap<Long, Conta> contas = new HashMap<>();

    public void criarConta(String nome, String email, String telefone, String senha, double saldoConta) throws ValorInvalido, NomeInvalido, EmailInvalido, TelefoneInvalido, SenhaInvalida {

        long id = criarNumeroConta();

        if(validarInfo(nome, email, telefone, senha, saldoConta)){
            Conta conta = new Conta(id, nome, email, telefone, senha, saldoConta);
            contas.put(id, conta);
        }
    }

    public void criarConta(String nome, String email, String telefone, String senha) throws ValorInvalido, NomeInvalido, EmailInvalido, TelefoneInvalido, SenhaInvalida {
        criarConta(nome, email, telefone, senha, 0);
    }

    public void logarConta(String email, String senha) throws ContaInvalida, SenhaInvalida {
        long numeroConta = getNumeroConta(email);
        Conta conta = getConta(numeroConta);

        if (!senha.equals(conta.getSenha())){
            throw new SenhaInvalida("Senha incorreta.");
        }
    }

    public void sacar(long numeroConta, double valor) throws ContaInvalida, ValorInvalido, SaldoInsuficiente {
        Conta conta = getConta(numeroConta);
        conta.sacar(valor);
    }

    public void depositar(long numeroConta, double valor) throws ContaInvalida, ValorInvalido {
        Conta conta = getConta(numeroConta);
        conta.depositar(valor);
    }

    public void transferir(long numeroContaRemetente, String emailContaDestinatario, double valor) throws ContaInvalida, ValorInvalido, SaldoInsuficiente {
        Scanner in = new Scanner(System.in);

        long numeroContaDestino = getNumeroConta(emailContaDestinatario);
        Conta contaRemetente = getConta(numeroContaRemetente);
        Conta contaDestino = getConta(numeroContaDestino);

        if(validarInfoTransferencia(valor)){
            System.out.println("Tem certeza que deseja transferir " + valor + "R$ para " + contaDestino.getNomeCliente() + "?");
            char opcao;
            do {
                System.out.print("Digite s para sim e n para não: ");
                opcao = in.next().toLowerCase().charAt(0);
            } while (opcao != 's' && opcao != 'n');

            if (opcao == 's'){
                contaRemetente.sacar(valor);
                contaDestino.depositar(valor);
            }
        }

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
    public void infoConta(long numeroConta) throws ContaInvalida {
        Conta conta = getConta(numeroConta);
        conta.infoConta();
    }

    private boolean validarInfo(String nome, String email, String telefone, String senha, double saldoConta) throws NomeInvalido, EmailInvalido, TelefoneInvalido, ValorInvalido, SenhaInvalida {
        if (!nome.matches("^[a-zA-ZÀ-ÿ ]*$")){
            throw new NomeInvalido("O nome deve conter apenas letras.");
        }

        if(!email.contains("@")){
            throw new EmailInvalido("Digite um email válido.");
        }

        for(Conta conta: contas.values()){
            if(conta.getEmailCliente().equals(email)){
               throw new EmailInvalido("Já existe uma conta registrada com este email.");
            }
        }

        if(!telefone.matches("^[0-9]*$")){
            throw new TelefoneInvalido("O número de telefone deve conter apenas números.");
        }

        if(telefone.length() != 11){
            throw  new TelefoneInvalido("O número de telefone deve possuir 11 dígitos.");
        }

        if(saldoConta < 0){
            throw new ValorInvalido("O valor do saldo inicial não pode ser negativo.");
        }

        if(senha.length() < 8){
            throw new SenhaInvalida("A senha deve possuir ao menos 8 dígitos");
        }

        if(!senha.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")){
            throw new SenhaInvalida("A senha deve conter ao menos um número, uma letra e um caracter especial.");
        }

        return true;
    }

    private boolean validarInfoTransferencia(double valor) throws ValorInvalido {
        if (valor < 0) {
            throw new ValorInvalido("O valor transferido não pode ser <= 0.");
        }

        return true;
    }

    private Conta getConta(long numeroConta) throws ContaInvalida {
        if (!contas.containsKey(numeroConta)){
            throw new ContaInvalida("Número de conta inexistente: " + numeroConta);
        }

        return contas.get(numeroConta);
    }

    public String getNomeCliente(long numeroConta) throws ContaInvalida {
            Conta conta = getConta(numeroConta);
            return conta.getNomeCliente();
    }

    public String getNomeCliente(String email) throws ContaInvalida{
        long numeroConta = getNumeroConta(email);
        Conta conta = getConta(numeroConta);
        return conta.getNomeCliente();
    }

    public long getNumeroConta(String email) throws ContaInvalida {
        for (Conta conta : contas.values()) {
            if (conta.getEmailCliente().equals(email)) {
                return conta.getNumeroConta();
            }
        }

        throw new ContaInvalida("Email de conta inexistente: " + email);
    }
    public double getSaldoConta(long numeroConta) throws ContaInvalida {
        Conta conta = getConta(numeroConta);
        return conta.getSaldoConta();
    }

    public String getEmailCliente(long numeroConta) throws ContaInvalida {
        Conta conta = getConta(numeroConta);
        return conta.getEmailCliente();
    }


}
