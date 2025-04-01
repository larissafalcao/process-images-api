package br.com.larissafalcao.process_images_api.service;

import br.com.larissafalcao.process_images_api.persistence.domain.UserEntity;
import br.com.larissafalcao.process_images_api.persistence.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UsuarioRepository usuarioRepository;

    public UserEntity findByLogin(String login) {
        return (UserEntity) usuarioRepository.findByLogin(login);
    }

}
