package server.dto;

import lombok.Data;
import server.constant.CardType;

@Data
public class CardRequest {
    private String frontImageBase64;
    private String backImageBase64;
    private CardType cardType;
}
