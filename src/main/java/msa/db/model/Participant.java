package msa.db.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Objects;

/*
import de.dhbw.App;
import de.dhbw.LogMessage;
import de.dhbw.factory.AlgorithmFactory;
 */

@Entity
@Table(name = "participants")
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull
    private Integer id;

    @Column(name = "name", unique = true)
    @NotNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;

    public Participant(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    private Participant() {
    }

    public Integer getId() {
        return id;
    }

    private void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    private void setType(Type type) {
        this.type = type;
    }

    //TODO fix this
    /*
    public static Participant byName(App app, String name) {
        var cq = app.getSession().createQuery("FROM Participant WHERE name = :name");
        return (Participant) cq.setParameter("name", name).getSingleResult();
    }
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return id.equals(that.id) &&
                name.equals(that.name) &&
                type.equals(that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type);
    }
}