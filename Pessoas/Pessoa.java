package Pessoas;
public class Pessoa {
    private String nome;
    private String primeiroNome;
    private String apelidos;
    private String dataNascimento;
    private String numero;

    public Pessoa() {
    }

    public Pessoa(String nome, String dataNascimento, String numero) {
        this.setNomeCompleto(nome);
        this.dataNascimento = dataNascimento;
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.setNomeCompleto(nome);
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nome = nomeCompleto == null ? "" : nomeCompleto.trim();
        String[] partes = this.nome.split("\\s+");

        if (partes.length == 0 || this.nome.isEmpty()) {
            this.primeiroNome = "";
            this.apelidos = "";
            return;
        }

        this.primeiroNome = partes[0];
        this.apelidos = partes.length > 1 ? this.nome.substring(this.primeiroNome.length()).trim() : "";
    }

    public String getPrimeiroNome() {
        return primeiroNome;
    }

    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
        this.nome = primeiroNome + (this.apelidos == null || this.apelidos.isEmpty() ? "" : " " + this.apelidos);
    }

    public String getApelidos() {
        return apelidos;
    }

    public void setApelidos(String apelidos) {
        this.apelidos = apelidos == null ? "" : apelidos.trim();
        this.nome = primeiroNome + (this.apelidos.isEmpty() ? "" : " " + this.apelidos);
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Pessoa{"
                + "primeiroNome='" + primeiroNome + '\''
                + ", apelidos='" + apelidos + '\''
                + ", dataNascimento='" + dataNascimento + '\''
                + ", numero='" + numero + '\''
                + '}';
    }
}
