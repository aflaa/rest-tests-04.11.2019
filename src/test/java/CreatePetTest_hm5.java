import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.is;

public class CreatePetTest_hm5 {

    long idPet = 0;
    String path = "";

    @Before
    @Test
    public void createPet() {

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
        idPet = RestAssured
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
                // .log().all()
                .extract().path("id")

        ;
        System.out.println("Generated data.Pet Id is " + idPet);

        String idString = Long.toString(idPet);
        String pathPetId = "https://petstore.swagger.io/v2/pet/" + (idString);
        path = pathPetId;
    }

    @Test
    public void getPetByID() {
        String getPetPath = path;
        System.out.println("Looking for data.Pet by Path " + getPetPath);

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .get(getPetPath)
                // .get("https://petstore.swagger.io/v2/pet/9216678377732766644")
                .then()
                .statusCode(anyOf(is(200), is(202)))
                .body("category.name", is("string"))
                .body("name", is("Mey"))
                .log().all()

        ;
    }

    @Test
    public void deletePetByID() {
        String deletePetPath = path;
        System.out.println("Looking for data.Pet by Path " + deletePetPath);

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .delete(deletePetPath)
                // .get("https://petstore.swagger.io/v2/pet/9216678377732766644")
                .then()
                .statusCode(anyOf(is(200), is(202)))
                .log().all()
        ;

    }
}
