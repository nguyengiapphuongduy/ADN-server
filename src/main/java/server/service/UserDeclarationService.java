package server.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.document.Declaration;
import server.document.User;
import server.dto.UserDeclarationRequest;
import server.dto.UserDeclarationResponse;
import server.repository.DeclarationRepository;
import server.repository.UserRepository;
import server.util.NullableFieldBeanUtilsBean;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Optional;

@Service
@Transactional
public class UserDeclarationService {

    @Resource
    private UserRepository userRepository;
    @Resource
    private DeclarationRepository declarationRepository;

    public UserDeclarationResponse saveOrUpdate(UserDeclarationRequest requestBody) {
        User user = new User();
        Declaration declaration = new Declaration();
        NullableFieldBeanUtilsBean.getInstance().copyProperties(user, requestBody.getUserRequest());
        NullableFieldBeanUtilsBean.getInstance().copyProperties(declaration, requestBody.getDeclarationRequest());

        User target;
        Optional<User> targetOptional = userRepository.findByCmndOrCccd(user.getCmnd(), user.getCccd());
        if (targetOptional.isPresent()) {
            target = targetOptional.get();
            NullableFieldBeanUtilsBean.getInstance().copyProperties(target, user);
        } else {
            target = user;
        }

        Declaration savedDeclaration = declarationRepository.save(declaration);
        if (target.getDeclarations() == null) {
            target.setDeclarations(new HashSet<>());
        }
        target.getDeclarations().add(savedDeclaration);
        User savedUser = userRepository.save(target);

        UserDeclarationResponse response = new UserDeclarationResponse();
        NullableFieldBeanUtilsBean.getInstance().copyProperties(response, savedUser);
        NullableFieldBeanUtilsBean.getInstance().copyProperties(response, savedDeclaration);
        return response;
    }
}