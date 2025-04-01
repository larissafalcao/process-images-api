package br.com.larissafalcao.process_images_api.controller;

import br.com.larissafalcao.process_images_api.controller.dto.request.ImageRequest;
import br.com.larissafalcao.process_images_api.controller.dto.response.ImageResponse;
import br.com.larissafalcao.process_images_api.controller.openapi.ImageOpenApi;
import br.com.larissafalcao.process_images_api.service.ImageService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/images")
@AllArgsConstructor
public class ImageController implements ImageOpenApi {
    private final ImageService imageService;

    @Override
    @PostMapping()
    public ResponseEntity<ImageResponse> processImage(@RequestBody @Valid ImageRequest imageProcessRequest) {
        return ResponseEntity.ok(imageService.sendImageRequest(imageProcessRequest));
    }
}
