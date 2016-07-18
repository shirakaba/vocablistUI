package uk.co.birchlabs;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.util.List;

/**
 * Based on http://stackoverflow.com/questions/2611619/onetomany-and-composite-primary-keys
 */
@Immutable
@Entity
@Table(name="jmdict_sense")
public class JMDictSense {
    @Id
//    @GeneratedValue
    @Column(name = "data", nullable=false, updatable=false) // may not need this annotation
    private Integer data;

    private Integer id;

    private Integer adfixes;
    private Integer adjectives;
    private Integer adnominals;
    private Integer adverbs;
    private Integer nouns;
    private Integer particles;
    private Integer propernouns;
    private Integer verbsandaux;

    // TODO: not sure what to do about the field being an Integer called id in the real table.
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(
//            foreignKey = @ForeignKey(name = "FK_JMDICT_SENSE_ID"), // not sure whether necessary
            name="id", insertable = false, updatable = false
    )
    private JMDictEntry jmDictEntryS;

    // Note: these mappedBy names don't actually have to be unique because it searches only within-class.
    @OneToMany(mappedBy = "jmDictSenseD", fetch = FetchType.EAGER)
    private List<JMDictDef> defs;

//    @OneToMany(mappedBy = "jmDictSenseT", fetch = FetchType.EAGER)
//    private List<JMDictType> types;

    public JMDictSense() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

//    public List<JMDictType> getTypes() {
//        return types;
//    }
//
//    public void setTypes(List<JMDictType> types) {
//        this.types = types;
//    }

    public JMDictEntry getJmDictEntryS() {
        return jmDictEntryS;
    }

    public void setJmDictEntryS(JMDictEntry jmDictEntryS) {
        this.jmDictEntryS = jmDictEntryS;
    }

    public Integer getData() {
        return data;
    }

    public void setData(Integer data) {
        this.data = data;
    }

    public List<JMDictDef> getDefs() {
        return defs;
    }

    public void setDefs(List<JMDictDef> defs) {
        this.defs = defs;
    }

    public Integer getAdfixes() {
        return adfixes;
    }

    public void setAdfixes(Integer adfixes) {
        this.adfixes = adfixes;
    }

    public Integer getAdjectives() {
        return adjectives;
    }

    public void setAdjectives(Integer adjectives) {
        this.adjectives = adjectives;
    }

    public Integer getAdnominals() {
        return adnominals;
    }

    public void setAdnominals(Integer adnominals) {
        this.adnominals = adnominals;
    }

    public Integer getAdverbs() {
        return adverbs;
    }

    public void setAdverbs(Integer adverbs) {
        this.adverbs = adverbs;
    }

    public Integer getNouns() {
        return nouns;
    }

    public void setNouns(Integer nouns) {
        this.nouns = nouns;
    }

    public Integer getParticles() {
        return particles;
    }

    public void setParticles(Integer particles) {
        this.particles = particles;
    }

    public Integer getPropernouns() {
        return propernouns;
    }

    public void setPropernouns(Integer propernouns) {
        this.propernouns = propernouns;
    }

    public Integer getVerbsAndAux() {
        return verbsandaux;
    }

    public void setVerbsAndAux(Integer verbsAndAux) {
        this.verbsandaux = verbsAndAux;
    }
}
