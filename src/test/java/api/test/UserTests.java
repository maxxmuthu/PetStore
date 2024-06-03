package api.test;

import org.testng.annotations.BeforeClass;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configurator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.Test;

import api.utilities.ReadExcel;
import api.utilities.Log;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserTests {

	
// Here we created the tests only for User module
	
	Faker faker;
	User userPayload;
	
	@BeforeClass
	public void setupData() 
	{
		faker = new Faker();
		userPayload = new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
	}
	
	@Test(priority=1)
	public void testPostUser() 
	{
		// Logging the message
		Log.startTestCase("User Module Scenario_01");
		Log.info("This is User Module Scenario_01");
				
		Response response = UserEndPoints.createUser(userPayload);
		response.then().log().body();
		
		System.out.println("<---- User is created with below details ---->");
		
		System.out.println("Id: " + userPayload.getId());
		System.out.println("Username: " + userPayload.getUsername());
		System.out.println("Firstname: " + userPayload.getFirstName());
		System.out.println("Lastname: " + userPayload.getLastName());
		System.out.println("Email: " + userPayload.getEmail());
		System.out.println("Password: " + userPayload.getPassword());
		System.out.println("Phone: " + userPayload.getPhone());
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
	    Log.endTestCase("User Module Scenario_01");
				
	}
	
	
	@Test(priority=2)
	public void testGetUser() 
	{
		// Logging the message
		Log.startTestCase("User Module Scenario_02");
		Log.info("This is User Module Scenario_02");
		   
		Response response = UserEndPoints.readUser(this.userPayload.getUsername());
		response.then().log().body()
		               .header("Content-Type", "application/json");
		
		Assert.assertEquals(response.getStatusCode(), 200);
			
		Assert.assertEquals(response.jsonPath().getInt("id"), userPayload.getId());
		Assert.assertEquals(response.jsonPath().getString("username"), userPayload.getUsername());
		Assert.assertEquals(response.jsonPath().getString("firstName"), userPayload.getFirstName());
		Assert.assertEquals(response.jsonPath().getString("lastName"), userPayload.getLastName());
		Assert.assertEquals(response.jsonPath().getString("email"), userPayload.getEmail());
		Assert.assertEquals(response.jsonPath().getString("password"), userPayload.getPassword());
		Assert.assertEquals(response.jsonPath().getString("phone"), userPayload.getPhone());
				
	    Log.endTestCase("User Module Scenario_02");
	}
	
	
	@Test(priority=3, dataProvider="ExcelDataRead", dataProviderClass = api.utilities.ReadExcel.class) //pass the DataProvider class from the utility package // ReadExcel class is used in this run
	public void testUpdateUserByName(String data[])
	{
		// Logging the message
		Log.startTestCase("User Module Scenario_03");
		Log.info("This is User Module Scenario_03");
		
		System.out.println("<----Before Update ---->");
				
		System.out.println("Username: " + userPayload.getUsername());
		System.out.println("Firstname: " + userPayload.getFirstName());
		System.out.println("Lastname: " + userPayload.getLastName());
		System.out.println("Email: " + userPayload.getLastName());
		System.out.println("Password: " + userPayload.getLastName());
		System.out.println("Phone: " + userPayload.getLastName());
		
		
	  //Update the data using 'User' class from payload package
		
		userPayload.setId(userPayload.getId());
		userPayload.setUsername(data[1]);
		userPayload.setFirstName(data[2]);
		userPayload.setLastName(data[3]);
		userPayload.setEmail(data[4]);
		userPayload.setPassword(data[5]);
		userPayload.setPhone(data[6]);
		
	 /*  userPayload.setUsername(faker.name().username());
	   userPayload.setFirstName(faker.name().firstName());
	   userPayload.setLastName(faker.name().lastName()); */

	   Response response=UserEndPoints.updateUser(this.userPayload.getUsername(),userPayload);
	   response.then().log().body();

	   Assert.assertEquals(response.getStatusCode(),200);

	  //Checking the data after update
	   Response responseAfterupdate=UserEndPoints.updateUser(this.userPayload.getUsername(),userPayload);
	   Assert.assertEquals(responseAfterupdate.getStatusCode(),200);
	   
	   System.out.println("<---- After Update ---->");
	   
	   System.out.println("Username: " + userPayload.getUsername());
	   System.out.println("Firstname: " + userPayload.getFirstName());
	   System.out.println("Lastname: " + userPayload.getLastName());
	   
	    Log.endTestCase("User Module Scenario_03");

	}

	//@Test(priority=4)
	public void testDeleteUserByName()
	{
	    // Logging the message
		Log.startTestCase("User Module Scenario_04");
		Log.info("This is User Module Scenario_04");
		
	  Response response=UserEndPoints.deleteUser(this.userPayload.getUsername());
	  Assert.assertEquals(response.getStatusCode(),200);
	  
	    Log.endTestCase("User Module Scenario_04");

	}

	@Test(priority=5)
	public void testLoginUser() 
	{
		
		// Logging the message
		Log.startTestCase("User Module Scenario_05");
		Log.info("This is User Module Scenario_05");
		
		Response response = UserEndPoints.loginUser(this.userPayload.getUsername(), this.userPayload.getPassword(), userPayload);
		response.then().log().body()
		               .header("Content-Type", "application/json");
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
	    Log.endTestCase("User Module Scenario_05");
		
	}	
	
	
	//@Test(priority=6)
	public void testLogoutUser() 
	{
		// Logging the message
		Log.startTestCase("User Module Scenario_06");
		Log.info("This is User Module Scenario_06");
		
		Response response = UserEndPoints.LogoutUser();
		response.then().log().body()
		               .header("Content-Type", "application/json");
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().getString("message"), "ok");
		
	    Log.endTestCase("User Module Scenario_06");
	}	
	
	
}