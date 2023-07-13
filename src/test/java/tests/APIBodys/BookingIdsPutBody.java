package tests.APIBodys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingIdsPutBody {
        String firstname;
        String lastname;
        int totalprice;
        Boolean depositpaid;
        String additionalneeds;
}
