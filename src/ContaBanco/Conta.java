package ContaBanco;

public class Conta {
    private long numeroConta;
    private double saldoConta;
    private String nomeCliente;
    private String emailCliente;
    private int numeroTelefone;

    public Conta(long numeroConta, String nomeCliente, String emailCliente, int numeroTelefone, double saldoConta) {
        this.numeroConta = numeroConta;
        this.nomeCliente = nomeCliente;
        this.emailCliente = emailCliente;
        this.numeroTelefone = numeroTelefone;
        this.saldoConta = saldoConta;
    }

    public Conta(long numeroConta, String nomeCliente, String emailCliente, int numeroTelefone) {
        this(numeroConta, nomeCliente, emailCliente, numeroTelefone, 0);
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

    public void depositar(double valor) throws ValorInvalido {

        if(valor <= 0){
            throw new ValorInvalido("O valor depositado deve ser >= 0.");
        }

        saldoConta += valor;

    }

    public void sacar(double valor) throws SaldoInsuficiente, ValorInvalido {
        if (valor > saldoConta) {
            throw new SaldoInsuficiente("Seu saldo é insuficiente.");
        }

        if(valor <= 0){
            throw new ValorInvalido("O valor sacado deve ser >= 0.");
        }

        saldoConta -= valor;
    }

    public void transferir(int contaDestino, double valor){

    }
}

