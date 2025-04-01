package br.com.larissafalcao.process_images_api.exception;

public class ErrorProcessingImageException extends RuntimeException {
    public ErrorProcessingImageException(String message) {
        super(message);
    }
}
