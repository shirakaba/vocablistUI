package uk.co.birchlabs;

import javax.persistence.*;
//import javax.validation.constraints.NotNull;

/**
 * eg. 為る
 */
@Entity
@Table(name="jmdict_word")
public class JMDictWord {

    @EmbeddedId
    private IdDataKey idDataKey;

    @ManyToOne
    // This FK's annotated name must equal the annotated name for the target partial key in IdDataKey.
    @JoinColumn(
//            foreignKey = @ForeignKey(name = "FK_JMDICT_WORD_ID"), // not sure whether necessary
            name="id", insertable = false, updatable = false
    )
    private JMDictEntry jmDictEntryW;

    public JMDictWord() {
    }

    public IdDataKey getIdDataKey() {
        return idDataKey;
    }

    public void setIdDataKey(IdDataKey idDataKey) {
        this.idDataKey = idDataKey;
    }
}
