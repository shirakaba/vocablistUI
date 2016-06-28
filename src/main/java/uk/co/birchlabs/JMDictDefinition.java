package uk.co.birchlabs;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.repository.query.Param;

import javax.persistence.*;
//import javax.validation.constraints.NotNull;

/**
 * eg. to do; to cause to become, to make (into), to turn (into); to serve as, to act as, to work as...
 * Based on http://stackoverflow.com/questions/2611619/onetomany-and-composite-primary-keys
 */
@Entity
@Table(name="jmdict_definition")
public class JMDictDefinition {

    @EmbeddedId
    private SenseDataKey senseDataKey;

    @ManyToOne
    // This FK's annotated name must equal the annotated name for the target partial key in SenseDataKey.
    @JoinColumn(
//            foreignKey = @ForeignKey(name = "FK_JMDICT_DEFINITION_SENSE"), // not sure whether necessary
            name="sense", insertable = false, updatable = false
    )
//    @org.hibernate.annotations.ForeignKey(name = "FK_CHILD_OBJECT_PARENTID")
    private JMDictSense jmDictSense;

    public JMDictDefinition() {
    }


    public JMDictSense getJmDictSense() {
        return jmDictSense;
    }

    public SenseDataKey getSenseDataKey() {
        return senseDataKey;
    }

    public void setSenseDataKey(SenseDataKey senseDataKey) {
        this.senseDataKey = senseDataKey;
    }

    public void setJmDictSense(JMDictSense jmDictSense) {
        this.jmDictSense = jmDictSense;
    }
}
