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
    @Id
//    @Column(name = "id", nullable=false, updatable=false)
    private Integer id;

    public JMDictEntry() {
    }

    @OneToMany(mappedBy = "jmDictEntryS", fetch = FetchType.LAZY)
    private List<JMDictSense> senses;

    // can't seem to change this to lazy. Only used by jmDictEntryRepository2.getEntries() though.
    @OneToMany(mappedBy = "jmDictEntryW", fetch = FetchType.EAGER)
    private List<JMDictWord> words;

    @OneToMany(mappedBy = "jmDictEntryP", fetch = FetchType.LAZY)
    private List<JMDictPronunciation> pron;

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

    public List<JMDictPronunciation> getPron() {
        return pron;
    }

    public void setPron(List<JMDictPronunciation> pron) {
        this.pron = pron;
    }
}
