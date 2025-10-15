package dados;

public class Perecivel extends Doacao{
    private int validade;
    private TipoPerecivel tipoPerecivel;

    public TipoPerecivel getTipoPerecivel() {
        return tipoPerecivel;
    }

    public int getValidade() {
        return validade;
    }

    public Perecivel(String descricao, double valor, int quantidade, TipoPerecivel tipoPerecivel, int validade, Doador doador) {
        super(descricao, valor, quantidade, doador);
        this.validade = validade;
        this.tipoPerecivel = tipoPerecivel;
    }

    @Override
    public String geraResumo(){
        return getDescricao() + "," + getValor() + "," + getQuantidade() + "," + getTipoPerecivel() + ","
                + getValidade() + "," + getDoador().getNome() + "," + getDoador().getEmail();
    }
}
