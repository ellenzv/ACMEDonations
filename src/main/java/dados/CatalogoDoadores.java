package dados;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;

public class CatalogoDoadores {
    private List<Doador> doadores;

    public CatalogoDoadores() {
        doadores = new ArrayList<>();
    }

    public void lerArquivo() {
        List<Doador> novosDoadores = new ArrayList<>();
        Path path = Paths.get("recursos","doadores.csv");
        try (BufferedReader br = Files.newBufferedReader(path,
                Charset.forName("UTF8"))) {
            String linha = null;
            while ((linha = br.readLine()) != null) {
                // separador: ;
                Scanner sc = new Scanner(linha).useDelimiter(";");
                String nome;
                String email;
                nome = sc.next();
                email = sc.next();
                Doador novoDoador = new Doador(nome, email);
                for (Doador doador : doadores){
                    if(!doador.getEmail().equals(novoDoador.getEmail())){
                        doadores.add(novoDoador);
                    }else{
                        System.out.println("1:ERRO:doador repetido");
                    }
                }
            }
        }
        catch (IOException e) {
            System.err.format("Erro de E/S: %s%n", e);
        }
    }
}