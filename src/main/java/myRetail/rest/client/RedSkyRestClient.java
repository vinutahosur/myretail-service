package myRetail.rest.client;

import java.net.URI;
import java.net.URISyntaxException;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class RedSkyRestClient {
	
	private final RestTemplate restTemplate;

	public RedSkyRestClient(RestTemplateBuilder restTemplateBuilder) {
		
		this.restTemplate = restTemplateBuilder.build();
	}
	
	public String getProductName(int id) throws Exception
	{
		try {
			String name = null;
			
			URI endpoint = getRedskyURI(id);
			
			String productDetails;
			try {
				productDetails = restTemplate.getForObject(endpoint, String.class);
			} catch (HttpClientErrorException e) {
				if(e.getStatusCode().equals(HttpStatus.NOT_FOUND))
				{
					//If the Redsky returns a 404, myRetail will also return a 404
					//For any other error we will return a 500
					System.out.println("Product not found on Redsky");
					return null;
				}
				throw e;
			}
			
			JSONObject jsonObject = new JSONObject(productDetails);
			
			JSONObject productObject = jsonObject.getJSONObject("product");
			JSONObject itemObject =  productObject.getJSONObject("item");
			JSONObject descriptionObject = itemObject.getJSONObject("product_description");
			
			name = descriptionObject.getString("title");
			
			System.out.println(name);
			
			return name;
			
		} catch (RestClientException e) {
			throw e;
		} catch (URISyntaxException e) {
			throw e;
		} catch (JSONException e) {
			throw e;
		}
	}

	private URI getRedskyURI(int id) throws URISyntaxException {
		return new URI(String.format(REDSKY_ENDPOINT, id));
	}
	
	private final static String REDSKY_ENDPOINT = "http://redsky.target.com/v2/pdp/tcin/%s?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics";

}
