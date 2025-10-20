package dados;

import java.util.ArrayList;
import java.util.List;

public class Doador {
    private String nome;
    private String email;
    private List<Doacao> doacoesCadastradas;

    public Doador(String nome, String email) {
        this.nome = nome;
        this.email = email;
        this.doacoesCadastradas = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public List<Doacao> getDoacoesCadastradas() {
        return doacoesCadastradas;
    }

    @Override
    public String toString() {
        return nome + ";" + email;
    }

}
