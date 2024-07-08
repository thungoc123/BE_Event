package EventManagement.Event.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
    @Entity(name = "sponsor_event")
        @IdClass(SponsorEventId.class)
        public class SponsorEvent {
            @Id
            @Column(name = "event_id")
            private int eventId;

            @Id
            @Column(name = "sponsor_id")
            private Long sponsorId;

            @ManyToOne(fetch = FetchType.EAGER)
            @MapsId("eventId")
            @JsonBackReference
            @JoinColumn(name = "event_id", referencedColumnName = "id")
            private Event event;

            @ManyToOne(fetch = FetchType.EAGER)
            @MapsId("sponsorId")
            @JsonIgnoreProperties("sponsorEvent")
            @JoinColumn(name = "sponsor_id", referencedColumnName = "id")
            private Sponsor sponsor;

            @Column(name = "profit_percent")
            private Double profitPercent;

}
