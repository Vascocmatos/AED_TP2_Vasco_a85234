package listas;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LimparListas {
    private static final Path PASTA_LISTAS = Paths.get("Pessoas", "listas");

    public static void main(String[] args) {
        Path ficheiroFeminino = PASTA_LISTAS.resolve("feminino");
        Path ficheiroMasculino = PASTA_LISTAS.resolve("masculino.txt");

        if (args.length == 2) {
            ficheiroFeminino = Paths.get(args[0]);
            ficheiroMasculino = Paths.get(args[1]);
        } else if (args.length != 0) {
            System.err.println(
                    "Uso: java listas.LimparListas [ficheiro-feminino ficheiro-masculino]");
            return;
        }

        try {
            limpar(ficheiroFeminino, PASTA_LISTAS.resolve("feminino_limpo.txt"), 'a');
            limpar(ficheiroMasculino, PASTA_LISTAS.resolve("masculino_limpo.txt"), 'o');
            System.out.println("Listas limpas com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao limpar as listas: " + e.getMessage());
        }
    }

    private static void limpar(Path origem, Path destino, char terminacaoAEliminar)
            throws IOException {
        List<String> linhas = Files.readAllLines(origem, StandardCharsets.UTF_8);
        List<String> linhasMantidas = new ArrayList<>();

        for (String linha : linhas) {
            String nome = linha.trim();
            if (nome.isEmpty() || !primeiroNomeTerminaEm(nome, terminacaoAEliminar)) {
                linhasMantidas.add(linha);
            }
        }

        Files.write(destino, linhasMantidas, StandardCharsets.UTF_8);
    }

    private static boolean primeiroNomeTerminaEm(String nomeCompleto, char terminacao) {
        String primeiroNome = nomeCompleto.split("\\s+")[0];
        return primeiroNome.toLowerCase(Locale.ROOT).endsWith(String.valueOf(terminacao));
    }
}
