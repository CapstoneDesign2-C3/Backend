package capstone.design.control_automation.category.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false, length = 20)
    private String engName;

    @Column(nullable = false)
    private Boolean isMobile;

    public Category(String name, String engName, Boolean isMobile) {
        this.name = name;
        this.engName = engName;
        this.isMobile = isMobile;
    }

    public void updateInfo(String name, String engName, Boolean isMobile) {
        this.name = name;
        this.engName = engName;
        this.isMobile = isMobile;
    }
}
