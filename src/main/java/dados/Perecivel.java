package dados;

public class Perecivel extends Doacao{
    private int validade;
    private TipoPerecivel tipoPerecivel;

    public TipoPerecivel getTipoPerecivel() {
        return tipoPerecivel;
    }

    @Override
    public String geraResumo(){
        return "";
    }
}
