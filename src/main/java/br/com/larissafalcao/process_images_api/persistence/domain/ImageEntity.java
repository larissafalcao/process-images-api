package br.com.larissafalcao.process_images_api.persistence.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_image")
public class ImageEntity {

    @Id
    @Column(name = "image_id")
    private Long imageId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "request_date_time")
    private LocalDateTime requestDateTime;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "original_image_path")
    private String originalImagePath;

    @Column(name = "processed_image_path")
    private String processedImagePath;

    @Column(name = "resize_percentage")
    private Integer resizePercentage;

    @Column(name = "grayscale_filter")
    private Boolean grayscaleFilter;

    @Column(name = "status")
    private String status;

}
