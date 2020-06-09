package server.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Data
public class User {
    @Id
    private String idCardNumber;
    @NotBlank
    private String name;
    @NotNull
    private Date birthDay;
    @NotBlank
    private String permanentAddress;
    @NotNull
    private Date cardDate;
    @NotBlank
    private String cardPlace;
    @NotBlank
    @Indexed(unique = true)
    private String email;
    @NotBlank
    @Indexed(unique = true)
    private String phoneNumber;
    @DBRef
    private Set<Declaration> declarations;
}
