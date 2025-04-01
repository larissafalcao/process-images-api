package br.com.larissafalcao.process_images_api.persistence.repository;

import br.com.larissafalcao.process_images_api.persistence.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UserEntity, Long> {
    UserDetails findByLogin(String login);
}
