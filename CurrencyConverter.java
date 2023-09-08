import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class CurrencyConverter {
    private static final String API_URL = "https://api.apilayer.com/exchangerates_data/latest";
    private static final String API_KEY = "\"https://api.exchangerate.host/latest\""; // Replace with your API key

    static  class currency{
        String items[];
        int no_of_currencies;
        int favouritecurrencies;

        currency() {

            this.items = new String[10];
            final int no_of_currencies1 = this.no_of_currencies;
        }

        void addCurrency(String items) {
            this.items[no_of_currencies]=items;
            no_of_currencies++;
            System.out.println( items + " is the added currency");
        }
        void add_favorite_currency(String items){
            this.items[favouritecurrencies]=items;
            favouritecurrencies++;
            System.out.println( items + " is the favorite currency");
        }

        void showFavouriteCurrency(){
            System.out.println("Favourite currencies are : ");
            for (String favourite : this.items) {

                if (favourite==null){
                    continue;
                }
                System.out.println("*"+ favourite);

            }

        }



        void show_available_currencies(){
            System.out.println("Available currencies are : ");
            for (String item : this.items) {

                if (item==null){
                    continue;
                }
                System.out.println("*"+ item);

            }
        }
    }

    public static void main(String[] args) {
        currency cu = new currency();
        cu.addCurrency("Euro");
        cu.addCurrency("IND");
        cu.show_available_currencies();
        cu.add_favorite_currency("EURO");
        cu.add_favorite_currency("IND");
        cu.show_available_currencies();


        try {
            String baseCurrency = "IND";
            String targetCurrency = "USD";
            double amountToConvert = 100.0;

            // Build the API URL
            String urlString = "https://api.exchangerate.host/latest" + "?base=" + baseCurrency + "&symbols=" + targetCurrency;
            URL url = new URL(urlString);

            // Set up the HTTP connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + API_KEY);

            // Read and parse the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parse JSON response
            JSONObject jsonResponse = new JSONObject(response.toString());
            double exchangeRate = jsonResponse.getJSONObject("rates").getDouble(targetCurrency);
            double convertedAmount = amountToConvert * exchangeRate;

            System.out.println(amountToConvert + " " + baseCurrency + " is equal to " + convertedAmount + " " + targetCurrency);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
