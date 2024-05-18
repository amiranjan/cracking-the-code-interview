import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.IntStream;

public class StockPriceSolution {
    private static final Map<Integer, LinkedHashMap<String, String>> stockPrices = new HashMap<>();
    private static class StockPrice {
        String date;
        // SPY -->
        //"VOO": --> 439.25
        LinkedHashMap<String, String> prices;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public LinkedHashMap<String, String> getPrices() {
            return prices;
        }

        public void setPrices(LinkedHashMap<String, String> prices) {
            this.prices = prices;
        }
    }

    private static void dataQualityCheck(StockPrice stockPrice) {
        stockPrice.getPrices().forEach((stock, price) -> {
            if ("".equals(price)) {
                System.out.println("Data quality issue found for stock " + stock + " on date " + stockPrice.getDate() + ". Getting stock details from " + (Integer.parseInt(stockPrice.getDate()) - 1));
                stockPrice.getPrices().put(stock, stockPrices.get(Integer.parseInt(stockPrice.getDate()) - 1).get(stock));
            }
        });
    }

    private static void insertStockPrice(Integer date) {
        try {
            HttpURLConnection connection = (HttpURLConnection)new URL("https://api.codesignalcontent.com/stockPrices/api/getPrices?date=" + date).openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setReadTimeout(10000);

            connection.connect();
            BufferedReader rd  = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = rd.readLine()) != null) {
                sb.append(line + '\n');
            }
            ObjectMapper objectMapper = new ObjectMapper();
            StockPrice stockPrice = objectMapper.readValue(sb.toString(), StockPrice.class);
            dataQualityCheck(stockPrice);
            stockPrices.put(date, stockPrice.getPrices());
        } catch (IOException e) {
            System.out.println(e.getMessage() + ". Error while getting stock price. Hence, using stock prices from " + (date - 1));
            stockPrices.put(date, stockPrices.get(date - 1));
        }
    }

    public static void main(String[] args) throws Exception {
        IntStream.range(20220101, 20220101 + 31).forEach(StockPriceSolution::insertStockPrice);
        System.out.println(stockPrices);
    }
}
