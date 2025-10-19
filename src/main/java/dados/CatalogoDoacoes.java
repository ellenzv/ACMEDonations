package dados;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class CatalogoDoacoes {
    private final List<Doacao> doacoes;
    private final CatalogoDoadores catalogoDoadores;

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
                try {
                    Scanner sc = new Scanner(linha).useDelimiter(";");
                    String descricao = sc.next();
                    double valor = Double.parseDouble(sc.next());
                    int quantidade = Integer.parseInt(sc.next());
                    String email = sc.next();
                    String tipo = sc.next();
                    int validade = Integer.parseInt(sc.next());

                    cadastrarDoacaoPerecivel(descricao, valor, quantidade, tipo, validade, email);
                } catch (NumberFormatException e) {
                    System.out.println("2:ERRO:formato invalido.");
                } catch (NoSuchElementException e) {
                    System.out.println("2:ERRO:formato invalido.");
                }
            }
        } catch (IOException e) {
            System.err.format("Erro ao ler o arquivo: %s%n", e);
        } catch (NumberFormatException e) {
            System.out.println("2:ERRO:formato invalido.");
        }
    }

    public void cadastrarDoacaoPerecivel(String descricao, double valor, int quantidade, String tipo, int validade, String email) {
        TipoPerecivel tipoPerecivel;
        try {
            tipoPerecivel = TipoPerecivel.valueOf(tipo.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("2:ERRO:tipo invalido.");
            return;
        }

        Doador doador = this.catalogoDoadores.consultarDoadorPorEmail(email);
        if (doador == null) {
            System.out.println("2:ERRO:doador inexistente.");
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
                try {
                    Scanner sc = new Scanner(linha).useDelimiter(";");
                    String descricao = sc.next();
                    double valor = Double.parseDouble(sc.next());
                    int quantidade = Integer.parseInt(sc.next());

                    String email = sc.next();
                    String tipo = sc.next();

                    cadastrarDoacaoDuravel(descricao, valor, quantidade, tipo, email);

                } catch (NumberFormatException e) {
                    System.out.println("3:ERRO:formato invalido.");
                } catch (NoSuchElementException e) {
                    System.out.println("3:ERRO:formato invalido.");
                }
            }
        } catch (IOException e) {
            System.err.format("Erro ao ler o arquivo: %s%n", e);
        }
    }

    public void cadastrarDoacaoDuravel(String descricao, double valor, int quantidade, String tipo, String email) {
        TipoDuravel tipoDuravel;
        try {
            tipoDuravel = TipoDuravel.valueOf(tipo.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("3:ERRO:tipo invalido.");
            return;
        }

        Doador doador = catalogoDoadores.consultarDoadorPorEmail(email);

        if (doador == null) {
            System.out.println("3:ERRO:doador inexistente.");
            return;
        }

        Duravel novaDoacaoDuravel = new Duravel(descricao, valor, quantidade, tipoDuravel, doador);
        doacoes.add(novaDoacaoDuravel);
        System.out.println("3:" + novaDoacaoDuravel.getDescricao() + "," + novaDoacaoDuravel.getValor() + "," +
                novaDoacaoDuravel.getQuantidade() + "," + novaDoacaoDuravel.getTipoDuravel());
    }

    public void mostrarDoacoesCadastradas() {
        if (doacoes.isEmpty())
            System.out.println("5:ERRO:nenhuma doacao cadastrada.");
        for (Doacao doacao : doacoes) {
            System.out.println("5:" + doacao.geraResumo());
        }
    }

    public void quantidadeDoacoesDoador() {
        doacoes.stream()
                .filter(doacao -> doacao.getDoador() != null)
                .collect(Collectors.groupingBy(Doacao::getDoador, Collectors.counting()))
                .forEach((doador, quantidade) -> System.out.println("6:" + doador.getNome() +
                        "," + doador.getEmail() + "," + quantidade));
    }

    public void mostrarDoacoesDoador(String nome) {
        Doador doador = catalogoDoadores.consultarDoadorPorNome(nome);
        boolean encontrado = false;

        for (Doacao doacao : doacoes) {
            if (doacao.getDoador().equals(doador)) {
                System.out.println("7:" + doacao.geraResumoSemNome());
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("7:ERRO:nenhuma doacao localizada.");
        }
    }


    public void mostrarDuraveisPorTipo(String tipo) {
        TipoDuravel tipoDuravel;

        try {
            tipoDuravel = TipoDuravel.valueOf(tipo.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("8:ERRO:tipo invalido.");
            return;
        }
        for (Doacao doacao : doacoes) {
            if (doacao instanceof Duravel doacaoDuravel) {
                if (doacaoDuravel.getTipoDuravel().equals(tipoDuravel))
                    System.out.println("8:" + doacao.geraResumo());
            }
        }
    }

    public void doacaoPerecivelMaiorQuantidade(String tipo) {
        int maior = 0;
        Perecivel maiorDoacaoPerecivel = null;
        TipoPerecivel tipoPerecivel;

        try {
            tipoPerecivel = TipoPerecivel.valueOf(tipo.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("9:ERRO:tipo invalido.");
            return;
        }

        for (Doacao doacao : doacoes) {
            if (doacao instanceof Perecivel doacaoPerecivel) {
                if (doacaoPerecivel.getTipoPerecivel().equals(tipoPerecivel))
                    if (doacaoPerecivel.getQuantidade() > maior) {
                        maior = doacaoPerecivel.getQuantidade();
                        maiorDoacaoPerecivel = doacaoPerecivel;
                    }
            }
        }
        if (maiorDoacaoPerecivel != null) {
            System.out.println("9:" + maiorDoacaoPerecivel.geraResumo());
        } else {
            System.out.println("9:ERRO:nenhuma doacao localizada.");
        }
    }

    public void doadorMaiorMontante() {
        if(doacoes.isEmpty()){
            System.out.println("10:ERRO:nenhum doador localizado");
        }

        Doador maiorDoador = null;
        double maiorMontante = 0;
        Map<Doador, Double> montanteDoador = doacoes.stream()
                .filter(doacao -> doacao.getDoador() != null)
                .collect(Collectors.groupingBy(Doacao::getDoador, Collectors.summingDouble(Doacao::getValor)));

        /*O for com map foi construído com ajuda do chatGPT porque não estava encontrando um jeito de percorrer o Map
        do montanteDoador. Prompt: chat, estou fazendo um método para achar o doador com maior montante. Eu tenho o
        seguinte código: Map<Doador, Double> montanteDoador = doacoes.stream()
                .filter(doacao -> doacao.getDoador() != null)
                .collect(Collectors.groupingBy(Doacao::getDoador, Collectors.summingDouble(Doacao::getValor)));
        minha ideia é fazer um for para percorrer o Map criado podendo comparar os montantes agrupados. Mas meu for está
        dando erro (for Doacao doacao : montanteDoador). O que está errado e como posso percorrer a coleção?*/

        for (Map.Entry<Doador, Double> busca : montanteDoador.entrySet()) {
            Doador doador = busca.getKey();
            double total = busca.getValue();

            if (total >= maiorMontante) {
                maiorMontante = total;
                maiorDoador = doador;
            }
        }
        if(maiorDoador != null)
            System.out.println("10:" + maiorDoador.getNome() + "," + maiorDoador.getEmail() + "," + maiorMontante);

        //método feito antes, mas que acabava pegando todas as doações e retornava 477.77
//        Doador maiorMontante = null;
//        double montante = 0;
//
//        for (Doacao doacao : doacoes) {
//            if (doacao.getValor() > montante) {
//                montante += doacao.getValor();
//                maiorMontante = doacao.getDoador();
//            }
//
//        }
//        if (maiorMontante != null) {
//            System.out.println("10:" + maiorMontante.getNome() +
//                    "," + maiorMontante.getEmail() + "," + montante);
//        } else {
//            System.out.println("10:ERRO:nenhum doador localizado.");
//        }
    }

}

