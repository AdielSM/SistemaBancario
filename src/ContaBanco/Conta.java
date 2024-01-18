package ContaBanco;

public class Conta {
    private long numeroConta;
    private double saldoConta;
    private String nomeCliente;
    private String emailCliente;

    private int numeroTelefone;

    public long getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(long numeroConta) {
        this.numeroConta = numeroConta;
    }

    public double getSaldoConta() {
        return saldoConta;
    }

    public void setSaldoConta(double saldoConta) {
        this.saldoConta = saldoConta;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public int getNumeroTelefone() {
        return numeroTelefone;
    }

    public void setNumeroTelefone(int numeroTelefone) {
        this.numeroTelefone = numeroTelefone;
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
}

