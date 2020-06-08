package server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.dto.UserDeclarationRequest;
import server.dto.UserDeclarationResponse;
import server.service.UserDeclarationService;

import javax.annotation.Resource;

@RestController
@RequestMapping("user-declarations")
public class UserDeclarationController {

    @Resource
    private UserDeclarationService userDeclarationService;

    @PostMapping
    public ResponseEntity<UserDeclarationResponse> saveOrUpdate(@RequestBody UserDeclarationRequest requestBody) {
        UserDeclarationResponse responseBody = userDeclarationService.saveOrUpdate(requestBody);
        return ResponseEntity.ok(responseBody);
    }
}
