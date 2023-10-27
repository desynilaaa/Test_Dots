package requests;

import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class NotAuthorized {
	@Test
	public void deleteAllBooksNotAuthorized() {
		
		RestAssured.baseURI = "https://bookstore.toolsqa.com";
		RequestSpecification request = RestAssured.given();
		
		String param = "?UserId=78be09ba-fe70-4582-9146-d8c01f658bef";
		request.header("Content-Type","application/json");
		Response response = request.delete("/BookStore/v1/Books" + param);
		
		System.out.println("Response Status Code for deleteAllBooks is "+response.getStatusCode());
		response.prettyPrint();
		
		String responseInString = response.asString();
		JsonPath jsonPath = new JsonPath(responseInString);
	    
		Assertions.assertEquals(401, response.getStatusCode());
	    Assertions.assertEquals("1200",jsonPath.getString("code"));
	    Assertions.assertEquals("User not authorized!",jsonPath.getString("message"));
	}
	
	@Test
	public void postBookNotAuthorized() {
		
		RestAssured.baseURI = "https://bookstore.toolsqa.com";
		RequestSpecification request = RestAssured.given();
		
		String payload = "{\r\n" + 
				"  \"userId\": \"78be09ba-fe70-4582-9146-d8c01f658bef\",\r\n" + 
				"  \"collectionOfIsbns\": [\r\n" + 
				"    {\r\n" + 
				"      \"isbn\": \"9781449331818\"\r\n" + 
				"    }\r\n" + 
				"  ]\r\n" + 
				"}";
		
		request.header("Content-Type","application/json");
		
		Response response = request.body(payload).post("/BookStore/v1/Books");
		
		System.out.println("Response Status Code addNewBookTest is "+response.getStatusCode());
		response.prettyPrint();
		
		String responseInString = response.asString();
		JsonPath jsonPath = new JsonPath(responseInString);
	    
		Assertions.assertEquals(401, response.getStatusCode());
	    Assertions.assertEquals("1200",jsonPath.getString("code"));
	    Assertions.assertEquals("User not authorized!",jsonPath.getString("message"));

	}
}
