package capstone.design.control_automation.detected_object.entity;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import capstone.design.control_automation.category.entity.Category;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
@Entity
@Table(name = "detected_object")
@NoArgsConstructor(access = PROTECTED)
@Getter
public abstract class DetectedObject {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "alias")
    private String alias;

    @Column(name = "crop_img_url", nullable = false)
    private String cropImgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    public DetectedObject(String cropImgUrl, Category category) {
        this.cropImgUrl = cropImgUrl;
        this.category = category;
    }

    public void changeAlias() {
        this.alias = alias;
    }
}
