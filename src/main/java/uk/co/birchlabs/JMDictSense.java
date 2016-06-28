package uk.co.birchlabs;

import javax.persistence.*;
import java.util.List;

/**
 * Based on http://stackoverflow.com/questions/2611619/onetomany-and-composite-primary-keys
 */
@Entity
@Table(name="jmdict_sense")
public class JMDictSense {
    @Id
//    @GeneratedValue
    @Column(name = "data", nullable=false, updatable=false)
    private Integer data;

    // TODO: not sure what to do about the field being an Integer called id in the real table.
    @ManyToOne
    @JoinColumn(
//            foreignKey = @ForeignKey(name = "FK_JMDICT_SENSE_ID"), // not sure whether necessary
            name="id", insertable = false, updatable = false
    )
    private JMDictEntry jmDictEntryS;

    @OneToMany(mappedBy = "jmDictSense", fetch = FetchType.EAGER)
//    @IndexColumn(name = "pos", base=0)
    private List<JMDictDefinition> definitions;

    public JMDictSense() {
    }


    public Integer getData() {
        return data;
    }

    public void setData(Integer data) {
        this.data = data;
    }

//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }

    public List<JMDictDefinition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<JMDictDefinition> definitions) {
        this.definitions = definitions;
    }
}
