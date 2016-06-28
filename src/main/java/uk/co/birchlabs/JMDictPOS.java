package uk.co.birchlabs;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * e.g. adv-na
 */
@Entity
@Table(name="jmdict_type")
public class JMDictPOS {

    @EmbeddedId
    private SenseDataKey senseDataKey;

    public JMDictPOS() {
    }

    public SenseDataKey getSenseDataKey() {
        return senseDataKey;
    }

    public void setSenseDataKey(SenseDataKey senseDataKey) {
        this.senseDataKey = senseDataKey;
    }
}
