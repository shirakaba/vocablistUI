package uk.co.birchlabs;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.util.List;

/**
 * Created by jamiebirch on 28/06/2016.
 */
@Immutable
@Entity
@Table(name="jmdict_entry")
public class JMDictEntry {
    public final static Integer START_OF_PROPER_NOUNS_ID = 5000000;

    @Id
//    @Column(name = "id", nullable=false, updatable=false)
    private Integer id;

    public JMDictEntry() {
    }

    @OneToMany(mappedBy = "jmDictEntryS", fetch = FetchType.EAGER)
    private List<JMDictSense> senses;

    // can't seem to change this to lazy. Only used by jmDictEntryRepository2.getEntries() though.
    @OneToMany(mappedBy = "jmDictEntryW", fetch = FetchType.EAGER)
    private List<JMDictWord> words;

    @OneToMany(mappedBy = "jmDictEntryP", fetch = FetchType.EAGER)
    private List<JMDictPron> pron;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<JMDictSense> getSenses() {
        return senses;
    }

    public void setSenses(List<JMDictSense> senses) {
        this.senses = senses;
    }

    public List<JMDictWord> getWords() {
        return words;
    }

    public void setWords(List<JMDictWord> words) {
        this.words = words;
    }

    public List<JMDictPron> getPron() {
        return pron;
    }

    public void setPron(List<JMDictPron> pron) {
        this.pron = pron;
    }
}
