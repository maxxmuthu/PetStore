package api.test;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configurator;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;
import api.utilities.Log;
import api.endpoints.Routes;
import api.endpoints.StoreEndPoints;
import api.payload.Store;
import api.utilities.TestNGListener;
import io.restassured.response.Response;


public class StoreTests extends Routes {  // here we extend Routes only for getting 'testCaseId' 

// Here we created the tests only for Store module
	
	Faker faker;
	Store storePayload;
	
	@BeforeClass
	public void setupData() 
	{
		faker = new Faker();
		storePayload = new Store();
		
		int id = faker.number().numberBetween(1, 10);	
		int petId = faker.number().numberBetween(1, 100);
		int quantity = faker.number().numberBetween(1, 10);
		String shipDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
		boolean complete = faker.bool().bool();
		
		// Create storePayload object
		storePayload.setId(id);
		storePayload.setPetId(petId);
		storePayload.setQuantity(quantity);
		storePayload.setShipDate(shipDate);
		storePayload.setStatus("placed");
		storePayload.setComplete(complete);	
	}
	
	
	
	@Test(priority=1)
	public void testPostStoreOrder() 
	{	
		testCaseId = 12344;
		
		// Logging the message
		Log.startTestCase("Store Module Scenario_01");
		Log.info("This is Store Module Scenario_01");
		
		Response response = StoreEndPoints.createStoreOrder(storePayload);		
		response.then().log().body();

		String headervalue=response.getHeader("Content-Type");

		//System.out.println("Header: " + headervalue);
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(headervalue, "application/json");

		System.out.println("<---- Store Order is created with below details ---->");

		System.out.println("Id: " + storePayload.getId());
		System.out.println("Pet Id: " + storePayload.getPetId());
		System.out.println("Quantity: " + storePayload.getQuantity());
		System.out.println("Ship Date: " + storePayload.getShipDate());
		System.out.println("Status: " + storePayload.getStatus());
		System.out.println("Complete: " + storePayload.getComplete()); 

	    Log.endTestCase("Store Module Scenario_01");
	}	
	
	
	@Test(priority=2)
	public void testGetStoreOrder() 
	{
		testCaseId = 12345;

		// Logging the message
		Log.startTestCase("Store Module Scenario_02");
		Log.info("This is Store Module Scenario_02");
		
		Response response = StoreEndPoints.readStoreOrder(this.storePayload.getId());
		response.then().log().body()
		               .header("Content-Type", "application/json");
		
		Assert.assertEquals(response.getStatusCode(), 200);
			
		Assert.assertEquals(response.jsonPath().getInt("id"), storePayload.getId());
		Assert.assertEquals(response.jsonPath().getInt("petId"), storePayload.getPetId());
		Assert.assertEquals(response.jsonPath().getInt("quantity"), storePayload.getQuantity());
		Assert.assertEquals(response.jsonPath().getString("status"), "placed");
		Assert.assertEquals(response.jsonPath().getString("complete"), "true");
		
	    Log.endTestCase("Store Module Scenario_02");
	}
	
		
	@Test(priority=3)
	public void testDeleteStoreOrder()
	{
		
		testCaseId = 12346;

		// Logging the message
		Log.startTestCase("Store Module Scenario_03");
		Log.info("This is Store Module Scenario_03");
		
	  Response response=StoreEndPoints.deleteStoreOrder(this.storePayload.getId());
	  Assert.assertEquals(response.getStatusCode(),200);
	  
	    Log.endTestCase("Store Module Scenario_03");
	}


	
}