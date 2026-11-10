public class TesteLeitura {
    public static void main(String[] args) {
        Leitura leitura = new Leitura();
        ListaPessoas lista = leitura.leitura("input/dados.txt");
        System.out.println("Total: " + lista.tamanho());
        for (int i = 0; i < lista.tamanho(); i++) {
            Pessoa p = lista.getPessoa(i);
            System.out.println(p.getPrimeiroNome() + " | " + p.getApelidos() + " | " + p.getDataNascimento() + " | " + p.getNumero());
        }
    }
}
