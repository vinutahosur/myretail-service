package myRetail.service;

import java.net.URISyntaxException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import myRetail.model.Product;
import myRetail.model.Price;
import myRetail.nosql.repo.PriceRepository;
import myRetail.rest.client.RedSkyRestClient;

@Component
public class MyRetailService 
{
	@Autowired
	RedSkyRestClient redSkyRestClient;
	
	@Autowired
	PriceRepository priceRepository;
	
	public void setProductPriceRepo(PriceRepository productPriceRepo)
	{
		this.priceRepository = productPriceRepo;
	}
	
	public void setRedSkyRestClient(RedSkyRestClient redSkyRestClient) {
		this.redSkyRestClient = redSkyRestClient;
	}
	
	public Product getProduct(int id) throws Exception
	{
		String name = getName(id);	
		if(name == null)
		{
			return null;
		}
		Price price = getPrice(id);
		
		Product product = new Product(id, name, price);
		
		return product;		
	}
	
	public Product updateProduct(Product product)
	{
		Price price = getPrice(product.getId());
		price.setCurrencyCode(product.getPrice().getCurrencyCode());
		price.setValue(product.getPrice().getValue());
		
		Price newPrice = updatePrice(price);
		product.setPrice(newPrice);
		
		return product;
	}

	private Price updatePrice(Price price) 
	{
		return priceRepository.save(price);
	}

	private Price getPrice(int id) 
	{
		Optional<Price> optional = priceRepository.findById(id);
		if(optional.isPresent())
		{
			return optional.get();
		}
		else
		{
			return null;
		}
	}
	
	private String getName(int id) throws Exception
	{
		return redSkyRestClient.getProductName(id);
	}
}
