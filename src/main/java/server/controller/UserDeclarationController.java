package server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.dto.UserDeclarationRequest;
import server.dto.UserDeclarationResponse;
import server.service.UserDeclarationService;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("user-declarations")
public class UserDeclarationController {

    @Resource
    private UserDeclarationService userDeclarationService;

    @PostMapping
    public ResponseEntity<Object> saveOrUpdate(@RequestBody UserDeclarationRequest requestBody) {
        try {
            UserDeclarationResponse responseBody = userDeclarationService.saveOrUpdate(requestBody);
            return ResponseEntity.ok().body(responseBody);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.badRequest().body(new HashMap.SimpleEntry<>("message", e.getMessage()));
        }
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<UserDeclarationResponse> detail(@PathVariable String id) {
        UserDeclarationResponse response = userDeclarationService.detail(id);
        Optional<UserDeclarationResponse> responseOptional = Optional.ofNullable(response);
        return ResponseEntity.of(responseOptional);
    }
}
