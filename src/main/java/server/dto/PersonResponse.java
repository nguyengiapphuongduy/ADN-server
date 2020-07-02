package server.dto;

import lombok.Data;
import server.constant.CardType;

import java.util.Date;

@Data
public class PersonResponse {
    private String name;
    private String idCardNumber;
    private Date birthDay;
    private String permanentAddress;
    private Date cardDate;
    private String cardPlace;
    private CardType cardType;
    private String phoneNumber;
    private String email;
}
