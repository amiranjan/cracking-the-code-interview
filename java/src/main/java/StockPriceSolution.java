import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockPriceSolution {
    private static class StockPrice {
        String date;
        // SPY -->
        //"VOO": --> 439.25
        Map<String, String> prices;
        public StockPrice() {
        }

        public StockPrice(String date, Map<String, String> prices) {
            this.date = date;
            this.prices = prices;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

    private static StockPrice get(Integer date) throws Exception {
        HttpURLConnection connection = (HttpURLConnection)new URL("https://api.codesignalcontent.com/stockPrices/api/getPrices?date=20220103").openConnection();

        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setReadTimeout(10000);

        connection.connect();
        BufferedReader rd  = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder sb = new StringBuilder();

        String line = null;
        while ((line = rd.readLine()) != null)
        {
            sb.append(line + '\n');
        }
        Map<String, Object> map = parse(sb.toString());
        System.out.println(map);
        ObjectMapper objectMapper = new ObjectMapper();
        StockPrice stockPrice = objectMapper.readValue(sb.toString(), StockPrice.class);
        System.out.println(stockPrice);

        return stockPrice;
    }

    public static Map<String, Object> parse(String json) {
        // Create a new map to store the parsed JSON data.
        Map<String, Object> data = new HashMap<>();

        // Split the JSON string into an array of key-value pairs.
        String[] pairs = json.split(",");

        // Iterate over the key-value pairs and add them to the map.
        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            String key = keyValue[0].trim();
            Object value = keyValue[1].trim();

            // If the value is a string, remove the surrounding quotes.
            if (value instanceof String) {
                if (value.toString().startsWith ("\"") && value.toString().endsWith("\"")) {
                    value = value.toString().substring(1, value.toString().length() - 1);
                }
            }

            // Add the key-value pair to the map.
            data.put(key, value);
        }

        // Return the parsed JSON data.
        return data;
    }

    public static void main(String[] args) throws Exception {

        List<Integer> dates = new ArrayList<>();
        dates.add(20220101);
        get(20220101);
        //IntStream.range(20220101,20220131).forEach(date ->);
        Map<Integer, StockPrice> stockPriceMap = new HashMap<>();
        for (Integer date: dates) {
            // Http clall to retrive prices
            Map<String, String> priceMap = new HashMap<>();
            priceMap.put("SPY", null);
            priceMap.put("VOO", "439.25");
            priceMap.put("IVV", "479.84");
            priceMap.put("FTSE", "676.0");
            priceMap.put("MCHI", null);

            StockPrice stockPrice = new StockPrice(String.valueOf(date), priceMap);

            stockPriceMap.put(date, stockPrice);
        }
        // Http call to retrieve prices
        Map<String, String> priceMap = new HashMap<>();
        priceMap.put("SPY", "567.54");
        priceMap.put("VOO", "439.25");
        priceMap.put("IVV", "479.84");
        priceMap.put("FTSE", "676.9");
        priceMap.put("MCHI", "345.8");

        StockPrice stockPrice = new StockPrice(String.valueOf(20220102), priceMap);

        stockPriceMap.put(20220102, stockPrice);

        /*for (Map.Entry<Integer, StockPrice> dateStockPrice : stockPriceMap.entrySet()) {
                if (dateStockPrice.price == null)
                    stockPriceMap.put(company, stockPriceMap.get(date + 1).prices.get(company));
        }

        System.out.println(stockPriceMap);*/
    }
}
