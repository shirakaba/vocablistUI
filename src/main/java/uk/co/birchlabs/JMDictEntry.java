package uk.co.birchlabs;

import javax.persistence.*;
import java.util.List;

/**
 * Created by jamiebirch on 28/06/2016.
 */
@Entity
@Table(name="jmdict_entry")
public class JMDictEntry {
    @Id
    @Column(name = "entryId", nullable=false, updatable=false)
    private Integer id;

    public JMDictEntry() {
    }

    @OneToMany(mappedBy = "jmDictEntryS", fetch = FetchType.EAGER)
//    @IndexColumn(name = "pos", base=0)
    private List<JMDictSense> senses;

    @OneToMany(mappedBy = "jmDictEntryW", fetch = FetchType.EAGER)
//    @IndexColumn(name = "pos", base=0)
    private List<JMDictWord> words;
}
