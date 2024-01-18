package ContaBanco;

public class Conta {
    private int numeroConta;
    private double saldoConta;
    private String nomeCliente;
    private String emailCliente;

    private int numeroTelefone;

    public int getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(int numeroConta) {
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

    public String depositar(double valor){
        saldoConta += valor;

        return "Depósito realizado com sucesso.";
    }

    public String sacar(double valor) throws SaldoInsuficiente{
        if (saldoConta == 0 || valor - saldoConta <= 0){
            throw new SaldoInsuficiente("Seu saldo é insuficiente.");
        }

        saldoConta -= valor;
        return "Saque relizado com sucesso.";

    }
}

