package capstone.design.control_automation.common.exception;

import org.springframework.http.HttpStatus;

public enum ExternalApiErrorCode {
    GOOGLE_STATIC_MAP_INVALID_PARAM(HttpStatus.BAD_REQUEST, "요청에 잘못된 매개변수가 포함되거나, 필요한 매개변수가 누락되었습니다."),
    GOOGLE_STATIC_MAP_INVALID_KEY(HttpStatus.FORBIDDEN, "요청에 포함된 API 키가 잘못되었습니다."),
    EXTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "외부 API 서버에서 에러가 발생하였습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ExternalApiErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
