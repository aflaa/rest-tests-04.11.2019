import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.is;

public class CreatePetTest {

    private PetEndpoint petEndpoint = new PetEndpoint();

    private static Long petId;
    private String status = "reserved";
    private String name = "Oops";
    private String body = "{\n" +
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
            "  \"status\": \"reserved\"\n" +
            "}";

    private String updatedBody = "{\n" +
            "  \"id\": 0,\n" +
            "  \"category\": {\n" +
            "    \"id\": 0,\n" +
            "    \"name\": \"string\"\n" +
            "  },\n" +
            "  \"name\": \"Oops\",\n" +
            "  \"photoUrls\": [\n" +
            "    \"string\"\n" +
            "  ],\n" +
            "  \"tags\": [\n" +
            "    {\n" +
            "      \"id\": 0,\n" +
            "      \"name\": \"string\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"status\": \"canceled\"\n" +
            "}";


    @Before
    public void prepare() {
        ValidatableResponse response = petEndpoint
                .createPet(body)
                .statusCode(anyOf(is(200), is(202)))
                .body("category.name", is("string"))
                .body("category.name", is(not("")));
        petId = response.extract().path("id");
    }

    @Test
    public void createPetTest() {
        petEndpoint
                .createPet(body)
                .statusCode(anyOf(is(200), is(202)))
                .body("category.name", is("string"))
                .body("category.name", is(not("")))
        ;
    }

    @Test
    public void getPetByID() {
        petEndpoint
                .getPet(petId)
                .statusCode(anyOf(is(200), is(202)))
                .body("category.name", is(not("")))
        ;
    }

    @Test
    public void deletePetByID() {
        petEndpoint
                .deletePet(petId)
                .statusCode(anyOf(is(200), is(202)))
        ;
        petEndpoint
                .getPet(petId)
                .statusCode(is(404))
                .body("message", is("Pet not found"))
        ;
    }

    @Test
    public void getPetByStatus() {
        petEndpoint
                .getPetStatus(status)
                .statusCode(anyOf(is(200), is(202)))
                .body("status[0]", is(status))
        ;
    }

    @Test
    public void updatePetByBody() {
        petEndpoint
                .updatePet(updatedBody)
                .statusCode(anyOf(is(200), is(202)))
                .body("category.name", is("string"))
                .body("category.name", is(not("")))
        ;
        petEndpoint
                .getPet(petId)
                .statusCode(is(200))
                .body("status", is("canceled"))
                .body("name", is(name))
        ;
    }

    @Test
    public void updatePetNameStatus() {
        petEndpoint
                .updatePetById(petId, name, status)
                .statusCode(anyOf(is(200), is(202)))
        ;
        petEndpoint
                .getPet(petId)
                .statusCode(is(200))
                .body("status", is(status))
                .body("name", is(name))
        ;
    }
}