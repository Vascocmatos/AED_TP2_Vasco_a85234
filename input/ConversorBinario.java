import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ConversorBinario {
    public static void converter(String ficheiroTexto, String ficheiroBinario) throws IOException {
        try (BufferedReader leitor = new BufferedReader(new InputStreamReader(
                new FileInputStream(ficheiroTexto), StandardCharsets.UTF_8));
             DataOutputStream escritor = new DataOutputStream(new FileOutputStream(ficheiroBinario))) {

            while (true) {
                String nome = lerLinhaObrigatoria(leitor);
                if (nome == null) {
                    return;
                }

                String data = lerLinhaObrigatoria(leitor);
                String numeroTexto = lerLinhaObrigatoria(leitor);

                if (data == null || numeroTexto == null) {
                    throw new IOException("Registo incompleto no ficheiro de texto.");
                }

                int[] partesData = converterData(data);
                int numero = converterNumero(numeroTexto);

                escritor.writeUTF(nome);.
                escritor.writeByte(partesData[0]);
                escritor.writeByte(partesData[1]);
                escritor.writeShort(partesData[2]);
                escritor.writeInt(numero);
            }
        }
    }

    private static String lerLinhaObrigatoria(BufferedReader leitor) throws IOException {
        String linha = leitor.readLine();
        if (linha == null) {
            return null;
        }

        linha = linha.trim();
        if (linha.isEmpty()) {
            throw new IOException("Foi encontrada uma linha vazia no ficheiro de texto.");
        }
        return linha;
    }

    private static int[] converterData(String data) throws IOException {
        String[] partes = data.split("/");
        if (partes.length != 3) {
            throw new IOException("Data inválida: " + data);
        }

        try {
            int dia = Integer.parseInt(partes[0]);
            int mes = Integer.parseInt(partes[1]);
            int ano = Integer.parseInt(partes[2]);

            if (dia < 1 || dia > 31 || mes < 1 || mes > 12
                    || ano < 1 || ano > 65535) {
                throw new IOException("Data inválida: " + data);
            }

            return new int[] { dia, mes, ano };
        } catch (NumberFormatException e) {
            throw new IOException("Data inválida: " + data, e);
        }
    }

    private static int converterNumero(String numeroTexto) throws IOException {
        try {
            return Integer.parseInt(numeroTexto);
        } catch (NumberFormatException e) {
            throw new IOException("Número inválido: " + numeroTexto, e);
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println(
                    "Uso: java -cp input ConversorBinario input\\dados.txt input\\dados.bin");
            return;
        }

        try {
            converter(args[0], args[1]);
            System.out.println("Conversão concluída.");
        } catch (IOException e) {
            System.err.println("Erro ao converter o ficheiro: " + e.getMessage());
        }
    }
}
