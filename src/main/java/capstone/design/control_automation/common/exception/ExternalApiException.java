package capstone.design.control_automation.common.exception;

public class ExternalApiException extends RuntimeException {

    private final ExternalApiErrorCode errorCode;

    public ExternalApiException(ExternalApiErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ExternalApiErrorCode getErrorCode() {
        return errorCode;
    }
}
