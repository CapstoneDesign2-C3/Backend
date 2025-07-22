package capstone.design.control_automation.mapper.detectedObject;

import static org.assertj.core.api.Assertions.assertThat;

import capstone.design.control_automation.common.config.MyBatisConfig;
import capstone.design.control_automation.detected_object.repository.dto.DetectedObjectQueryResult.MobileObject;
import capstone.design.control_automation.mapper.event.EventMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@MybatisTest
@Import(MyBatisConfig.class)
@ActiveProfiles("test")
class DetectedObjectMapperTest {

    @Autowired
    DetectedObjectMapper detectedObjectMapper;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void findMobileObjectCountByFilterAndIds() {
        Long expected = 20L;
        Long actual = detectedObjectMapper.findMobileObjectCountByFilterAndIds("사람", null);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findMobileObjectsByFilterAndIds() {
        List<MobileObject> expected = List.of(
            new MobileObject(1L, "uuid1", "사람", "/crops/object1.jpg", "Object1"),
            new MobileObject(2L, "uuid2", "사람", "/crops/object2.jpg", "Object2"),
            new MobileObject(3L, "uuid3", "사람", "/crops/object3.jpg", "Object3"),
            new MobileObject(4L, "uuid4", "사람", "/crops/object4.jpg", "Object4"),
            new MobileObject(5L, "uuid5", "사람", "/crops/object5.jpg", "Object5")
        );
        List<MobileObject> actual = detectedObjectMapper.findMobileObjectsByFilterAndIds("사람", null,5, 0L);

        assertThat(actual).hasSameElementsAs(expected);
    }

    @Test
    @DisplayName("mobile object 에 별칭 붙이기 test")
    @Transactional
    void aliasMobileObjectTest() {
        //given
        Long detectedObjectId = 1L;
        String expected = "new-alias";
        String before = jdbcTemplate.queryForObject(
            "SELECT alias FROM detected_object WHERE id = ?",
            String.class,
            detectedObjectId
        );
        //when
        detectedObjectMapper.aliasDetectedObject(detectedObjectId, expected);
        //then
        String after = jdbcTemplate.queryForObject(
            "SELECT alias FROM detected_object WHERE id = ?",
            String.class,
            detectedObjectId
        );

        assertThat(before).isNotEqualTo(after);
        assertThat(after).isEqualTo(expected);
    }
}
