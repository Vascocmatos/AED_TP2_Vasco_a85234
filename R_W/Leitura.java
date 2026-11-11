package R_W;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;

import Pessoas.ListaPessoas;
import Pessoas.Pessoa;

public class Leitura {
    public ListaPessoas leitura(String nomeFicheiro) {
        ListaPessoas lista = new ListaPessoas();
        try (DataInputStream in = new DataInputStream(new FileInputStream(nomeFicheiro))) {
            while (true) {
                String nomeCompleto;

                try {
                    nomeCompleto = in.readUTF();
                } catch (EOFException e) {
                    break;
                }

                int dia = in.readUnsignedByte();
                int mes = in.readUnsignedByte();
                int ano = in.readUnsignedShort();
                int numero = in.readInt();

                String dataNascimento = String.format("%02d/%02d/%04d", dia, mes, ano);
                lista.adicionarPessoa(new Pessoa(nomeCompleto, dataNascimento,
                        String.valueOf(numero)));
            }
        } catch (IOException e) {
            System.err.println("Erro a ler o ficheiro binário: " + e.getMessage());
        }

        return lista;
    }
}
