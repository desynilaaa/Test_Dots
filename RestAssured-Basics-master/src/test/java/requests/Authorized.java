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

public class Authorized {
	@Test
	@Order(1)
	public void deleteAllBooks() {
		
		RestAssured.baseURI = "https://bookstore.toolsqa.com";
		RequestSpecification request = RestAssured.given();
		String credentials = "nila:123Qwerty*";
		byte[] encodedCredentials = Base64.encodeBase64(credentials.getBytes());
		String encodedCredentialsAsString = new String(encodedCredentials);
		request.header("Authorization","Basic "+encodedCredentialsAsString);
		
		String param = "?UserId=78be09ba-fe70-4582-9146-d8c01f658bef";
		request.header("Content-Type","application/json");
		Response response = request.delete("/BookStore/v1/Books" + param);
		
		System.out.println("Response Status Code for deleteAllBooks is "+response.getStatusCode());
//		response.prettyPrint();
	    
		Assertions.assertEquals(204, response.getStatusCode());
	}
	
	@Test
	@Order(2)
	public void addNewBookTest() {
		
		RestAssured.baseURI = "https://bookstore.toolsqa.com";
		RequestSpecification request = RestAssured.given();
		String credentials = "nila:123Qwerty*";
		byte[] encodedCredentials = Base64.encodeBase64(credentials.getBytes());
		String encodedCredentialsAsString = new String(encodedCredentials);
		request.header("Authorization","Basic "+encodedCredentialsAsString);
		
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
//		response.prettyPrint();
		
		String responseInString = response.asString();
		JsonPath jsonPath = new JsonPath(responseInString);
	    
		Assertions.assertEquals(201, response.getStatusCode());
	    Assertions.assertEquals("9781449331818",jsonPath.getString("books[0].isbn"));

	}
	
	@Test
	@Order(3)
	public void isbnAlreadyAddedTest() {
		
		RestAssured.baseURI = "https://bookstore.toolsqa.com";
		RequestSpecification request = RestAssured.given();
		String credentials = "nila:123Qwerty*";
		byte[] encodedCredentials = Base64.encodeBase64(credentials.getBytes());
		String encodedCredentialsAsString = new String(encodedCredentials);
		request.header("Authorization","Basic "+encodedCredentialsAsString);
		
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
		
		String responseInString = response.asString();
		JsonPath jsonPath = new JsonPath(responseInString);
	    
		Assertions.assertEquals(400, response.getStatusCode());
	    Assertions.assertEquals("1210",jsonPath.getString("code"));
	    Assertions.assertEquals("ISBN already present in the User's Collection!",jsonPath.getString("message"));
		
		System.out.println("Response Status Code for isbnAlreadyAddedTest is "+response.getStatusCode());
//		response.prettyPrint();
	     
	}
	
	
	@Test
	@Order(4)
	public void isbnNotValid() {
		
		RestAssured.baseURI = "https://bookstore.toolsqa.com";
		RequestSpecification request = RestAssured.given();
		String credentials = "nila:123Qwerty*";
		byte[] encodedCredentials = Base64.encodeBase64(credentials.getBytes());
		String encodedCredentialsAsString = new String(encodedCredentials);
		request.header("Authorization","Basic "+encodedCredentialsAsString);
		
		String payload = "{\r\n" + 
				"  \"userId\": \"78be09ba-fe70-4582-9146-d8c01f658bef\",\r\n" + 
				"  \"collectionOfIsbns\": [\r\n" + 
				"    {\r\n" + 
				"      \"isbn\": \"9781449325999\"\r\n" + 
				"    }\r\n" + 
				"  ]\r\n" + 
				"}";
		
		request.header("Content-Type","application/json");
		
		Response response = request.body(payload).post("/BookStore/v1/Books");
		
		System.out.println("Response Status Code isbnNotValid is "+response.getStatusCode());
//		response.prettyPrint();
		
		String responseInString = response.asString();
		JsonPath jsonPath = new JsonPath(responseInString);
	    
		Assertions.assertEquals(400, response.getStatusCode());
	    Assertions.assertEquals("1205",jsonPath.getString("code"));
	    Assertions.assertEquals("ISBN supplied is not available in Books Collection!",jsonPath.getString("message"));
	}
	
	@Test
	@Order(5)
	public void wrongUserId() {
		
		RestAssured.baseURI = "https://bookstore.toolsqa.com";
		RequestSpecification request = RestAssured.given();
		String credentials = "nila:123Qwerty*";
		byte[] encodedCredentials = Base64.encodeBase64(credentials.getBytes());
		String encodedCredentialsAsString = new String(encodedCredentials);
		request.header("Authorization","Basic "+encodedCredentialsAsString);
		
		String payload = "{\r\n" + 
				"  \"userId\": \"78be09ba-fe70-4582-9146-d8c01f6\",\r\n" + 
				"  \"collectionOfIsbns\": [\r\n" + 
				"    {\r\n" + 
				"      \"isbn\": \"9781449331818\"\r\n" + 
				"    }\r\n" + 
				"  ]\r\n" + 
				"}";
		
		request.header("Content-Type","application/json");
		
		Response response = request.body(payload).post("/BookStore/v1/Books");
		
		String responseInString = response.asString();
		JsonPath jsonPath = new JsonPath(responseInString);
		
		System.out.println("Response Status Code for wrongUserId is "+response.getStatusCode());
		response.prettyPrint();
	    
		Assertions.assertEquals(401, response.getStatusCode());
	    Assertions.assertEquals("1207",jsonPath.getString("code"));
	    Assertions.assertEquals("User Id not correct!",jsonPath.getString("message"));
		

	     
	}
}
