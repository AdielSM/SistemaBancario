package ContaBanco;

import ContaBanco.exceptions.SaldoInsuficiente;
import ContaBanco.exceptions.ValorInvalido;

class Conta {
    private final long numeroConta;
    private double saldoConta;
    private String nomeCliente;
    private String emailCliente;
    private String telefone;
    private String senha;



    /**
     * Cria uma nova instância da classe Conta com os parâmetros fornecidos.
     * 
     * @param numeroConta o número da conta
     * @param nomeCliente o nome do cliente
     * @param emailCliente o email do cliente
     * @param telefone o telefone do cliente
     * @param senha a senha da conta
     * @param saldoConta o saldo da conta
     */
    public Conta(long numeroConta, String nomeCliente, String emailCliente, String telefone,
                 String senha, double saldoConta) {
        this.numeroConta = numeroConta;
        this.nomeCliente = nomeCliente;
        this.emailCliente = emailCliente;
        this.telefone = telefone;
        this.saldoConta = saldoConta;
        this.senha = senha;
    }


    /**
     * Cria uma nova instância da classe Conta com os parâmetros fornecidos.
     * 
     * @param numeroConta o número da conta
     * @param nomeCliente o nome do cliente
     * @param emailCliente o email do cliente
     * @param telefone o telefone do cliente
     * @param senha a senha da conta
     */
    public Conta(long numeroConta, String nomeCliente, String emailCliente, String telefone, String senha) {
        this(numeroConta, nomeCliente, emailCliente, telefone, senha, 0);
    }


    public long getNumeroConta() {
        return numeroConta;
    }


    public double getSaldoConta() {
        return saldoConta;
    }


    public String getNomeCliente() {
        return nomeCliente;
    }


    public String getEmailCliente() {
        return emailCliente;
    }


    public String getTelefone(){
        return telefone;
    }


    public String getSenha() {
        return senha;
    }


    /**
     * Deposita um valor na conta bancária.
     * 
     * @param valor o valor a ser depositado
     * @throws ValorInvalido se o valor for menor ou igual a zero
     */
    public void depositar(double valor) throws ValorInvalido {

        if(valor <= 0){
            throw new ValorInvalido("O valor depositado deve ser >= 0.");
        }

        saldoConta += valor;
    }


    /**
     * Realiza um saque na conta bancária.
     * 
     * @param valor o valor a ser sacado
     * @throws SaldoInsuficiente se o saldo da conta for insuficiente para o saque
     * @throws ValorInvalido se o valor do saque for menor ou igual a zero
     */
    public void sacar(double valor) throws SaldoInsuficiente, ValorInvalido {
        if (valor > saldoConta) {
            throw new SaldoInsuficiente("Seu saldo é insuficiente.");
        }

        if(valor <= 0){
            throw new ValorInvalido("O valor sacado deve ser >= 0.");
        }

        saldoConta -= (double) Math.round(valor * 100.0) / 100;
    }


    /**
     * Imprime as informações da conta bancária, incluindo o nome do cliente, email do cliente,
     * telefone do cliente, número da conta e saldo da conta.
     */
    public void infoConta(){
        System.out.println("Informações da conta bancária:\n");
        System.out.println("Nome do cliente: " + nomeCliente +
                           "\nEmail do cliente: " + emailCliente +
                           "\nTelefone do cliente: " + telefone +
                           "\nNúmero da conta: " + numeroConta +
                           "\nSaldo da conta: " + saldoConta + "R$");
    }

}

