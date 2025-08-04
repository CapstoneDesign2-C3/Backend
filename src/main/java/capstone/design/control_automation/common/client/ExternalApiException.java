package capstone.design.control_automation.common.client;

import org.springframework.http.HttpStatus;

public class ExternalApiException extends RuntimeException {

    private final HttpStatus statusCode;

    public ExternalApiException(ExternalApiErrorCode errorCode) {
        super(errorCode.getMessage());
        this.statusCode = errorCode.getHttpStatus();
    }
}
