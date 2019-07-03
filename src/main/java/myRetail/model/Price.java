package myRetail.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@DynamoDBTable(tableName = "ProductPrice")
public class Price 
{
	private Integer id;
    private Float value;
    private String currencyCode;
    
    public Price(Integer id, Float value, String currencyCode) {
		this.id = id;
		this.value = value;
		this.currencyCode = currencyCode;
	}
    
	public Price() {

	}

	@DynamoDBHashKey
    @JsonIgnore
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@DynamoDBAttribute
	@JsonProperty("currency_code")
	public String getCurrencyCode() {
		return currencyCode;
	}
	
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	@DynamoDBAttribute
	public Float getValue() {
		return value;
	}
	
	public void setValue(Float value) {
		this.value = value;
	}

    
    
}
