import data.Pet;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.is;

public class PetTest {
    private PetEndpoint petEndpoint = new PetEndpoint();

    private Pet pet = new Pet(0,"string", "Mey","reserved" );
    private static Long petId;
    private String status = "reserved";
    private String name = "Oops";
//    private String body = "{\n" +
//            "  \"id\": 0,\n" +
//            "  \"category\": {\n" +
//            "    \"id\": 0,\n" +
//            "    \"name\": \"string\"\n" +
//            "  },\n" +
//            "  \"name\": \"Mey\",\n" +
//            "  \"photoUrls\": [\n" +
//            "    \"string\"\n" +
//            "  ],\n" +
//            "  \"tags\": [\n" +
//            "    {\n" +
//            "      \"id\": 0,\n" +
//            "      \"name\": \"string\"\n" +
//            "    }\n" +
//            "  ],\n" +
//            "  \"status\": \"reserved\"\n" +
//            "}";


    @Before
    public void prepare() {
        ValidatableResponse response = petEndpoint
                .createPet(pet)
                .statusCode(anyOf(is(200), is(202)))
                .body("category.name", is("string"))
                .body("category.name", is(not("")));
        petId = response.extract().path("id");
    }

    @Test
    public void createPetTest() {
        petEndpoint
                .createPet(pet)
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
                .body("category.name", is(not("")));
    }

    @Test
    public void deletePetByID() {
        petEndpoint
                .deletePet(petId)
                .statusCode(anyOf(is(200), is(202)));
        petEndpoint
                .getPet(petId)
                .statusCode(is(404))
                .body("message", is("data.Pet not found"));
    }

    @Test
    public void getPetByStatus() {
        petEndpoint
                .getPetByStatus(status)
                .statusCode(200)
                .body("status[0]", is(status));//ToDo: verify each element status
    }

    @Test
    public void updatePetByBody() {
        String updatedBody = "{\n" +
                "  \"id\": " + petId + ",\n" +
                "  \"category\": {\n" +
                "    \"id\": 0,\n" +
                "    \"name\": \"pets\"\n" +
                "  },\n" +
                "  \"name\": \"" + name + "\",\n" +
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
        petEndpoint
                .updatePet(updatedBody)
                .statusCode(anyOf(is(200), is(202)))
                .body("category.name", is("pets"))
                .body("status", is("canceled"))
                .body("name", is(name));
    }

    @Test
    public void updatePetNameStatus() {
        petEndpoint
                .updatePetById(petId, name, status)
                .statusCode(anyOf(is(200), is(202)));
        petEndpoint
                .getPet(petId)
                .statusCode(is(200))
                .body("status", is(status))
                .body("name", is(name));
    }
}