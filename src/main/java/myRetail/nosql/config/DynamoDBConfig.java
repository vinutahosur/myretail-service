package myRetail.nosql.config;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.PropertiesFileCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

import myRetail.nosql.repo.PriceRepository;

@Configuration
@EnableDynamoDBRepositories
  (basePackageClasses = PriceRepository.class)
public class DynamoDBConfig {
	 
	    @Bean
	    public AmazonDynamoDB amazonDynamoDB(AWSCredentialsProvider awsCredentialsProvider) {
	        AmazonDynamoDB amazonDynamoDB
	                = AmazonDynamoDBClientBuilder.standard()
	                .withCredentials(awsCredentialsProvider).withRegion(Regions.US_EAST_1).build();
	        return amazonDynamoDB;
	    }

	    @Bean
	    public AWSCredentialsProvider awsCredentialsProvider() {
	        return DefaultAWSCredentialsProviderChain.getInstance();
	    }
	}
