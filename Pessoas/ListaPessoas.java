package Pessoas;
import java.util.ArrayList;

public class ListaPessoas {
    private ArrayList<Pessoa> pessoas;

    public ListaPessoas() {
        this.pessoas = new ArrayList<>();
    }

    public void adicionarPessoa(Pessoa pessoa) {
        pessoas.add(pessoa);
    }

    public void removerPessoa(int indice) {
        if (indice >= 0 && indice < pessoas.size()) {
            pessoas.remove(indice);
        }
    }

    public Pessoa getPessoa(int indice) {
        if (indice >= 0 && indice < pessoas.size()) {
            return pessoas.get(indice);
        }
        return null;
    }

    public int tamanho() {
        return pessoas.size();
    }

    public ArrayList<Pessoa> getPessoas() {
        return pessoas;
    }

    public void listarPessoas() {
        for (int i = 0; i < pessoas.size(); i++) {
            System.out.println((i + 1) + " - " + pessoas.get(i));
        }
    }
}
