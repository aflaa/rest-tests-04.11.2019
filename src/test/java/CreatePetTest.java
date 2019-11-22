import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.is;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreatePetTest {

   static long petId;

    @Test
    public void test1CreatePet() {

        String body = "{\n" +
                "  \"id\": 0,\n" +
                "  \"category\": {\n" +
                "    \"id\": 0,\n" +
                "    \"name\": \"string\"\n" +
                "  },\n" +
                "  \"name\": \"Mey\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 0,\n" +
                "      \"name\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"available\"\n" +
                "}";
        ValidatableResponse response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                // .contentType("application/json")
                // .header("ContentType", "json")
                .body(body)
                .post("https://petstore.swagger.io/v2/pet")
                .then()
                .statusCode(anyOf(is(200), is(202)))
                .body("category.name", is("string"))
                .body("category.name", is(not("")))
                .log().all();
        //response.extract().body().jsonPath().getString("id");
        petId = response.extract().path("id");

    }

    @Test
    public void test2GetPetByID() {
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .get("https://petstore.swagger.io/v2/pet/" + petId)
                .then()
                .statusCode(anyOf(is(200), is(202)))
                .body("category.name", is(not("")))
                .log().all()
        ;
    }

    @Test
    public void test3DeletePetByID() {
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .delete("https://petstore.swagger.io/v2/pet/" + petId)
                .then()
                .statusCode(anyOf(is(200), is(202)))
                .log().all()
        ;
    }
}
