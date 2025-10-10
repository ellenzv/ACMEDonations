package dados;

public abstract class Doacao {
    private String descricao;
    private double valor;
    private int quantidade;
    public Doador doador;

    public Doador getDoador() {
        return doador;
    }

    public abstract String geraResumo();
}
