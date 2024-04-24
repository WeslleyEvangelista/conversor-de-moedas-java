import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ConversorDeMoedas {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            exibirMenu();
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer do scanner

            switch (opcao) {
                case 1:
                    converterMoeda("USD", "ARS", scanner);
                    break;
                case 2:
                    converterMoeda("ARS", "USD", scanner);
                    break;
                case 3:
                    converterMoeda("USD", "BRL", scanner);
                    break;
                case 4:
                    converterMoeda("BRL", "USD", scanner);
                    break;
                case 5:
                    converterMoeda("USD", "COP", scanner);
                    break;
                case 6:
                    converterMoeda("COP", "USD", scanner);
                    break;
                case 7:
                    continuar = false;
                    System.out.println("Obrigado por utilizar o conversor de moedas. Até mais!");
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
                    break;
            }
        }
        scanner.close();
    }

    public static void exibirMenu() {
        System.out.println("Conversor de Moedas");
        System.out.println("1 - Dólar para Peso Argentino");
        System.out.println("2 - Peso Argentino para Dólar");
        System.out.println("3 - Dólar para Real");
        System.out.println("4 - Real para Dólar");
        System.out.println("5 - Dólar para Peso Colombiano");
        System.out.println("6 - Peso Colombiano para Dólar");
        System.out.println("7 - Sair");
        System.out.print("Escolha a opção desejada: ");
    }

    public static void converterMoeda(String moedaOrigem, String moedaDestino, Scanner scanner) {
        System.out.print("Digite o valor em " + moedaOrigem + ": ");
        double valor = scanner.nextDouble();
        scanner.nextLine(); // Limpar o buffer do scanner

        try {
            // URL da API para obter a taxa de câmbio
            String url = "https://api.exchangerate-api.com/v4/latest/" + moedaOrigem;

            // Fazendo a requisição HTTP
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStreamReader reader = new InputStreamReader(conn.getInputStream());
                JsonParser parser = new JsonParser();
                JsonObject jsonResponse = parser.parse(reader).getAsJsonObject();

                // Obtendo a taxa de câmbio da moeda de origem para a moeda de destino
                double taxaCambio = jsonResponse.getAsJsonObject("rates").get(moedaDestino).getAsDouble();

                // Realizando a conversão
                double valorConvertido = valor * taxaCambio;

                // Exibindo o resultado da conversão
                System.out.println("Valor em " + moedaOrigem + ": " + valor);
                System.out.println("Valor em " + moedaDestino + ": " + valorConvertido);
            } else {
                System.out.println("Erro ao realizar a conversão. Código de resposta HTTP: " + responseCode);
            }
        } catch (IOException e) {
            System.out.println("Erro ao realizar a conversão. Verifique sua conexão com a internet.");
        }
    }
}
