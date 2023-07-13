package tests.APIBodys;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthBody {
    String username;
    String password;
}
