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

    public String lerArquivoDoacoesPereciveis() {
        StringBuilder sb = new StringBuilder();

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

                    sb.append(cadastrarDoacaoPerecivel(descricao, valor, quantidade, tipo, validade, email))
                            .append("\n");

                } catch (NumberFormatException e) {
                    sb.append("2:ERRO:formato invalido.").append("\n");
                } catch (NoSuchElementException e) {
                    sb.append("2:ERRO:formato invalido.").append("\n");
                }
            }
        } catch (IOException e) {
            sb.append("Erro ao ler o arquivo").append("\n");
        } catch (NumberFormatException e) {
            sb.append("2:ERRO:formato invalido.").append("\n");
        }

        if (!sb.isEmpty()) {
            sb.setLength(sb.length() - 1);
        }

        return sb.toString();
    }

    public String cadastrarDoacaoPerecivel(String descricao, double valor, int quantidade, String tipo, int validade, String email) {
        TipoPerecivel tipoPerecivel;
        StringBuilder resultadoSaida = new StringBuilder();

        try {
            tipoPerecivel = TipoPerecivel.valueOf(tipo.toUpperCase());
        } catch (IllegalArgumentException e) {
            return "2:ERRO:tipo invalido.";

        }

        Doador doador = this.catalogoDoadores.consultarDoadorPorEmail(email);
        if (doador == null) {
            return "2:ERRO:doador inexistente.";
        }
        Perecivel novaDoacaoPerecivel = new Perecivel(descricao, valor, quantidade, tipoPerecivel, validade, doador);
        doacoes.add(novaDoacaoPerecivel);
        doador.getDoacoesCadastradas().add(novaDoacaoPerecivel);

        resultadoSaida.append("2:")
                .append(novaDoacaoPerecivel.getDescricao()).append(",")
                .append(novaDoacaoPerecivel.getValor()).append(",")
                .append(novaDoacaoPerecivel.getQuantidade()).append(",")
                .append(novaDoacaoPerecivel.getTipoPerecivel()).append(",")
                .append(novaDoacaoPerecivel.getValidade());

        return resultadoSaida.toString();
    }

    public String lerArquivoDoacoesDuraveis() {
        StringBuilder sb = new StringBuilder();

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

                    sb.append(cadastrarDoacaoDuravel(descricao, valor, quantidade, tipo, email)).append("\n");

                } catch (NumberFormatException e) {
                    sb.append("3:ERRO:formato invalido.").append("\n");
                } catch (NoSuchElementException e) {
                    sb.append("3:ERRO:formato invalido.").append("\n");
                }
            }
        } catch (IOException e) {
            sb.append("Erro ao ler o arquivo").append("\n");
        }

        if (!sb.isEmpty()) {
            sb.setLength(sb.length() - 1);
        }

        return sb.toString();
    }

    public String cadastrarDoacaoDuravel(String descricao, double valor, int quantidade, String tipo, String email) {
        TipoDuravel tipoDuravel;
        StringBuilder sb = new StringBuilder();
        try {
            tipoDuravel = TipoDuravel.valueOf(tipo.toUpperCase());
        } catch (IllegalArgumentException e) {
            return "3:ERRO:tipo invalido.";

        }

        Doador doador = catalogoDoadores.consultarDoadorPorEmail(email);

        if (doador == null) {
            return "3:ERRO:doador inexistente.";

        }

        Duravel novaDoacaoDuravel = new Duravel(descricao, valor, quantidade, tipoDuravel, doador);
        doacoes.add(novaDoacaoDuravel);
        doador.getDoacoesCadastradas().add(novaDoacaoDuravel);
        sb.append("3:")
                .append(novaDoacaoDuravel.getDescricao()).append(",")
                .append(novaDoacaoDuravel.getValor()).append(",")
                .append(novaDoacaoDuravel.getQuantidade()).append(",")
                .append(novaDoacaoDuravel.getTipoDuravel());
        return sb.toString();
    }

    public String mostrarDoacoesCadastradas() {
        if (doacoes.isEmpty())
            return "5:ERRO:nenhuma doacao cadastrada.";

        return doacoes.stream()
                .map(doacao -> "5:" + doacao.geraResumo())
                .collect(Collectors.joining("\n"));
    }

    public String quantidadeDoacoesDoador() {
        List<Doador> auxiliar = new ArrayList<>(catalogoDoadores.getDoadores());

        if (auxiliar.isEmpty()) {
            return "6:ERRO:nenhum doador encontrado.";
        }
//        auxiliar.sort((doador1, doador2) -> Integer.compare(
//                doador2.getDoacoesCadastradas().size(),
//                doador1.getDoacoesCadastradas().size()
//        ));

        return auxiliar.stream()
                .map(doador -> "6:" + doador.getNome() + "," + doador.getEmail() + "," + doador.getDoacoesCadastradas().size())
                .collect(Collectors.joining("\n"));

//        for(Doador doador : auxiliar){
//            System.out.println("6:" + doador.getNome() + "," + doador.getEmail() + ","
//                    + doador.getDoacoesCadastradas().size());
//        }
    }

    public String mostrarDoacoesDoador(String nome) {
        StringBuilder sb = new StringBuilder();

        Doador doador = catalogoDoadores.consultarDoadorPorNome(nome);
        boolean encontrado = false;

        for (Doacao doacao : doacoes) {
            if (doacao.getDoador().equals(doador)) {
                sb.append("7:")
                        .append(doacao.geraResumoSemNome())
                        .append("\n");
                encontrado = true;
            }
        }
        if (!encontrado) {
            return "7:ERRO:nenhuma doacao localizada.";
        }

        if (!sb.isEmpty())
            sb.setLength(sb.length() - 1);

        return sb.toString();
    }

    public String mostrarDuraveisPorTipo(String tipo) {
        TipoDuravel tipoDuravel;
        StringBuilder sb = new StringBuilder();

        try {
            tipoDuravel = TipoDuravel.valueOf(tipo.toUpperCase());
        } catch (IllegalArgumentException e) {
            return "8:ERRO:tipo invalido.";
        }
        for (Doacao doacao : doacoes) {
            if (doacao instanceof Duravel doacaoDuravel) {
                if (doacaoDuravel.getTipoDuravel().equals(tipoDuravel))
                    sb.append("8:")
                            .append(doacao.geraResumo())
                            .append("\n");
            }
        }

        if(!sb.isEmpty())
            sb.setLength(sb.length()-1);

        return sb.toString();
    }

    public String doacaoPerecivelMaiorQuantidade(String tipo) {
        int maior = 0;
        Perecivel maiorDoacaoPerecivel = null;
        TipoPerecivel tipoPerecivel;
        StringBuilder sb = new StringBuilder();

        try {
            tipoPerecivel = TipoPerecivel.valueOf(tipo.toUpperCase());
        } catch (IllegalArgumentException e) {
            return "9:ERRO:tipo invalido.";
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
            sb.append("9:")
                    .append(maiorDoacaoPerecivel.geraResumo());
        } else {
            return "9:ERRO:nenhuma doacao localizada.";
        }
        return sb.toString();
    }

    public String doadorMaiorMontante() {
        List<Doador> doadores = catalogoDoadores.getDoadores();
        StringBuilder sb = new StringBuilder();

        if (doadores.isEmpty()) {
            return "10:ERRO:nenhum doador localizado.";
        }

        Doador maiorDoador = null;
        double maiorMontante = 0;

        for (Doador doador : doadores) {
            double montante = doador.getDoacoesCadastradas().stream()
                    .mapToDouble(Doacao::getValor)
                    .sum();

            if (montante >= maiorMontante) {
                maiorMontante = montante;
                maiorDoador = doador;
            }
        }

        if (maiorDoador != null) {
            sb.append("10:")
                    .append(maiorDoador.getNome()).append(",")
                    .append(maiorDoador.getEmail()).append(",")
                    .append(maiorMontante);
        }

        return sb.toString();
    }

}

