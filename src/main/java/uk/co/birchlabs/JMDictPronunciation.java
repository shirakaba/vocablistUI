package uk.co.birchlabs;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
//import javax.validation.constraints.NotNull;

/**
 * eg. する
 */
@Entity
@Table(name="jmdict_pronunciation")
public class JMDictPronunciation {

    @EmbeddedId
    private IdDataKey idDataKey;

    @ManyToOne(fetch = FetchType.LAZY)
    // This FK's annotated name must equal the annotated name for the target partial key in IdDataKey.
    @JoinColumn(
//            foreignKey = @ForeignKey(name = "FK_JMDICT_PRONUNCIATION_ID"), // not sure whether necessary
            name="id", insertable = false, updatable = false
    )
    private JMDictEntry jmDictEntryP;

    public JMDictPronunciation() {
    }

    public IdDataKey getIdDataKey() {
        return idDataKey;
    }

    public void setIdDataKey(IdDataKey idDataKey) {
        this.idDataKey = idDataKey;
    }

    public JMDictEntry getJmDictEntryP() {
        return jmDictEntryP;
    }

    public void setJmDictEntryP(JMDictEntry jmDictEntryP) {
        this.jmDictEntryP = jmDictEntryP;
    }
}