package uk.co.birchlabs;

import javax.persistence.*;

/**
 * Created by jamiebirch on 29/06/2016.
 */
@Entity
@Table(name="jmdict_type")
public class JMDictType {

    @EmbeddedId
    private SenseDataKey senseDataKey;

    @ManyToOne
    @JoinColumn(name="sense", insertable = false, updatable = false)
    private JMDictSense jmDictSenseT;

    public JMDictType() {
    }

    public SenseDataKey getSenseDataKey() {
        return senseDataKey;
    }

    public void setSenseDataKey(SenseDataKey senseDataKey) {
        this.senseDataKey = senseDataKey;
    }

    public JMDictSense getJmDictSenseT() {
        return jmDictSenseT;
    }

    public void setJmDictSenseT(JMDictSense jmDictSenseT) {
        this.jmDictSenseT = jmDictSenseT;
    }
}
