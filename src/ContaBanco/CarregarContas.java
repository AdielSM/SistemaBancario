package ContaBanco;

import ContaBanco.exceptions.*;

public class CarregarContas {
    public static void carregarContas(Banco banco) throws ValorInvalido, NomeInvalido, EmailInvalido, TelefoneInvalido, SenhaInvalida {
        banco.criarConta("João","Joao@email.com", "83912345678", "a.aa@aoi1", 1000);
        banco.criarConta("Maria", "Maria@email.com", "83912345678", "a.aa@aoi2", 2000);
        banco.criarConta("José", "Jose@email.com", "83912345678", "a.aa@aoi3", 3000);
}
}





