package EventManagement.Event.DTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PaymentRequestDTO implements Serializable {
    private long amount;
    private String email;
}
