package EventManagement.Event.entity;

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

            @ManyToOne(fetch = FetchType.LAZY)
            @MapsId("eventId")
            @JoinColumn(name = "event_id", referencedColumnName = "id")
            private Event event;

            @ManyToOne(fetch = FetchType.LAZY)
            @MapsId("sponsorId")
            @JoinColumn(name = "sponsor_id", referencedColumnName = "id")
            private Sponsor sponsor;

            @Column(name = "profit_percent")
            private Double profitPercent;

}
