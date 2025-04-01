package br.com.larissafalcao.process_images_api.exception;

public class ErrorSavingImageException extends RuntimeException {
    public ErrorSavingImageException(String message) {
        super(message);
    }
}
