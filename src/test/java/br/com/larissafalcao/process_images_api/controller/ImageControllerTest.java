package br.com.larissafalcao.process_images_api.controller;

import br.com.larissafalcao.process_images_api.controller.dto.response.ImageResponse;
import br.com.larissafalcao.process_images_api.service.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ImageControllerTest {

    @Mock
    ImageService imageService;
    @InjectMocks
    ImageController imageController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void processImage_withValidData_shouldReturnOkResponse() {
        ImageResponse imageResponse = ImageResponse.builder().status("Image process successfully sent").build();
        MultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test".getBytes());

        when(imageService.sendImageRequest(any(), any(), any(), any())).thenReturn(imageResponse);

        ResponseEntity<ImageResponse> response = imageController.processImage("userLogin", 50, true, file);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Image process successfully sent", response.getBody().getStatus());
    }

    @Test
    void processImage_withNullResizePercentage_shouldReturnOkResponse() {
        ImageResponse imageResponse = ImageResponse.builder().status("Image process successfully sent").build();
        MultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test".getBytes());

        when(imageService.sendImageRequest(any(), any(), any(), any())).thenReturn(imageResponse);

        ResponseEntity<ImageResponse> response = imageController.processImage("userLogin", null, true, file);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Image process successfully sent", response.getBody().getStatus());
    }

    @Test
    void processImage_withNullGrayscaleFilter_shouldReturnOkResponse() {
        ImageResponse imageResponse = ImageResponse.builder().status("Image process successfully sent").build();
        MultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test".getBytes());

        when(imageService.sendImageRequest(any(), any(), any(), any())).thenReturn(imageResponse);

        ResponseEntity<ImageResponse> response = imageController.processImage("userLogin", 50, null, file);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Image process successfully sent", response.getBody().getStatus());
    }

    @Test
    void processImage_withNullFile_shouldThrowException() {
        when(imageService.sendImageRequest(any(), any(), any(), any())).thenThrow(new RuntimeException("File is required"));

        RuntimeException exception = org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
            imageController.processImage("userLogin", 50, true, null);
        });

        assertEquals("File is required", exception.getMessage());
    }
}
