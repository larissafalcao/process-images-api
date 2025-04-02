package br.com.larissafalcao.process_images_api.service;

import br.com.larissafalcao.process_images_api.persistence.domain.UserEntity;
import br.com.larissafalcao.process_images_api.persistence.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserEntity findByLogin(String login) {
        final var user = (UserEntity) userRepository.findByLogin(login);
        if(user == null){
            throw new EntityNotFoundException("User not found");
        }
        return user;
    }

}
