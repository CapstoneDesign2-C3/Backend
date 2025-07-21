package capstone.design.control_automation.report.util.hwp;

import lombok.Getter;

public record GsoParam(
    int posX,
    int posY,
    int width,
    int height
) {

    @Getter
    enum PaperSize{
        MAX_WIDTH(150);
        private final int value;
        PaperSize(int value) {
            this.value = value;
        }
    }

}
