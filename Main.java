import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import Pessoas.ListaPessoas;
import Pessoas.DataNascimento;
import Pessoas.Pessoa;
import Pessoas.Genero;
import R_W.Leitura;

public class Main {
    public static void main(String[] args) {
        // lê o ficheiro binário com os dados das pessoas
        if (args.length == 0) {
            System.err.println("Utilização: java Main <ficheiro>");
            return;
        }

        String nomeFicheiro = args[0];
        // se o ficheiro não existir no diretório atual, procura na pasta input
        File ficheiroOriginal = new File(nomeFicheiro);
        if (!ficheiroOriginal.exists()) {
            File ficheiroNaInput = new File("input", nomeFicheiro);
            if (ficheiroNaInput.exists()) {
                nomeFicheiro = ficheiroNaInput.getPath();
            }
        }

        Scanner sc = new Scanner(System.in);
        Leitura leitura = new Leitura();
        ListaPessoas pessoas = leitura.leitura(nomeFicheiro);
        Genero genero;

        try {
            genero = new Genero();
            genero.organizar(pessoas);
        } catch (IOException e) {
            System.err.println("Erro ao preparar as listas por genero: " + e.getMessage());
            sc.close();
            return;
        }

        int opcao;
        do {
            System.out.println("#####################");
            System.out.println("1 - Listar");
            System.out.println("2 - Inserir");
            System.out.println("3 - Imprimir");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");
            opcao = Integer.parseInt(sc.nextLine().trim());

            switch (opcao) {
                case 1:
                    int opcaoListar;
                    do {
                        System.out.println("#####################");
                        System.out.println("1 - Listar todas");
                        System.out.println("2 - Dividir por genero");
                        System.out.println("3 - Maiores de 18 anos");
                        System.out.println("4 - Menores de 18 anos");
                        System.out.println("0 - Voltar");
                        System.out.print("Opção: ");
                        opcaoListar = Integer.parseInt(sc.nextLine().trim());

                        switch (opcaoListar) {
                            case 1:
                                pessoas.listarPessoas();
                                break;
                            case 2:
                                listarPorGenero(genero);
                                break;
                            case 3:
                                listarPorIdade(pessoas, true);
                                break;
                            case 4:
                                listarPorIdade(pessoas, false);
                                break;
                        }
                    } while (opcaoListar != 0);
                    break;
                case 2:
                    System.out.print("Nome: ");
                    String nome = sc.nextLine().trim();
                    System.out.print("Data de nascimento (DD/MM/AAAA): ");
                    String dataNascimento;
                    while (true) {
                        dataNascimento = sc.nextLine().trim();
                        try {
                            DataNascimento.converter(dataNascimento);
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Data inválida: " + e.getMessage());
                            System.out.print("Data de nascimento (DD/MM/AAAA): ");
                        }
                    }
                    System.out.print("Número: ");
                    String numero = sc.nextLine().trim();

                    Pessoa pessoa = new Pessoa(nome, dataNascimento, numero);
                    pessoas.adicionarPessoa(pessoa);
                    String classificacao = genero.adicionarPessoa(pessoa);
                    if (classificacao.equals("não classificado")) {
                        System.out.println("Pessoa inserida, mas o nome \""
                                + pessoa.getPrimeiroNome() + "\" não foi reconhecido ou não autorizado.");
                    } else {
                        System.out.println("Pessoa inserida como " + classificacao + ": "
                                + pessoa.getPrimeiroNome());
                    }
                    break;
                case 3:
                    try {
                        genero.gravarListas();
                        System.out.println("Ficheiros de mulheres e homens criados em output.");
                    } catch (IOException e) {
                        System.err.println("Não foi possível criar os ficheiros de saída: "
                                + e.getMessage());
                    }
                    break;
            }
        } while (opcao != 0);

        sc.close();
    }

    private static void listarPorGenero(Genero genero) {
        System.out.println("Mulheres:");
        genero.getMulheres().listarPessoas();
        System.out.println("Homens:");
        genero.getHomens().listarPessoas();

        if (genero.getNaoClassificadas().tamanho() > 0) {
            System.out.println("Não classificadas:");
            genero.getNaoClassificadas().listarPessoas();
            for (Pessoa pessoa : genero.getNaoClassificadas().getPessoas()) {
                System.out.println("Nome não reconhecido ou não autorizado: "
                        + pessoa.getPrimeiroNome());
            }
        }
    }

    private static void listarPorIdade(ListaPessoas pessoas, boolean maiores) {
        for (Pessoa pessoa : pessoas.getPessoas()) {
            if ((maiores && pessoa.getIdade() >= 18)
                    || (!maiores && pessoa.getIdade() < 18)) {
                System.out.println(pessoa);
            }
        }
    }
}