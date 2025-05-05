package capstone.design.control_automation.common.exception;

public class NotFoundException extends RuntimeException {
    ErrorCode errorCode;
    public NotFoundException(String message) {
        super(message);
    }
    public NotFoundException(ErrorCode errorcode){this.errorCode = errorcode;}
}
