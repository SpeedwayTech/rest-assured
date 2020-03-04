package basics;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import files.Payload;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class PostTest {
	
	@BeforeMethod
	public static Properties getProp() throws IOException{
		Properties prop = new Properties();
		FileInputStream fs = new FileInputStream("E:\\Selenium_Training\\RestAssuredDemo\\src\\files\\env.properties");
		prop.load(fs);
		return prop;
	}

	@Test
	public void postTest() throws IOException {
		RestAssured.baseURI = getProp().getProperty("Base_URL");

		given().
		queryParam("key", getProp().getProperty("key")).
		body(Payload.getRequestBody()).	
		when().
		post(getProp().getProperty("postResource")).
		then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
		body("status",equalTo("OK"));
			
	}
}
