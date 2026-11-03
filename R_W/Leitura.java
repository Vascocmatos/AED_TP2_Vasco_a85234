Public Class Leitura {
    Public int[] leitura(String nomeFicheiro, int n) {
        int[] vetor = new int[n];

        try (DataInputStream in = new DataInputStream(new FileInputStream(nomeFicheiro))) {
            for (int i = 0; i < n; i++) {
                vetor[i] = in.readshort(); 
                

            }
            System.out.println(Arrays.toString(vetor));
        } catch (IOException e) {
            System.out.println("Erro a ler o ficheiro binário");
        }
        return vetor;
    }
}

