package server.document;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

@Data
public class User {
    @Id
    private String id;
    private String name;
    private String cmnd;
    private String cccd;
    private Date birthDay;
    private String permanentAddress;
    private Date cardDate;
    private String cardPlace;
    private List<Declaration> declarations;
}
