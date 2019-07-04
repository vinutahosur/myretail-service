package myRetail.service;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import myRetail.model.Price;
import myRetail.model.Product;
import myRetail.nosql.repo.PriceRepository;
import myRetail.rest.client.RedSkyRestClient;

@RunWith(MockitoJUnitRunner.class)
public class MyRetailServiceTest {
	
		@Mock
		RedSkyRestClient redSkyRestClientMock = Mockito.mock(RedSkyRestClient.class);
		
		@Mock
		PriceRepository priceRepositoryMock = Mockito.mock(PriceRepository.class);
		
		@InjectMocks
		MyRetailService myRetailService  = new MyRetailService();
	
	
	    @Test
	    public void testGetProduct() throws Exception { 
	
			myRetailService.setProductPriceRepo(priceRepositoryMock);
			myRetailService.setRedSkyRestClient(redSkyRestClientMock);
	    	
	    	int productId = 1;
	    	String name = "ProductA";
	    	Float price = new Float(9.99);
	    	String currency = "USD";
	    	
	    	Price priceA = new Price(1,price,currency);
	    	
	    	Mockito.when(redSkyRestClientMock.getProductName(productId)).thenReturn(name);
	    	Mockito.when(priceRepositoryMock.findById(new Integer(1))).thenReturn(Optional.of(priceA));
	    	
	    	Product product = myRetailService.getProduct(productId);
	    	
	    	assertEquals(productId, product.getId());
	    	assertEquals(name, product.getName());
	    	assertEquals(price, product.getPrice().getValue());
	    	assertEquals(currency, product.getPrice().getCurrencyCode());
	    }
	    
	    @Test
	    public void testUpdateProduct() throws Exception { 
	
			myRetailService.setProductPriceRepo(priceRepositoryMock);
			myRetailService.setRedSkyRestClient(redSkyRestClientMock);
	    	
	    	int productId = 1;
	    	String name = "ProductA";
	    	Float price1 = new Float(9.99);
	    	String currency = "USD";
	    	
	    	Float price2 = new Float(10.00);
	    	
	    	Price priceA = new Price(1,price1,currency);
	    	Price priceB = new Price(1, price2, currency);
	    	
	    	Product productToUpdate = new Product(productId, name, priceB);
	    	
	    	Mockito.when(redSkyRestClientMock.getProductName(productId)).thenReturn(name);
	    	Mockito.when(priceRepositoryMock.findById(new Integer(1))).thenReturn(Optional.of(priceA));
	    	Mockito.when(priceRepositoryMock.save(Mockito.any(Price.class))).thenReturn(priceB);
	    	
	    	Product updatedProduct = myRetailService.updateProduct(productToUpdate);
	    	
	    	assertEquals(productId, updatedProduct.getId());
	    	assertEquals(name, updatedProduct.getName());
	    	assertEquals(price2, updatedProduct.getPrice().getValue());
	    	assertEquals(currency, updatedProduct.getPrice().getCurrencyCode());
	    }
	

}
