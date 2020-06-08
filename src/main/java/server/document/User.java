package server.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Date;
import java.util.Set;

@Data
public class User {
    @Id
    private String id;
    private String name;
    @Indexed(unique = true)
    private String cmnd;
    @Indexed(unique = true)
    private String cccd;
    private Date birthDay;
    private String permanentAddress;
    private Date cardDate;
    private String cardPlace;
    private String email;
    private String phoneNumber;
    @DBRef
    private Set<Declaration> declarations;
}
