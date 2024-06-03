package api.utilities;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

import java.util.Map;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.RequestSender;
import io.restassured.specification.RequestSpecification;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


public class Utils {

 
	//1. logRequestDetails(RequestSpecification request): This method is used to log the request details, such as the request method, endpoint, headers, and body. 
	
	public static void logRequestDetails(RequestSpecification request) {
		
	    FilterableRequestSpecification filterableRequestSpecification = (FilterableRequestSpecification) request;
	    
	    System.out.println("Request Method: " + filterableRequestSpecification.getMethod());
	    System.out.println("Request Endpoint: " + filterableRequestSpecification.getURI());
	    
	    Headers headers = filterableRequestSpecification.getHeaders();
	    System.out.println("Request Headers:");
	    
	    for (Header header : headers) {
	        System.out.println(header.getName() + ": " + header.getValue());
	    }
	    
	    System.out.println("Request Body: " + filterableRequestSpecification.getBody());
	}	
	
	//2. logResponseHeaders(Response response): This method is used to log the response headers. 
	
	public static void logResponseHeaders(Response response) {
		
	    System.out.println("Response Headers: " + response.getHeaders());
	}
	
	
	//3. validateResponseTime(Response response, long expectedTime): This method is used to validate the response time against an expected time.
	
	public static void validateResponseTime(Response response, long expectedTime) {
		
	    long actualTime = response.getTime();
	    Assert.assertTrue(actualTime <= expectedTime, "Response time is greater than expected: " + actualTime + "ms");
	}
	
	
	/** 
	 * 4. validateJsonSchema(Response response, String schemaPath): This method is used to validate the response body against a JSON schema.
	 */
	
	public static void validateJsonSchema(Response response, String schemaPath) {
		
	    //response.then().assertThat().body(matchesJsonSchemaInClasspath(schemaPath));
	}
	
	/**
	 * 5. assertResponseStatus(Response response, int expectedStatus): This method is used to assert the response status code against an expected status code.s
	 */
	
	public static void assertResponseStatus(Response response, int expectedStatus) {
		
	    int actualStatus = response.getStatusCode();
	    
	    Assert.assertEquals(actualStatus, expectedStatus, "Response status code is not as expected: " + actualStatus);
	}
	
	
	// 6. validateStatusCode() - This function validates the status code of a given response.
	
	public static void validateStatusCode(Response response, int statusCode) {
		
	    response.then().assertThat().statusCode(statusCode);
	}
	
	
	//7. validateContentType() - This function validates the content type of a given response.
	
	public static void validateContentType(Response response, String contentType) {
		
	    response.then().assertThat().contentType(contentType);
	}
	
	
	//8. validateJsonKeyValue() - This function validates that a specific key-value pair exists in a given JSON response.
	
	public static void validateJsonKeyValue(Response response, String key, Object value) {
		
	    JsonPath jsonPath = response.jsonPath();
	    Object actualValue = jsonPath.get(key);
	    
	    Assert.assertEquals(actualValue, value);
	}
	
	
	//9. logCookies() - This function logs the cookies of a given response.
	
	public static void logCookies(Response response) {
		
	    System.out.println("Response Cookies:");
	    Map<String, String> cookies = response.getCookies();
	    
	    for (Map.Entry<String, String> cookie : cookies.entrySet()) {
	        System.out.println(cookie.getKey() + ": " + cookie.getValue());
	    }
	}
	
	
	//10. logRequestParams() - This function logs the request parameters of a given request.
	
	public static void logRequestParams(RequestSpecification request) {
		
	    System.out.println("Request Parameters:");
	    
	    //Map<String, String> queryParams = request.getQueryParams();
	    
	   // for (Map.Entry<String, String> param : queryParams.entrySet()) {
	    //    System.out.println(param.getKey() + ": " + param.getValue());
	    }
	//}
	
	
   //11. logRequestBody() - This function logs the request body of a given request.
	
	public static void logRequestBody(RequestSpecification request) {
	    System.out.println("Request Body:");
	    //System.out.println(request.getBody());
	}
	
	
	//12. validateResponseJsonPath() - This function validates that a specific JSON path expression matches a given expected value in a response.
	
	public static void validateResponseJsonPath(Response response, String jsonPath, String expectedValue) {
	    response.then().assertThat().body(jsonPath, equalTo(expectedValue));
	}
	
	
	
}