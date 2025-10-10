package dados;

public class Duravel extends Doacao{
    private TipoDuravel tipoDuravel;

    public TipoDuravel getTipoDuravel() {
        return tipoDuravel;
    }

    @Override
    public String geraResumo(){
        return "";
    }
}
