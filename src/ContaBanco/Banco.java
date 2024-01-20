package ContaBanco;

import ContaBanco.exceptions.*;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Banco {

    private final HashMap<Long, Conta> contas = new HashMap<>();

    /**
     * Cria uma nova conta bancária com as informações fornecidas.
     * 
     * @param nome O nome do titular da conta.
     * @param email O email do titular da conta.
     * @param telefone O telefone do titular da conta.
     * @param senha A senha da conta.
     * @param saldoConta O saldo inicial da conta.
     * @throws ValorInvalido Se o saldo inicial for negativo.
     * @throws NomeInvalido Se o nome do titular não tiver apenas letras e espaços.
     * @throws EmailInvalido Se o email do titular for inválido.
     * @throws TelefoneInvalido Se o telefone do titular não tiver apenas números e 11 dígitos.
     * @throws SenhaInvalida Se a senha não tiver ao menos 8 dígitos com uma letra, um número e um caractere especial.
     */
    public void criarConta(String nome, String email, String telefone, String senha, double saldoConta) throws ValorInvalido, NomeInvalido, EmailInvalido, TelefoneInvalido, SenhaInvalida {

        long id = criarNumeroConta();

        if(validarInfo(nome, email, telefone, senha, saldoConta)){
            Conta conta = new Conta(id, nome, email, telefone, senha, saldoConta);
            contas.put(id, conta);
        }
    }


    /**
     * Cria uma nova conta bancária com os dados fornecidos.
     * 
     * @param nome O nome do titular da conta.
     * @param email O email do titular da conta.
     * @param telefone O telefone do titular da conta.
     * @param senha A senha da conta.
     * @throws NomeInvalido Se o nome do titular não tiver apenas letras e espaços.
     * @throws EmailInvalido Se o email do titular for inválido.
     * @throws TelefoneInvalido Se o telefone do titular não tiver apenas números e 11 dígitos.
     * @throws SenhaInvalida Se a senha não tiver ao menos 8 dígitos com uma letra, um número e um caractere especial.
     */
    public void criarConta(String nome, String email, String telefone, String senha) throws ValorInvalido, NomeInvalido, EmailInvalido, TelefoneInvalido, SenhaInvalida {
        criarConta(nome, email, telefone, senha, 0);
    }


    /**
     * Realiza o login de uma conta bancária.
     * 
     * @param email o email associado à conta
     * @param senha a senha da conta
     * @throws ContaInvalida se a conta não existir
     * @throws SenhaInvalida se a senha informada estiver incorreta
     */
    public void logarConta(String email, String senha) throws ContaInvalida, SenhaInvalida {
        long numeroConta = getNumeroConta(email);
        Conta conta = getConta(numeroConta);

        if (!senha.equals(conta.getSenha())){
            throw new SenhaInvalida("Senha incorreta.");
        }
    }


    /**
     * Realiza um saque na conta bancária especificada.
     * 
     * @param numeroConta o número da conta bancária
     * @param valor o valor a ser sacado
     * @throws ContaInvalida se a conta bancária não existir
     * @throws ValorInvalido se o valor for inválido (negativo ou zero)
     * @throws SaldoInsuficiente se o saldo da conta for insuficiente para o saque
     */
    public void sacar(long numeroConta, double valor) throws ContaInvalida, ValorInvalido, SaldoInsuficiente {
        Conta conta = getConta(numeroConta);
        conta.sacar(valor);
    }


    /**
     * Realiza um depósito na conta bancária especificada.
     *
     * @param numeroConta o número da conta bancária
     * @param valor o valor a ser depositado
     * @throws ContaInvalida se a conta bancária não existir
     * @throws ValorInvalido se o valor for inválido (negativo ou zero)
     */
    public void depositar(long numeroConta, double valor) throws ContaInvalida, ValorInvalido {
        Conta conta = getConta(numeroConta);
        conta.depositar(valor);
    }


    /**
     * Transfere um determinado valor de uma conta para outra.
     * 
     * @param numeroContaRemetente o número da conta do remetente
     * @param emailContaDestinatario o email da conta do destinatário
     * @param valor o valor a ser transferido
     * @throws ContaInvalida se a conta do remetente ou do destinatário for inválida
     * @throws ValorInvalido se o valor da transferência for inválido
     * @throws SaldoInsuficiente se o saldo da conta do remetente for insuficiente para a transferência
     */
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
            in.close();
        }
    }


    /**
     * Cria um número de conta único para uma nova conta bancária.
     * 
     * @return O número de conta gerado.
     */
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


    /**
     * Obtém as informações da conta bancária com o número de conta especificado.
     * 
     * @param numeroConta o número da conta bancária
     * @throws ContaInvalida se a conta bancária não existir
     */
    public void infoConta(long numeroConta) throws ContaInvalida {
        Conta conta = getConta(numeroConta);
        conta.infoConta();
    }


    /**
     * Valida as informações fornecidas para criar uma nova conta bancária.
     *
     * @param nome O nome do cliente.
     * @param email O email do cliente.
     * @param telefone O número de telefone do cliente.
     * @param senha A senha da conta bancária.
     * @param saldoConta O saldo inicial da conta bancária.
     * @return true se as informações forem válidas, caso contrário, lança uma exceção.
     * @throws NomeInvalido Se o nome não contiver apenas letras.
     * @throws EmailInvalido Se o email não for válido ou já estiver registrado em outra conta.
     * @throws TelefoneInvalido Se o número de telefone não contiver apenas números ou não possuir 11 dígitos.
     * @throws ValorInvalido Se o saldo inicial for negativo.
     * @throws SenhaInvalida Se a senha não atender aos requisitos mínimos de segurança.
     */
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


    /**
     * Valida as informações de uma transferência.
     * 
     * @param valor o valor da transferência
     * @return true se as informações forem válidas, false caso contrário
     * @throws ValorInvalido se o valor da transferência for menor ou igual a zero
     */
    private boolean validarInfoTransferencia(double valor) throws ValorInvalido {
        if (valor < 0) {
            throw new ValorInvalido("O valor transferido não pode ser <= 0.");
        }
        return true;
    }


    /**
     * Retorna a conta bancária correspondente ao número de conta fornecido.
     *
     * @param numeroConta o número da conta bancária
     * @return a conta bancária correspondente ao número de conta fornecido
     * @throws ContaInvalida se o número de conta fornecido não existir
     */
    private Conta getConta(long numeroConta) throws ContaInvalida {
        if (!contas.containsKey(numeroConta)){
            throw new ContaInvalida("Número de conta inexistente: " + numeroConta);
        }
        return contas.get(numeroConta);
    }


    /**
     * Retorna o nome do cliente associado ao número da conta especificado.
     *
     * @param numeroConta o número da conta para obter o nome do cliente
     * @return o nome do cliente associado à conta especificada
     * @throws ContaInvalida se a conta especificada não existir
     */
    public String getNomeCliente(long numeroConta) throws ContaInvalida {
            Conta conta = getConta(numeroConta);
            return conta.getNomeCliente();
    }


    /**
     * Retorna o nome do cliente associado ao email fornecido.
     *
     * @param email o email do cliente
     * @return o nome do cliente
     * @throws ContaInvalida se a conta não existir ou estiver inválida
     */
    public String getNomeCliente(String email) throws ContaInvalida{
        long numeroConta = getNumeroConta(email);
        Conta conta = getConta(numeroConta);
        return conta.getNomeCliente();
    }


    /**
     * Retorna o número da conta associado ao email fornecido.
     * 
     * @param email o email do cliente da conta
     * @return o número da conta associado ao email fornecido
     * @throws ContaInvalida se não houver nenhuma conta associada ao email fornecido
     */
    public long getNumeroConta(String email) throws ContaInvalida {
        for (Conta conta : contas.values()) {
            if (conta.getEmailCliente().equals(email)) {
                return conta.getNumeroConta();
            }
        }
        throw new ContaInvalida("Email de conta inexistente: " + email);
    }


    /**
     * Retorna o saldo da conta bancária com o número especificado.
     *
     * @param numeroConta o número da conta bancária
     * @return o saldo da conta bancária
     * @throws ContaInvalida se a conta bancária não existir
     */
    public double getSaldoConta(long numeroConta) throws ContaInvalida {
        Conta conta = getConta(numeroConta);
        return conta.getSaldoConta();
    }


    /**
     * Retorna o email do cliente associado à conta bancária.
     *
     * @param numeroConta o número da conta bancária
     * @return o email do cliente associado à conta bancária
     * @throws ContaInvalida se a conta bancária não existir
     */
    public String getEmailCliente(long numeroConta) throws ContaInvalida {
        Conta conta = getConta(numeroConta);
        return conta.getEmailCliente();
    }
}