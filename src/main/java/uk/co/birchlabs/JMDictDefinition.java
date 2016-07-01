package uk.co.birchlabs;

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

    @ManyToOne(fetch = FetchType.LAZY)
    // This FK's annotated name must equal the annotated name for the target partial key in SenseDataKey.
    // Another JoinColumn example at http://www.thejavageek.com/2014/09/23/jpa-joincolumns-annotation-example/
    @JoinColumn(
//            foreignKey = @ForeignKey(name = "FK_JMDICT_DEFINITION_SENSE"), // not sure whether necessary
            name="sense", insertable = false, updatable = false
    )
//    @org.hibernate.annotations.ForeignKey(name = "FK_CHILD_OBJECT_PARENTID")
    private JMDictSense jmDictSenseD;

    public JMDictDefinition() {
    }


    public JMDictSense getJmDictSenseD() {
        return jmDictSenseD;
    }

    public SenseDataKey getSenseDataKey() {
        return senseDataKey;
    }

    public void setSenseDataKey(SenseDataKey senseDataKey) {
        this.senseDataKey = senseDataKey;
    }

    public void setJmDictSenseD(JMDictSense jmDictSenseD) {
        this.jmDictSenseD = jmDictSenseD;
    }
}
