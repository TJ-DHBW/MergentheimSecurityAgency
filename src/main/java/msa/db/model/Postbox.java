package msa.db.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "postbox")
public class Postbox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "participant_to_id")
    private Participant participantTo;

    @ManyToOne
    @JoinColumn(name = "participant_from_id", unique = true)
    private Participant participantFrom;

    @Column(name = "message")
    @NotNull
    private String message;

    @Column(name = "timestamp")
    private Integer timestamp;

    protected Postbox() {
    }

    public Postbox(Participant participantTo, Participant participantFrom, String message) {
        this.participantTo = participantTo;
        this.participantFrom = participantFrom;
        this.message = message;
        this.timestamp = Math.toIntExact(Instant.now().getEpochSecond());
    }

    public Postbox(Integer id, Participant participantTo, Participant participantFrom, String message) {
        this.id = id;
        this.participantTo = participantTo;
        this.participantFrom = participantFrom;
        this.message = message;
        this.timestamp = Math.toIntExact(Instant.now().getEpochSecond());
    }
}