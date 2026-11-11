import java.io.File;
import java.util.Scanner;

import Pessoas.ListaPessoas;
import Pessoas.OrganizadorPessoas;
import Pessoas.Pessoa;
import R_W.Leitura;

public class Main {
    public static void main(String[] args) {
        String nomeFicheiro = "input\\dados.bin";

        if (args.length > 0) {
            nomeFicheiro = args[0];

            File ficheiroOriginal = new File(nomeFicheiro);
            if (!ficheiroOriginal.exists()) {
                File ficheiroNaInput = new File("input", nomeFicheiro);
                if (ficheiroNaInput.exists()) {
                    nomeFicheiro = ficheiroNaInput.getPath();
                }
            }
        }

        Scanner sc = new Scanner(System.in);
        Leitura leitura = new Leitura();
        ListaPessoas pessoas = leitura.leitura(nomeFicheiro);
        int opcao;

        do {
            System.out.println("1 - Listar");
            System.out.println("2 - Inserir");
            System.out.println("3 - Organizar pessoas");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");
            opcao = Integer.parseInt(sc.nextLine().trim());

            switch (opcao) {
                case 1:
                    System.out.println("A listar...");
                    pessoas.listarPessoas();
                    break;
                case 2:
                    System.out.print("Nome: ");
                    String nome = sc.nextLine().trim();
                    System.out.print("Data de nascimento: ");
                    String dataNascimento = sc.nextLine().trim();
                    System.out.print("Número: ");
                    String numero = sc.nextLine().trim();

                    pessoas.adicionarPessoa(new Pessoa(nome, dataNascimento, numero));
                    System.out.println("Pessoa inserida com sucesso.");
                    break;
                case 3:
                    OrganizadorPessoas organizador = new OrganizadorPessoas();
                    organizador.organizar(pessoas);

                    System.out.println("Mulheres:");
                    organizador.getMulheres().listarPessoas();
                    System.out.println("Homens:");
                    organizador.getHomens().listarPessoas();

                    if (organizador.getNaoClassificadas().tamanho() > 0) {
                        System.out.println("Não classificadas:");
                        organizador.getNaoClassificadas().listarPessoas();
                    }
                    break;
            }
        } while (opcao != 0);

        sc.close();
    }
}