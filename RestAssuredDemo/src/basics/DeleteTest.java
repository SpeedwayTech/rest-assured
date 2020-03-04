package basics;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import files.Resources;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class DeleteTest {
	Properties prop;
	@BeforeMethod
	public void getProp() throws IOException{
		prop = new Properties();
		FileInputStream fs = new FileInputStream("E:\\Selenium_Training\\RestAssuredDemo\\src\\files\\env.properties");
		prop.load(fs);
	}

	@Test
	public void deleteTest() throws IOException {
		RestAssured.baseURI = prop.getProperty("Base_URL");

		Response res = given().
		queryParam("key", prop.getProperty("key")).
		body(Resources.getRequestBody()).	
		when().
		post(prop.getProperty("postResource")).
		then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
		body("status",equalTo("OK")).
		extract().response();
		
		String responseString=res.toString();
		System.out.println(responseString);
		
		JsonPath js= new JsonPath(responseString);
		String placeId = js.get("place_id");
		System.out.println("place id is "+placeId);
		
		//delete
		given().
		queryParam("key", prop.getProperty("key")).
		body("{"+
    "\"place_id\":\""+placeId+"\"}").
		when().   
		post(prop.getProperty("deleteResource")).
		then().assertThat().body("status", equalTo("OK"));
	}

}
