package dados;

public class Doador {
    private String nome;
    private String email;

    public Doador(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }
}
