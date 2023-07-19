package dados.dados;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

public class IMDBShows {

    public static void main(String[] args) throws CsvValidationException {
        String csvFile = "src/main/resources/IMDB.csv";

        Map<Integer, String[]> showMap = new HashMap<>();
        Set<String[]> showsUnicos = new LinkedHashSet<>();

        try (CSVReader leitor = new CSVReader(new FileReader(csvFile));
             Scanner scanner = new Scanner(System.in)) {

            String[] linha;
            int index = 0;

            while ((linha = leitor.readNext()) != null) {
                showMap.put(index, linha);
                if (!showsUnicos.contains(linha)) {
                    showsUnicos.add(linha);
                }
                index++;
            }
            
            int escolha;

            do {
                System.out.println("======= Menu =======");
                System.out.println("1. Exibir total de shows");
                System.out.println("2. Exibir total de shows únicos");
                System.out.println("3. Pesquisar show por nome");
                System.out.println("4. Pesquisar show por índice");
                System.out.println("5. Exibir shows ordenados");
                System.out.println("6. Cadastrar novo show");
                System.out.println("0. Sair");
                System.out.print("Escolha uma opção: ");
                escolha = scanner.nextInt();

                switch (escolha) {
                    case 1:
                        exibirTotalShows(showMap);
                        break;
                    case 2:
                        exibirTotalShowsUnicos(showsUnicos);
                        break;
                    case 3:
                        pesquisarShowPorNome(showMap, scanner);
                        break;
                    case 4:
                        pesquisarShowPorIndice(showMap, scanner);
                        break;
                    case 5:
                        exibirShowsOrdenados(showsUnicos);
                        break;
                    case 6:
                        cadastrarNovoShow(showsUnicos, scanner);
                        break;
                    case 0:
                        System.out.println("Encerrando o programa...");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
                System.out.println();
            } while (escolha != 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void exibirTotalShows(Map<Integer, String[]> showsMapa) {
        System.out.println("Total de shows: " + showsMapa.size());
    }

    private static void exibirTotalShowsUnicos(Set<String[]> showsUnicos) {
        System.out.println("Total de shows únicos: " + showsUnicos.size());
    }

    private static void pesquisarShowPorNome(Map<Integer, String[]> showsMapa, Scanner scanner) {
        System.out.print("Digite o nome do show a ser pesquisado: ");
        String buscaShow = scanner.next();

        boolean encontrado = false;

        for (Map.Entry<Integer, String[]> entrada : showsMapa.entrySet()) {
            String[] show = entrada.getValue();
            if (show[0].toLowerCase().contains(buscaShow.toLowerCase())) {
                System.out.println("Show encontrado: " + show[0]);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            System.out.println("Show não encontrado.");
        }
    }

    private static void pesquisarShowPorIndice(Map<Integer, String[]> showsMapa, Scanner scanner) {
        System.out.print("Digite o índice do show a ser pesquisado: ");
        int indice = scanner.nextInt();

        String[] show = showsMapa.get(indice);

        if (show != null) {
            System.out.println("Show encontrado: " + show[0]);
        } else {
            System.out.println("Show não encontrado.");
        }
    }

    private static void exibirShowsOrdenados(Set<String[]> showsOrdenados) {
        List<String[]> sortedShows = new ArrayList<>(showsOrdenados);
        sortedShows.sort(Comparator.comparing(a -> a[0]));

        System.out.println("Shows ordenados:");
        for (String[] show : sortedShows) {
            System.out.println(show[0]);
        }
    }
    
    private static void cadastrarNovoShow(Set<String[]> showsUnicos, Scanner scanner) throws IOException {
        scanner.nextLine();

        System.out.print("Digite o nome do show: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o ano de lançamento: ");
        String ano = scanner.nextLine();

        System.out.print("Digite o número de episódios: ");
        String episodios = scanner.nextLine();

        System.out.print("Digite a classificação indicativa: ");
        String classificacao = scanner.nextLine();

        System.out.print("Digite a avaliação: ");
        String avaliacao = scanner.nextLine();

        System.out.print("Digite a sinopse: ");
        String sinopse = scanner.nextLine();

        System.out.print("Digite o link da imagem: ");
        String linkImagem = scanner.nextLine();

        System.out.print("Digite o link do IMDb: ");
        String linkIMDB = scanner.nextLine();

        String[] novoShow = {nome, ano, episodios, classificacao, avaliacao, linkImagem, sinopse, linkIMDB};
        showsUnicos.add(novoShow);

        //atualiza o arquivo CSV com o novo show ( ainda não deu 100% certo )
        try (CSVWriter writer = new CSVWriter(new FileWriter("src/main/resources/IMDB.csv", true))) {
            writer.writeNext(novoShow);
        }

        System.out.println("Novo show cadastrado com sucesso!");
    }
}
