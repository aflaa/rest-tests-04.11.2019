import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestLogSpecification;
import io.restassured.specification.RequestSpecification;

import java.util.ArrayList;
import java.util.List;

public class PetEndpoint {
    public final static String CREATE_PET = "/pet";
    public final static String GET_PET = "/pet/{petID}";
    public final static String DELETE_PET = "/pet/{petID}";
    public final static String GET_PET_STATUS = "/pet/findByStatus?status={status}";
    public final static String UPDATE_PET = "/pet/{petID}";

    static {
        RestAssured.filters(new RequestLoggingFilter(LogDetail.ALL));
        RestAssured.filters(new ResponseLoggingFilter(LogDetail.ALL));
    }

    public RequestSpecification given() {
        return RestAssured
                .given()
                .baseUri("https://petstore.swagger.io/v2")
                .contentType(ContentType.JSON)

                ;

    }
//    public RequestSpecification givenUrlenc() {
//        return RestAssured
//                .given()
//                .baseUri("https://petstore.swagger.io/v2")
//                .contentType(ContentType.URLENC)
//                ;
//    }

    public ValidatableResponse createPet(String body) {
        return given()
                .body(body)
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

    public ValidatableResponse getPetStatus(String status) {
//        List<String[]> list = new ArrayList<String[]>();
//            String firstBody = list.get(1)[0];
        return given()
                .get(GET_PET_STATUS, status)
                .then()
                ;
    }

    public ValidatableResponse updatePet(String body) {
        return given()
                .body(body)
                .put(CREATE_PET)
                .then();
    }

    public ValidatableResponse updatePetById(long petId, String name, String status) {
        return given()
                .contentType(ContentType.URLENC)
                .formParam("name", name)
                .formParam("status", status)
                .post(UPDATE_PET, petId)
                .then();


    }
}
