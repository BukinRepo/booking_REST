package tests.APIBodys;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class BookingBody {
        String firstname;
        String lastname;
        int totalprice;
        Boolean depositpaid;
        BookingDatesBody bookingdates;
        String additionalneeds;


}
