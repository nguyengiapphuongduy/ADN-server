package server.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserRequest {
    private String cmnd;
    private String cccd;
    private String name;
    private Date birthDay;
    private String permanentAddress;
    private Date cardDate;
    private String cardPlace;
}
