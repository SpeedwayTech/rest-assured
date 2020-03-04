package basics;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;



public class GetTest {

	@Test
	public void getTest() {
		
		RestAssured.baseURI="https://maps.googleapis.com";		
		given().
			param("location", "-33.8670522,151.1957362").
			param("radius", "1500").
			param("key", "AIzaSyB6EI5EQVzG0fQ1O8jaKKq-0b_GfHQMXb8").
		when().
			get("/maps/api/place/nearbysearch/json").
		then().
			assertThat().statusCode(200).and().body("results[0].name",equalTo("Sydney"));
	}
}
