package ContaBanco;

public class SaldoInsuficiente extends Exception {
    public SaldoInsuficiente(String mensagem){
        super(mensagem);
    }
}
