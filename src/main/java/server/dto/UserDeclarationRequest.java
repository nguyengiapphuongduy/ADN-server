package server.dto;

import lombok.Data;

@Data
public class UserDeclarationRequest {
    private UserRequest userRequest;
    private DeclarationRequest declarationRequest;
}
