import data.Pet;
import data.Status;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.io.File;

public class PetEndpoint {
    public final static String CREATE_PET = "/pet";
    public final static String GET_PET = "/pet/{petID}";
    public final static String DELETE_PET = "/pet/{petID}";
    public final static String GET_PET_STATUS = "/pet/findByStatus";
    public final static String UPDATE_PET_BY_ID = "/pet/{petID}";
    public final static String UPDATE_PET = "/pet";
    public final static String UPLOAD_PET_IMAGE = "/pet/{petID}/uploadImage";

    static {
        RestAssured.filters(new RequestLoggingFilter(LogDetail.ALL));
        RestAssured.filters(new ResponseLoggingFilter(LogDetail.ALL));
    }

    public RequestSpecification given() {
        return RestAssured
                .given()
                .baseUri("https://petstore.swagger.io/v2")
                .contentType(ContentType.JSON);
    }

    public ValidatableResponse createPet(Pet pet) {
        return given()
                .body(pet)
                .post(CREATE_PET)
                .then();
    }

    public ValidatableResponse getPet(long petId) {
        return given()
                .get(GET_PET, petId)
                .then();
    }

    public ValidatableResponse deletePet(long petId) {
        return given()
                .delete(DELETE_PET, petId)
                .then();
    }

    public ValidatableResponse getPetByStatus(Status status) {
        return given()
                .queryParam("status", status)
                .get(GET_PET_STATUS)
                .then();
    }

    public ValidatableResponse updatePet(Pet updatedPet) {
        return given()
                .body(updatedPet)
                .put(UPDATE_PET)
                .then();
    }

    public ValidatableResponse updatePetById(long petId, String petName, Status petStatus) {
        return given()
                .contentType(ContentType.URLENC)
                .formParam("name", petName)
                .formParam("status", petStatus)
                .post(UPDATE_PET_BY_ID, petId)
                .then();


    }

    public ValidatableResponse uploadPetImage(long petId, String resourcesFilePath) {
        File file = new File(getClass().getResource(resourcesFilePath).getFile());
        return given()
                .contentType("multipart/form-data")
                .multiPart(file)
                .post(UPLOAD_PET_IMAGE, petId)
                .then();
    }

}
