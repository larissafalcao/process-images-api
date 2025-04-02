package br.com.larissafalcao.process_images_api.service;

import br.com.larissafalcao.process_images_api.persistence.domain.UserEntity;
import br.com.larissafalcao.process_images_api.persistence.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UsuarioRepository usuarioRepository;

    public UserEntity findByLogin(String login) {
        final var user = (UserEntity) usuarioRepository.findByLogin(login);
        if(user == null){
            throw new EntityNotFoundException("User not found");
        }
        return user;
    }

}
