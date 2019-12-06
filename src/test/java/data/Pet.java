package data;

//import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;
import lombok.Data;// need to be installed lombok plugin in addition

import java.util.ArrayList;
import java.util.List;
//this annotation is translate object to json
// @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Builder
@Data
public class Pet {

    @Builder.Default
    private long id = 0;
    @Builder.Default
    private Category category = Category.builder().build();
    @Builder.Default
    private String name = "Mey";
    @Builder.Default
    private List<String> photoUrls  = new ArrayList<>();
    @Builder.Default
    private List<Tag> tags = new ArrayList<>();
    @Builder.Default
    private Status status = Status.pending;

}
