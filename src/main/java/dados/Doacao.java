package dados;

public abstract class Doacao {
    private String descricao;
    private double valor;
    private int quantidade;
    private Doador doador;

    public Doacao(String descricao, double valor, int quantidade, Doador doador){
        this.descricao = descricao;
        this.valor = valor;
        this.quantidade = quantidade;
        this.doador = doador;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getValor() {
        return valor;
    }

    public int getQuantidade() {
        return quantidade;
    }



    public Doador getDoador() {
        return doador;
    }

    public abstract String geraResumo();
}
