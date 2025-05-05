package capstone.design.control_automation.common.exception;

public class BadRequestException extends RuntimeException {
    ErrorCode errorCode;
    public BadRequestException(String message) {
        super(message);
    }
    public BadRequestException(ErrorCode errorcode){this.errorCode = errorcode;}
}
