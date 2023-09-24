import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class RestAssuredApiTest {
	@Test
	public void TestApi() throws JsonMappingException, JsonProcessingException {
		Response rs = RestAssured.given().contentType(ContentType.JSON)
				.baseUri("https://api.coindesk.com/v1/bpi/currentprice.json").when().get().then().extract().response();
		JSONObject jsonObject = new JSONObject(rs.jsonPath().getJsonObject("bpi"));

		ObjectMapper objectMapper = new ObjectMapper();
		Map parsedJsonObject = (Map) objectMapper.readValue(jsonObject.toJSONString(), new TypeReference<>() {
		});
		// Get all keys
		// Set allKeys = parsedJsonObject.keySet();

		Iterator<Map.Entry<Object, Object>> itr = parsedJsonObject.entrySet().iterator();

		while (itr.hasNext()) {
			Entry<Object, Object> entry = itr.next();
			if (entry.getKey().equals("GBP")) {
				/*
				 * System.out.println("Key = " + entry.getKey() + ", Value = " +
				 * entry.getValue());
				 */
				Map<String, String> s = (Map<String, String>) entry.getValue();
				for (Map.Entry<String, String> entry1 : s.entrySet())
					if (entry1.getKey().equals("rate")) {
						System.out.println("Key = " + entry1.getKey() + ", Value = " + entry1.getValue());
					}

			}
		}
	}
}
