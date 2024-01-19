package ContaBanco;

import ContaBanco.exceptions.EmailInvalido;
import ContaBanco.exceptions.NomeInvalido;
import ContaBanco.exceptions.TelefoneInvalido;
import ContaBanco.exceptions.ValorInvalido;

import java.util.HashMap;
import java.util.Random;

public class Banco {
    private HashMap<Long, Conta> contas = new HashMap<>();

    public void criarConta(String nome, String email, String telefone, double saldoConta) throws ValorInvalido, NomeInvalido, EmailInvalido, TelefoneInvalido {

        long id = criarNumeroConta();

        if(validarInfos(nome, email, telefone, saldoConta)){
            Conta conta = new Conta(id, nome, email, telefone, saldoConta);
            contas.put(id, conta);
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

    public boolean validarInfos(String nome, String email, String telefone, double saldoConta) throws NomeInvalido, EmailInvalido, TelefoneInvalido, ValorInvalido {
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

    public void criarConta(String nome, String email, String telefone) throws ValorInvalido, NomeInvalido, EmailInvalido, TelefoneInvalido {
        criarConta(nome, email, telefone, 0);
    }
}
