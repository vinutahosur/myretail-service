package myRetail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import myRetail.model.Product;
import myRetail.service.MyRetailService;

@RestController
public class MyRetailController {
	
	@Autowired
	MyRetailService myRetailService;

	@RequestMapping(value = "/myretail/products/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getProduct(@PathVariable("id") int id) throws Exception
	{
		Product product = myRetailService.getProduct(id);
		
		if(product == null)
		{
			return new ResponseEntity<Product>(product, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/myretail/products/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateProduct(@PathVariable("id") int id, @RequestBody Product product) throws Exception
	{
		Product updatedProduct = myRetailService.updateProduct(product);
		
		return new ResponseEntity<Product>(updatedProduct, HttpStatus.OK);
	}
	
	
}
