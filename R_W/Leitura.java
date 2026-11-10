import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Leitura {
    public ListaPessoas leitura(String nomeFicheiro) {
        ListaPessoas lista = new ListaPessoas();
        ByteArrayOutputStream linhaAtual = new ByteArrayOutputStream();

        String nomeCompleto = null;
        String dataNascimento = null;
        String numero = null;
        int campo = 0;

        try (FileInputStream in = new FileInputStream(nomeFicheiro)) {
            int byteLido;

            while ((byteLido = in.read()) != -1) {
                int valorAscii = byteLido & 0xFF;

                if (valorAscii == 10 || valorAscii == 13) {
                    String linha = new String(linhaAtual.toByteArray(), StandardCharsets.UTF_8).trim();
                    linhaAtual.reset();

                    if (linha.isEmpty()) {
                        continue;
                    }

                    if (campo == 0) {
                        nomeCompleto = linha;
                    } else if (campo == 1) {
                        dataNascimento = linha;
                    } else if (campo == 2) {
                        numero = linha;
                    }

                    campo++;

                    if (campo == 3) {
                        lista.adicionarPessoa(new Pessoa(nomeCompleto, dataNascimento, numero));
                        nomeCompleto = null;
                        dataNascimento = null;
                        numero = null;
                        campo = 0;
                    }

                    continue;
                }

                linhaAtual.write(valorAscii);
            }

            String ultimaLinha = new String(linhaAtual.toByteArray(), StandardCharsets.UTF_8).trim();
            if (!ultimaLinha.isEmpty()) {
                if (campo == 0) {
                    nomeCompleto = ultimaLinha;
                } else if (campo == 1) {
                    dataNascimento = ultimaLinha;
                } else if (campo == 2) {
                    numero = ultimaLinha;
                }

                campo++;

                if (campo == 3) {
                    lista.adicionarPessoa(new Pessoa(nomeCompleto, dataNascimento, numero));
                }
            }

        } catch (IOException e) {
            System.out.println("Erro a ler o ficheiro binário");
        }

        return lista;
    }
}

