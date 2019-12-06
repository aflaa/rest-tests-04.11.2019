package tests;

import data.Category;
import data.Pet;
import data.Status;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import steps.PetEndpoint;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.Matchers.*;

@RunWith(SerenityRunner.class)
public class PetTest {
    // private steps.PetEndpoint petEndpoint = new steps.PetEndpoint();
    @Steps
    private PetEndpoint petEndpoint;
    private static Long petId;
    private String name = "Mey";
    private String categoryName = "string";

    //  private Pet pet = new Pet(0, categoryName, name, Status.pending);

    //private Pet pet = Pet.builder().build();

    private Pet pet = Pet.builder()
            .id(0)
            .category(Category.builder().name("string").build())
            .name("Mey")
            .status(Status.pending)
            .build();

    @Before
    public void prepare() {
        ValidatableResponse response = petEndpoint
                .createPet(pet)
                .statusCode(anyOf(is(200), is(202)))
                .body("category.name", is(categoryName));
        petId = response.extract().path("id");
    }

    @Test
    public void createPetTest() {
        petEndpoint
                .createPet(pet)
                .statusCode(anyOf(is(200), is(202)))
                .body("category.name", is(categoryName))
        ;
    }

    @Test
    public void getPetByID() {
        petEndpoint
                .getPet(petId)
                .statusCode(anyOf(is(200), is(202)))
                .body("category.name", is(categoryName));
    }

    @Test
    public void deletePetByID() {
        petEndpoint
                .deletePet(petId)
                .statusCode(anyOf(is(200), is(202)));
        petEndpoint
                .getPet(petId)
                .statusCode(is(404))
                .body("message", is("Pet not found"));
    }

    @Test
    public void getPetByStatus() {
        petEndpoint
                .getPetByStatus(Status.pending)
                .statusCode(200)
                .body("status[]", everyItem(is(Status.pending.toString())));
    }

    @Test
    public void updatePetByBody() {
        Pet updatedPet = Pet.builder()
                .id(petId)
                .category(Category.builder().name("pets").build())
                .name("Oops")
                .status(Status.sold)
                .build();

        petEndpoint
                .updatePet(updatedPet)
                .statusCode(anyOf(is(200), is(202)))
                .body("category.name", is("pets"))
                .body("status", is(Status.sold.toString()))
                .body("name", is("Oops"));
    }

    @Test
    public void updatePetForm() {
        petEndpoint
                .updatePetById(petId, name, Status.sold)
                .statusCode(anyOf(is(200), is(202)));
        petEndpoint
                .getPet(petId)
                .statusCode(is(200))
                .body("status", is(Status.sold.toString()))
                .body("name", is(name));
    }

    @Test
    public void uploadPetImage() {
        String imagePath = "file:///test/resources/cat.jpg";
        petEndpoint
                .uploadPetImage(petId, imagePath)
                .statusCode(200)
                .body("message", containsString("uploaded to " + imagePath));
    }
}