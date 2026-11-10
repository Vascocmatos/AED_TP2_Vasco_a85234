import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String nomeFicheiro = "input/dados.txt";

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
                    System.out.println("Inserido: " + nome);
                    break;
            }
        } while (opcao != 0);

        sc.close();
    }
}