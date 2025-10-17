package dados;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class CatalogoDoadores {
    private List<Doador> doadores;

    public CatalogoDoadores() {
        doadores = new ArrayList<>();
    }

    public void lerArquivoDoadores() {

        Path path = Paths.get("recursos", "doadores.csv");
        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String linha;
            br.readLine();

            while ((linha = br.readLine()) != null) {
                try {
                    Scanner sc = new Scanner(linha).useDelimiter(";");
                    String nome = sc.next();
                    String email = sc.next();

                    cadastrarDoador(nome, email);
                } catch (NoSuchElementException e) {
                    System.out.println("Erro");
                }
            }
        } catch (IOException e) {
            System.err.format("Erro ao ler o arquivo: %s%n", e);
        }
    }

    public void cadastrarDoador(String nome, String email) {
        for (Doador doador : doadores) {
            if (doador.getEmail().equals(email)) {
                System.out.println("1:ERRO:doador repetido");
                return;
            }
        }
        Doador novoDoador = new Doador(nome, email);
        doadores.add(novoDoador);
        System.out.println("1:" + novoDoador.getNome() + "," + novoDoador.getEmail());
    }

    public Doador consultarDoadorPorEmail(String email) {
        for (Doador doador : doadores) {
            if (doador.getEmail().equals(email))
                return doador;
        }
        return null;
    }

    public Doador consultarDoadorPorNome(String nome) {
        for (Doador doador : doadores){
            if(doador.getNome().equalsIgnoreCase(nome))
                return doador;
        }
        return null;
    }
}