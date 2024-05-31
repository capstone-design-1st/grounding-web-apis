package org.example.first.groundingwebapis.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import javax.naming.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyPiecedException.class)
    public ResponseEntity<Map<String, Object>> handleAlreadyInvestedException(AlreadyPiecedException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(messages(e.getMessage()));
    }

    @ExceptionHandler(CannotDeleteException.class)
    public ResponseEntity<Map<String, Object>> handleCannotDeleteException(CannotDeleteException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(messages(e.getMessage()));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Map<String, Object>> handleFileLimitException(MaxUploadSizeExceededException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(messages("이 파일은 제한된 용량을 초과하였기 때문에 업로드할 수 없습니다."));
    }

    private Map<String, Object> messages(String message){
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now());
        errorBody.put("status", HttpStatus.BAD_REQUEST.value());
        errorBody.put("error", "Bad Request");
        errorBody.put("message", message);
        return errorBody;
    }
}