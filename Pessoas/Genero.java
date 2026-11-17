package Pessoas;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.List;

public class Genero {
    private ListaPessoas mulheres;
    private ListaPessoas homens;
    private ListaPessoas naoClassificadas;
    private final List<String> nomesFemininos;
    private final List<String> nomesMasculinos;

    public Genero() throws IOException {
        mulheres = new ListaPessoas();
        homens = new ListaPessoas();
        naoClassificadas = new ListaPessoas();
        nomesFemininos = lerNomes(Paths.get("Pessoas", "listas", "feminino_limpo.txt"));
        nomesMasculinos = lerNomes(Paths.get("Pessoas", "listas", "masculino_limpo.txt"));
    }

    public void organizar(ListaPessoas pessoas) {
        mulheres = new ListaPessoas();
        homens = new ListaPessoas();
        naoClassificadas = new ListaPessoas();

        for (Pessoa pessoa : pessoas.getPessoas()) {
            adicionarPessoa(pessoa);
        }
    }

    public String adicionarPessoa(Pessoa pessoa) {
        String primeiroNome = pessoa.getPrimeiroNome().toLowerCase(Locale.ROOT);
        boolean nomeMasculino = pesquisaBinaria(nomesMasculinos, primeiroNome);
        boolean nomeFeminino = pesquisaBinaria(nomesFemininos, primeiroNome);

        if (nomeMasculino) {
            homens.adicionarPessoa(pessoa);
            return "homem";
        }

        if (nomeFeminino) {
            mulheres.adicionarPessoa(pessoa);
            return "mulher";
        }

        if (primeiroNome.endsWith("o")) {
            homens.adicionarPessoa(pessoa);
            return "homem";
        }

        if (primeiroNome.endsWith("a")) {
            mulheres.adicionarPessoa(pessoa);
            return "mulher";
        }

        naoClassificadas.adicionarPessoa(pessoa);
        return "não classificado";
    }

    public void gravarListas() throws IOException {
        Files.createDirectories(Paths.get("output"));
        gravarLista(mulheres, Paths.get("output", "mulheres.bin"));
        gravarLista(homens, Paths.get("output", "homens.bin"));
    }

    private void gravarLista(ListaPessoas lista, Path ficheiro) throws IOException {
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(ficheiro.toFile()))) {
            for (Pessoa pessoa : lista.getPessoas()) {
                String[] partesData = pessoa.getDataNascimento().split("/");
                int dia = Integer.parseInt(partesData[0]);
                int mes = Integer.parseInt(partesData[1]);
                int ano = Integer.parseInt(partesData[2]);
                int numero = Integer.parseInt(pessoa.getNumero());

                out.writeUTF(pessoa.getNome());
                out.writeByte(dia);
                out.writeByte(mes);
                out.writeShort(ano);
                out.writeInt(numero);
            }
        } catch (NumberFormatException e) {
            throw new IOException("Dados inválidos ao gravar uma lista binária.", e);
        }
    }

    private List<String> lerNomes(Path ficheiro) throws IOException {
        List<String> nomes = new ArrayList<>();

        for (String linha : Files.readAllLines(ficheiro, StandardCharsets.UTF_8)) {
            String nome = linha.trim();
            if (!nome.isEmpty() && !nome.endsWith(":")) {
                nomes.add(nome.toLowerCase(Locale.ROOT));
            }
        }

        Collections.sort(nomes);
        return nomes;
    }

    private boolean pesquisaBinaria(List<String> nomes, String nome) {
        int minimo = 0;
        int maximo = nomes.size() - 1;

        while (minimo <= maximo) {
            int meio = minimo + (maximo - minimo) / 2;
            int comparacao = nome.compareTo(nomes.get(meio));

            if (comparacao == 0) {
                return true;
            }
            if (comparacao < 0) {
                maximo = meio - 1;
            } else {
                minimo = meio + 1;
            }
        }

        return false;
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
