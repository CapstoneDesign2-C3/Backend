package capstone.design.control_automation.common.exception;

public class ConflictException extends RuntimeException {
    ErrorCode errorCode;
    public ConflictException(String message) {
        super(message);
    }
    public ConflictException(ErrorCode errorcode){this.errorCode = errorcode;}
}
