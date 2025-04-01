package br.com.larissafalcao.process_images_api.persistence.repository;

import br.com.larissafalcao.process_images_api.persistence.domain.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
    int countByUserIdAndRequestDateTimeBetween(Long userId, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
