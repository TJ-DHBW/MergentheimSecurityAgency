package msa.db.model;

import com.sun.istack.NotNull;

import javax.persistence.*;

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
}