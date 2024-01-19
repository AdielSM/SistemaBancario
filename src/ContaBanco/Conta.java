package ContaBanco;

import ContaBanco.exceptions.SaldoInsuficiente;
import ContaBanco.exceptions.ValorInvalido;

class Conta {
    private final long numeroConta;
    private double saldoConta;
    private String nomeCliente;
    private String emailCliente;
    private String telefone;

    public Conta(long numeroConta, String nomeCliente, String emailCliente, String telefone, double saldoConta) {
        this.numeroConta = numeroConta;
        this.nomeCliente = nomeCliente;
        this.emailCliente = emailCliente;
        this.telefone = telefone;
        this.saldoConta = saldoConta;
    }

    public Conta(long numeroConta, String nomeCliente, String emailCliente, String telefone) {
        this(numeroConta, nomeCliente, emailCliente, telefone, 0);
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

    public String gettelefone(){
        return telefone;
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

    public void transferir(int contaDestino, double valor) throws ValorInvalido, SaldoInsuficiente {
        if(valor <= 0){
            throw new ValorInvalido("O valor transferido deve ser >= 0.");
        }

        sacar(valor);
        // implementar somente com classe banco.
    }
}

