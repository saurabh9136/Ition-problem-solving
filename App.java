package com.hibernate.BitCoinRate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
public class App 
{
	// use a hashMap dataStructure to store numbers with its word to convert numbers into word 
	  private static final Map<Integer, String> NUMBER_WORDS = new HashMap<>();

	    static { // use static block to use it in others functions
	        NUMBER_WORDS.put(0, "zero");
	        NUMBER_WORDS.put(1, "One"); // putting values inside the HashMap
	        NUMBER_WORDS.put(2, "Two");
	        NUMBER_WORDS.put(3, "Three");
	        NUMBER_WORDS.put(4, "Four");
	        NUMBER_WORDS.put(5, "Five");
	        NUMBER_WORDS.put(6, "Six");
	        NUMBER_WORDS.put(7, "Seven");
	        NUMBER_WORDS.put(8, "Eight");
	        NUMBER_WORDS.put(9, "Nine");
	        NUMBER_WORDS.put(10, "Ten");
	        NUMBER_WORDS.put(11, "Eleven");
	        NUMBER_WORDS.put(12, "Twelve");
	        NUMBER_WORDS.put(13, "Thirteen");
	        NUMBER_WORDS.put(14, "Fourteen");
	        NUMBER_WORDS.put(15, "Fifteen");
	        NUMBER_WORDS.put(16, "Sixteen");
	        NUMBER_WORDS.put(17, "Seventeen");
	        NUMBER_WORDS.put(18, "Eighteen");
	        NUMBER_WORDS.put(19, "Nineteen");
	        NUMBER_WORDS.put(20, "Twenty");
	        NUMBER_WORDS.put(30, "Thirty");
	        NUMBER_WORDS.put(40, "Forty");
	        NUMBER_WORDS.put(50, "Fifty");
	        NUMBER_WORDS.put(60, "Sixty");
	        NUMBER_WORDS.put(70, "Seventy");
	        NUMBER_WORDS.put(80, "Eighty");
	        NUMBER_WORDS.put(90, "Ninety");
	    }
	    // creating a function which convert the number into word
	    public static String convertToWords(int number) {
	        if (number == 0) { // if returned rate is 0 then print zero
	            return NUMBER_WORDS.get(number);
	        }
	        if (number < 20) {// because we manually entered number thats why we can directly return word between 1 to 20
	            return NUMBER_WORDS.get(number);
	        } else if (number < 100) {
	            int remainder = number % 10; // modules return the last word of any number
	            if (remainder == 0) {
	                return NUMBER_WORDS.get(number);
	            } else { // below code example like suppose number is 82 remainder is 2 if we divide 
	            	// it with 10 then the value is 8 multiply by 10 then 80 so the output will be eighty two
	                return NUMBER_WORDS.get(number / 10 * 10) + " " + NUMBER_WORDS.get(remainder);
	            }
	            // same approach for 3 and more digit numbers
	        } else if (number < 1000) {
	            int quotient = number / 100;
	            int remainder = number % 100;
	            if (remainder == 0) {
	                return NUMBER_WORDS.get(quotient) + " Hundred";
	            } else {
	                return NUMBER_WORDS.get(quotient) + " Hundred " + convertToWords(remainder);
	            }
	        } else if (number < 1000000) {
	            int quotient = number / 1000;
	            int remainder = number % 1000;
	            if (remainder == 0) {
	                return convertToWords(quotient) + " Thousand";
	            } else {
	                return convertToWords(quotient) + " Thousand " + convertToWords(remainder);
	            }
	        }

	        return "Number out of range";
	        
	    }
	    
	    
	
    public static void main( String[] args )
    {
		try {
			
			URL url = new URL("https://api.coindesk.com/v1/bpi/currentprice.json"); // save URL
			HttpURLConnection connection =(HttpURLConnection) url.openConnection();// creating a connection
			connection.setRequestMethod("GET");// setting a request method as GET
			
			int responsecode = connection.getResponseCode(); // store the value
			
			if(responsecode == HttpURLConnection.HTTP_OK) { // if HTTp return pass value OK thats mean the data is also fetched
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));// it allows to read the response data fromt the URl
				String inputLine;
				StringBuilder response = new StringBuilder();
				while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }
				reader.close();
				  
				JSONObject jsonResponse = new JSONObject(response.toString());
				JSONObject bpiObject = jsonResponse.getJSONObject("bpi");
                JSONObject usdObject = bpiObject.getJSONObject("USD");
                String rateString = usdObject.getString("rate");
             // Extract the integer part of the rate string
                String integerPart = rateString.split("\\.")[0];


             // Remove commas from the integer part
                String rateWithoutCommas = integerPart.replace(",", "");
             // Parse the rate as an integer
                int rate = Integer.parseInt(rateWithoutCommas);
                // Convert the rate to words
                String rateInWords = convertToWords(rate);

                System.out.println("Current Bitcoin rate: " +rateInWords +" USD");
            } else {
                System.out.println(" Unable to fetch Bitcoin rate. Response code: " + responsecode);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}	