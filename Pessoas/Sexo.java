package Pessoas;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class Sexo {
    private ListaPessoas mulheres;
    private ListaPessoas homens;
    private ListaPessoas naoClassificadas;
    private final Set<String> nomesFemininos;
    private final Set<String> nomesMasculinos;

    public Sexo() throws IOException {
        mulheres = new ListaPessoas();
        homens = new ListaPessoas();
        naoClassificadas = new ListaPessoas();
        nomesFemininos = lerNomes(Paths.get("Pessoas", "listas", "feminino"));
        nomesMasculinos = lerNomes(Paths.get("Pessoas", "listas", "masculino.txt"));
    }

    public void organizar(ListaPessoas pessoas) {
        mulheres = new ListaPessoas();
        homens = new ListaPessoas();
        naoClassificadas = new ListaPessoas();

        for (Pessoa pessoa : pessoas.getPessoas()) {
            adicionarPessoa(pessoa);
        }
    }

    public void adicionarPessoa(Pessoa pessoa) {
        String primeiroNome = pessoa.getPrimeiroNome().toLowerCase(Locale.ROOT);

        if (primeiroNome.endsWith("a") || nomesFemininos.contains(primeiroNome)) {
            mulheres.adicionarPessoa(pessoa);
        } else if (primeiroNome.endsWith("o") || nomesMasculinos.contains(primeiroNome)) {
            homens.adicionarPessoa(pessoa);
        } else {
            naoClassificadas.adicionarPessoa(pessoa);
        }
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

    private Set<String> lerNomes(Path ficheiro) throws IOException {
        Set<String> nomes = new HashSet<>();

        for (String linha : Files.readAllLines(ficheiro, StandardCharsets.UTF_8)) {
            String nome = linha.trim();
            if (!nome.isEmpty() && !nome.endsWith(":")) {
                nomes.add(nome.toLowerCase(Locale.ROOT));
            }
        }

        return nomes;
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
