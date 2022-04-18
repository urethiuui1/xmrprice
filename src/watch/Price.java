package watch;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Price {
	private String coin = "monero";
	private String price = "0.0";
	private Double change = 0.0;
	
	Price(String coin){
		this.coin = coin;
	}
	public void getData() throws IOException, ParseException {
		URL url = new URL("https://api.coincap.io/v2/assets/" + coin);
		HttpURLConnection http = (HttpURLConnection)url.openConnection();
		http.setRequestProperty("Accept", "application/json");
		http.disconnect();
		
		
        if (http.getResponseCode() != 200) {
            throw new RuntimeException("HttpResponseCode: " + http.getResponseCode());
        } else {

            String inline = "";
            Scanner scanner = new Scanner(url.openStream());

            //Write all the JSON data into a string using a scanner
            while (scanner.hasNext()) {
                inline += scanner.nextLine();
            }

            //Close the scanner
            scanner.close();

            //Using the JSON simple library parse the string into a json object
            JSONParser parse = new JSONParser();
            JSONObject data_obj = (JSONObject) parse.parse(inline);
            //Get the required object from the above created object
            JSONObject obj = (JSONObject) data_obj.get("data");

            //Parse and set values
            setPrice(((Double)round(Double.parseDouble((String)obj.get("priceUsd")), 2)).toString());
            setChange(round(Double.parseDouble((String)obj.get("changePercent24Hr")), 2));
            }
	}
	public String getPrice() {
		return this.price;
	}
	public Double getChange() {
		return this.change;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public void setChange(Double change) {
		this.change = change;
	}
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();
	    BigDecimal bd = BigDecimal.valueOf(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}
	