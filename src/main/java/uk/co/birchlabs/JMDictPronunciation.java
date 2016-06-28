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

    public JMDictPronunciation() {
    }

    public IdDataKey getIdDataKey() {
        return idDataKey;
    }

    public void setIdDataKey(IdDataKey idDataKey) {
        this.idDataKey = idDataKey;
    }

    //    @ManyToOne
//    private JMDictWord jmDictWord;
}