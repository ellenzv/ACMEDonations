package dados;

public class Duravel extends Doacao{
    private TipoDuravel tipoDuravel;

    public Duravel(String descricao, double valor, int quantidade, TipoDuravel tipoDuravel,  Doador doador) {
        super(descricao, valor, quantidade, doador);
        this.tipoDuravel = tipoDuravel;
    }

    public TipoDuravel getTipoDuravel() {
        return tipoDuravel;
    }

    @Override
    public String geraResumo(){
        return getDescricao() + "," + getValor() + "," + getQuantidade() + "," + getTipoDuravel() + ","
                + getDoador().getNome() + "," + getDoador().getEmail();
    }
}
