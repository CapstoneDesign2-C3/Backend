package capstone.design.control_automation.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.function.Predicate;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    //OK
    OK(HttpStatus.OK, "성공했습니다."),

    //Video
    VIDEO_NOT_FOUND(HttpStatus.NOT_FOUND, "비디오를 찾을 수 없습니다."),
    
    //Camera
    CAMERA_NOT_FOUND(HttpStatus.NOT_FOUND, "카메라를 찾을 수 없습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;

    public String getMessage(Throwable throwable) {
        return this.getMessage(this.getMessage(this.getMessage() + " - " + throwable.getMessage()));
    }

    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(this.getMessage());
    }
}
