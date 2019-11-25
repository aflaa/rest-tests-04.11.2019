import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.is;

public class CreatePetTest {

    static Long petId;
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

    public RequestSpecification given() {
        return RestAssured
                .given()
                .baseUri("https://petstore.swagger.io/v2")
                .log().all()
                .contentType(ContentType.JSON)
                ;
    }

    @Before
    public void prepare() {
        petId = given()
                .body(body)
                .post(PetEndpoint.CREATE_PET)
                .then()
        .extract().path("id");
        System.out.println(petId);
    }

    @Test
    public void CreatePet() {

        ValidatableResponse response = given()
                .body(body)
                .post(PetEndpoint.CREATE_PET)
                .then()
                .statusCode(anyOf(is(200), is(202)))
                .body("category.name", is("string"))
                .body("category.name", is(not("")))
                .log().all();
    }

    @Test
    public void GetPetByID() {
        given()
                .get(PetEndpoint.GET_PET, petId)
                .then()
                .statusCode(anyOf(is(200), is(202)))
                .body("category.name", is(not("")))
                .log().all()
        ;
    }

    @Test
    public void DeletePetByID() {
        given()
                .delete(PetEndpoint.DELETE_PET, petId)
                .then()
                .statusCode(anyOf(is(200), is(202)))
                .log().all()
        ;
        given()
                .get(PetEndpoint.GET_PET, petId)
                .then()
                .statusCode(is(404))
                .body("message", is ("Pet not found"))
                .log().all()
        ;
    }
}
