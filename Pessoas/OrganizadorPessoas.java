package Pessoas;

import java.util.Locale;

public class OrganizadorPessoas {
    private ListaPessoas mulheres;
    private ListaPessoas homens;
    private ListaPessoas naoClassificadas;

    public OrganizadorPessoas() {
        mulheres = new ListaPessoas();
        homens = new ListaPessoas();
        naoClassificadas = new ListaPessoas();
    }

    public void organizar(ListaPessoas pessoas) {
        mulheres = new ListaPessoas();
        homens = new ListaPessoas();
        naoClassificadas = new ListaPessoas();

        for (Pessoa pessoa : pessoas.getPessoas()) {
            String primeiroNome = pessoa.getPrimeiroNome().toLowerCase(Locale.ROOT);

            if (primeiroNome.endsWith("a")) {
                mulheres.adicionarPessoa(pessoa);
            } else if (primeiroNome.endsWith("o")) {
                homens.adicionarPessoa(pessoa);
            } else {
                naoClassificadas.adicionarPessoa(pessoa);
            }
        }
    }

    public ListaPessoas getMulheres() {
        return mulheres;
    }

    public ListaPessoas getHomens() {
        return homens;
    }

    public ListaPessoas getNaoClassificadas() {
        return naoClassificadas;
    }
}
