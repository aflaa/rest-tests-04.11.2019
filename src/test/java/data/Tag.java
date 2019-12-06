package data;

//import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Builder;
import lombok.Data;

//@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Data
@Builder
public class Tag {
    @Builder.Default
    private long id = 0;
    @Builder.Default
    private String name ="animals";

}
