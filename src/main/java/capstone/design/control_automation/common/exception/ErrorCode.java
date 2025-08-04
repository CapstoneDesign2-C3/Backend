package capstone.design.control_automation.common.exception;

import java.util.Optional;
import java.util.function.Predicate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    //OK
    OK(HttpStatus.OK, "성공했습니다."),

    //Video
    VIDEO_NOT_FOUND(HttpStatus.NOT_FOUND, "비디오를 찾을 수 없습니다."),

    //Camera
    CAMERA_NOT_FOUND(HttpStatus.NOT_FOUND, "카메라를 찾을 수 없습니다."),

    //Category
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "카테고리를 찾을 수 없습니다."),
    DETECTED_OBJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "탐지 객체를 찾을 수 없습니다."),

    //Qdrant
    SEARCH_FAILED(HttpStatus.CONFLICT, "검색에 실패하였습니다.");
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
