package br.com.larissafalcao.process_images_api.controller.openapi;

import br.com.larissafalcao.process_images_api.controller.dto.request.ImageRequest;
import br.com.larissafalcao.process_images_api.controller.dto.response.ImageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

public interface ImageOpenApi {

    @Operation(summary = "Process an image", description = "Process an image.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image process successfully sent"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "400", description = "Invalid data provided"),
    })
    ResponseEntity<ImageResponse> processImage(ImageRequest imageRequest);
}
