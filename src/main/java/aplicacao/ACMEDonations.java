package aplicacao;

import dados.CatalogoDoacoes;
import dados.CatalogoDoadores;

public class ACMEDonations {
    private final CatalogoDoadores catalogoDoadores;
    private final CatalogoDoacoes catalogoDoacoes;

    public ACMEDonations() {
        catalogoDoadores = new CatalogoDoadores();
        catalogoDoacoes = new CatalogoDoacoes(catalogoDoadores);
    }

    public void executar() {
        cadastrarDoadores();
        cadastrarDoacaoPerecivel();
    }

    private void cadastrarDoadores() {
        catalogoDoadores.lerArquivoDoadores();
    }

    private void cadastrarDoacaoPerecivel() {
        catalogoDoacoes.lerArquivoDoacoesPereciveis();
    }


}
