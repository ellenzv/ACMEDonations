package aplicacao;

import dados.CatalogoDoacoes;
import dados.CatalogoDoadores;
import dados.Doador;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Scanner;

public class ACMEDonations {
    private Scanner input = new Scanner(System.in);
    private PrintStream saidaPadrao = System.out;
    private final String nomeArquivoSaida = "recursos/relatorio.txt";
    private final CatalogoDoadores catalogoDoadores;
    private final CatalogoDoacoes catalogoDoacoes;

    public ACMEDonations() {
        catalogoDoadores = new CatalogoDoadores();
        catalogoDoacoes = new CatalogoDoacoes(catalogoDoadores);
        input = new Scanner(System.in);
        redirecionaEntrada();
        //redirecionaSaida();
    }

    public void executar() {
        cadastrarDoadores();
        cadastrarDoacaoPerecivel();
        cadastrarDoacaoDuravel();
        consultarDoadorPorEmail();
        mostrarTodasDoacoes();
        quantidadeDoacoesDoador();
        mostrarDoacoesDoador();
        mostrarDoacoesDuravelPorTipo();

    }

    //1
    private void cadastrarDoadores() {
        catalogoDoadores.lerArquivoDoadores();
    }

    //2
    private void cadastrarDoacaoPerecivel() {
        catalogoDoacoes.lerArquivoDoacoesPereciveis();
    }

    //3
    private void cadastrarDoacaoDuravel() {
        catalogoDoacoes.lerArquivoDoacoesDuraveis();
    }

    //4
    private void consultarDoadorPorEmail() {
        String email = input.nextLine();
        Doador doador = catalogoDoadores.consultarDoadorPorEmail(email);

        if (doador == null) {
            System.out.println("4:ERRO:e-mail inexistente");
        } else {
            System.out.println("4:" + doador.getNome() + "," + doador.getEmail());
        }

    }

    //5
    public void mostrarTodasDoacoes() {
        catalogoDoacoes.mostrarDoacoesCadastradas();
    }

    //6
    private void quantidadeDoacoesDoador() {
        catalogoDoacoes.quantidadeDoacoesDoador();
    }

    //7
    private void mostrarDoacoesDoador(){
        String nome = input.nextLine();
        catalogoDoacoes.mostrarDoacoesDoador(nome);
    }

    //8
    private void mostrarDoacoesDuravelPorTipo(){
        String tipo = input.nextLine();
        catalogoDoacoes.mostrarDuraveisPorTipo(tipo);
    }

    //Códigos auxiliares para redirecionamento de entrada
    private void redirecionaEntrada() {
        Path path = Paths.get("recursos", "dadosentrada.txt");
        try {
            BufferedReader streamEntrada = Files.newBufferedReader(path, StandardCharsets.UTF_8);
            input = new Scanner(streamEntrada);
        } catch (Exception e) {
            System.out.println(e);
        }
        Locale.setDefault(Locale.ENGLISH);
        input.useLocale(Locale.ENGLISH);
    }

//    private void redirecionaSaida() {
//        try {
//            PrintStream streamSaida = new PrintStream(new File(nomeArquivoSaida), StandardCharsets.UTF_8);
//            System.setOut(streamSaida);
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        Locale.setDefault(Locale.ENGLISH);
//    }
//
//    private void restauraSaida(){
//        System.setOut(saidaPadrao);
//    }
}
