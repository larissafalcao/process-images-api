package br.com.larissafalcao.process_images_api.controller;

import br.com.larissafalcao.process_images_api.controller.dto.response.ImageResponse;
import br.com.larissafalcao.process_images_api.controller.openapi.ImageOpenApi;
import br.com.larissafalcao.process_images_api.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/images")
@AllArgsConstructor
public class ImageController implements ImageOpenApi {
    private final ImageService imageService;

    @Override
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageResponse> processImage(@RequestHeader String userLogin,
                                                      @RequestParam(required = false) Integer resizePercentage,
                                                      @RequestParam(required = false) Boolean grayscaleFilter,
                                                      @RequestPart MultipartFile arquivo) {
        return ResponseEntity.ok(imageService.sendImageRequest(userLogin, resizePercentage, grayscaleFilter, arquivo));
    }
}
