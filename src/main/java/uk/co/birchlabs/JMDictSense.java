package uk.co.birchlabs;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.util.List;

/**
 * Based on http://stackoverflow.com/questions/2611619/onetomany-and-composite-primary-keys
 */
@Immutable
@Entity
@Table(name="jmdict_sense")
public class JMDictSense {
    @Id
//    @GeneratedValue
    @Column(name = "data", nullable=false, updatable=false) // may not need this annotation
    private Integer data;

    private Integer id;

    // TODO: not sure what to do about the field being an Integer called id in the real table.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
//            foreignKey = @ForeignKey(name = "FK_JMDICT_SENSE_ID"), // not sure whether necessary
            name="id", insertable = false, updatable = false
    )
    private JMDictEntry jmDictEntryS;

    // Note: these mappedBy names don't actually have to be unique because it searches only within-class.
    @OneToMany(mappedBy = "jmDictSenseD", fetch = FetchType.LAZY)
    private List<JMDictDefinition> definitions;

    @OneToMany(mappedBy = "jmDictSenseT", fetch = FetchType.EAGER)
    private List<JMDictType> types;

    public JMDictSense() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<JMDictType> getTypes() {
        return types;
    }

    public void setTypes(List<JMDictType> types) {
        this.types = types;
    }

    public JMDictEntry getJmDictEntryS() {
        return jmDictEntryS;
    }

    public void setJmDictEntryS(JMDictEntry jmDictEntryS) {
        this.jmDictEntryS = jmDictEntryS;
    }

    public Integer getData() {
        return data;
    }

    public void setData(Integer data) {
        this.data = data;
    }

    public List<JMDictDefinition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<JMDictDefinition> definitions) {
        this.definitions = definitions;
    }
}
