import data.Pet;
import data.Status;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.Matchers.*;

@RunWith(SerenityRunner.class)
public class PetTest {
    // private PetEndpoint petEndpoint = new PetEndpoint();
    @Steps
    private PetEndpoint petEndpoint;
    private static Long petId;
    private String name = "Mey";
    private String categoryName = "string";

    private Pet pet = new Pet(0, categoryName, name, Status.pending);

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
        Pet updatedPet = new Pet(petId, "pets", "Oops", Status.sold);

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
        String imagePath = "./cat.jpg";
        petEndpoint
                .uploadPetImage(petId, imagePath)
                .statusCode(200)
                .body("message", containsString("uploaded to " + imagePath));
    }
}