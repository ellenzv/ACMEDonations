package dados;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class CatalogoDoacoes {
    private List<Doacao> doacoes;
    private CatalogoDoadores catalogoDoadores;

    public CatalogoDoacoes(CatalogoDoadores catalogoDoadores) {
        this.doacoes = new ArrayList<>();
        this.catalogoDoadores = catalogoDoadores;
    }

    public void lerArquivoDoacoesPereciveis() {

        Path path = Paths.get("recursos", "doacoespereciveis.csv");
        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String linha;
            br.readLine();

            while ((linha = br.readLine()) != null) {
                Scanner sc = new Scanner(linha).useDelimiter(";");
                String descricao = sc.next();
                double valor = Double.parseDouble(sc.next());
                int quantidade = Integer.parseInt(sc.next());
                String email = sc.next();
                String tipo = sc.next();
                int validade = Integer.parseInt(sc.next());

                cadastrarDoacaoPerecivel(descricao, valor, quantidade, tipo, validade, email);
            }
        } catch (IOException e) {
            System.err.format("Erro ao ler o arquivo: %s%n", e);
        } catch (NumberFormatException e) {
            System.out.println("2:ERRO:Formato invalido");
        }
    }

    public void cadastrarDoacaoPerecivel(String descricao, double valor, int quantidade, String tipo, int validade, String email) {
        TipoPerecivel tipoPerecivel = null;
        try {
            tipoPerecivel = TipoPerecivel.valueOf(tipo.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("2:ERRO:tipo invalido");
            return;
        }

        Doador doador = this.catalogoDoadores.consultarDoadorPorEmail(email);
        if (doador == null) {
            System.out.println("2:ERRO:doador inexistente");
            return;
        }
        Perecivel novaDoacaoPerecivel = new Perecivel(descricao, valor, quantidade, tipoPerecivel, validade, doador);
        doacoes.add(novaDoacaoPerecivel);

        System.out.println("2:" + novaDoacaoPerecivel.getDescricao() + "," + novaDoacaoPerecivel.getValor() + "," +
                novaDoacaoPerecivel.getQuantidade() + "," + novaDoacaoPerecivel.getTipoPerecivel() + "," + novaDoacaoPerecivel.getValidade());
    }

    public void lerArquivoDoacoesDuraveis() {

        Path path = Paths.get("recursos", "doacoesduraveis.csv");
        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String linha;
            br.readLine();

            while ((linha = br.readLine()) != null) {
                Scanner sc = new Scanner(linha).useDelimiter(";");
                String descricao = sc.next();
                double valor = Double.parseDouble(sc.next());
                int quantidade = Integer.parseInt(sc.next());
                String email = sc.next();
                String tipo = sc.next();

                cadastrarDoacaoDuravel(descricao, valor, quantidade, tipo, email);
            }
        } catch (IOException e) {
            System.err.format("Erro ao ler o arquivo: %s%n", e);
        } catch (NumberFormatException e) {
            System.out.println("3:ERRO:Formato invalido");
        }
    }

    public void cadastrarDoacaoDuravel(String descricao, double valor, int quantidade, String tipo, String email) {
        TipoDuravel tipoDuravel = null;
        try {
            tipoDuravel = TipoDuravel.valueOf(tipo.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("3:ERRO:tipo invalido");
            return;
        }

        Doador doador = catalogoDoadores.consultarDoadorPorEmail(email);

        if (doador == null) {
            System.out.println("3:ERRO:doador inexistente");
            return;
        }

        Duravel novaDoacaoDuravel = new Duravel(descricao, valor, quantidade, tipoDuravel, doador);
        doacoes.add(novaDoacaoDuravel);
        System.out.println("3:" + novaDoacaoDuravel.getDescricao() + "," + novaDoacaoDuravel.getValor() + "," +
                novaDoacaoDuravel.getQuantidade() + "," + novaDoacaoDuravel.getTipoDuravel());
    }

    public void listarDoacoesCadastradas() {
        List<Doacao> listaDoacoes = new ArrayList<>();
        if (doacoes.isEmpty())
            System.out.println("5:ERRO:nenhuma doacao cadastrada");
        for (Doacao doacao : doacoes) {
            System.out.println("5:" + doacao.geraResumo());
        }
    }
}