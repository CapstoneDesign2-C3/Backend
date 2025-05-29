package capstone.design.control_automation.event.entity;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import capstone.design.control_automation.event.dto.EventResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "event")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Event {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "event_id")
    private Long id;

    @Column(name = "status")
    private String status;

    @Column(name = "keyword")
    private String keyword;

    public Event(String status, String keyword){
        this.status = status;
        this.keyword = keyword;
    }

    public EventResponse mapToResponse(){
        return new EventResponse(this.status, this.keyword);
    }
}
