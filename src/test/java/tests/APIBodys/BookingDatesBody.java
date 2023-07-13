package tests.APIBodys;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingDatesBody {

    String checkin;
    String checkout;

}
