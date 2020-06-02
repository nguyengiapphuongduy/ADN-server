package server.document;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Declaration {
    @Id
    private String id;
    private String answer1;
    private String answer2;
}
