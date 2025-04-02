package br.com.larissafalcao.process_images_api.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageRequest {

    @NotBlank
    private String userLogin;

    private Integer resizePercentage;

    private Boolean grayscaleFilter;

    @NotNull
    private MultipartFile file;

}
