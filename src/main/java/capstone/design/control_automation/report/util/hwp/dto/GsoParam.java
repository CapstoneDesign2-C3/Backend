package capstone.design.control_automation.report.util.hwp.dto;

import lombok.Getter;

public record GsoParam(
    double posX,
    double posY,
    int width,
    Integer height,
    int bottomMargin
) {

    private static final Integer DEFAULT_BOTTOM_MARGIN = 5;

    public GsoParam(double posX, double posY, int width, Integer height) {
        this(posX, posY, width, height, DEFAULT_BOTTOM_MARGIN);
    }

    @Getter
    public enum PaperSize {
        MAX_WIDTH(150), HALF_WIDTH(75);
        private final int value;

        PaperSize(int value) {
            this.value = value;
        }
    }

}
