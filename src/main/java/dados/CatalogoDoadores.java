package dados;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class CatalogoDoadores {
    private List<Doador> doadores;

    public CatalogoDoadores() {
        doadores = new ArrayList<>();
    }

    public String lerArquivoDoadores() {
        StringBuilder resultadoSaida = new StringBuilder();

        Path path = Paths.get("recursos", "doadores.csv");
        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String linha;
            br.readLine();

            while ((linha = br.readLine()) != null) {
                try {
                    Scanner sc = new Scanner(linha).useDelimiter(";");
                    String nome = sc.next();
                    String email = sc.next();

                    resultadoSaida.append(cadastrarDoador(nome, email)).append("\n");

                } catch (NoSuchElementException e) {
                    resultadoSaida.append("1:ERRO:formato invalido.").append("\n");
                }
            }
        } catch (IOException e) {
            resultadoSaida.append("Erro ao ler o arquivo").append("\n");
        }

        if (!resultadoSaida.isEmpty()) {
            resultadoSaida.setLength(resultadoSaida.length() - 1);
        }

        return resultadoSaida.toString();
    }

    public String cadastrarDoador(String nome, String email) {
        for (Doador doador : doadores) {
            if (doador.getEmail().equals(email)) {
                return "1:ERRO:doador repetido.";
            }
        }
        Doador novoDoador = new Doador(nome, email);
        doadores.add(novoDoador);
        return "1:" + novoDoador.getNome() + "," + novoDoador.getEmail();
    }

    public Doador consultarDoadorPorEmail(String email) {
        for (Doador doador : doadores) {
            if (doador.getEmail().equals(email))
                return doador;
        }
        return null;
    }

    public Doador consultarDoadorPorNome(String nome) {
        for (Doador doador : doadores) {
            if (doador.getNome().equalsIgnoreCase(nome))
                return doador;
        }
        return null;
    }

    public List<Doador> getDoadores() {
        List<Doador> listaAux = new ArrayList<>();

        for (Doador doador : doadores) {
            if (doador != null)
                listaAux.add(doador);
        }
        return listaAux;
    }
}