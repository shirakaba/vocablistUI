package uk.co.birchlabs;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.repository.query.Param;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;
//import javax.validation.constraints.NotNull;

/**
 * eg. to do; to cause to become, to make (into), to turn (into); to serve as, to act as, to work as...
 */
@Entity
@Table(name="jmdict_definition")
public class JMDictDefinition {

    @Id
    @Column(name="_rowid_")
    private Integer rowId;

//    @Id
//    @GenericGenerator(name="kaugen" , strategy="increment")
//    @GeneratedValue(generator="kaugen")
    private Integer id;
    private Integer sense;
    private String data;

    public JMDictDefinition() {
    }

    public Integer getSense() {
        return sense;
    }

    public void setSense(Integer sense) {
        this.sense = sense;
    }

    public Integer getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setData(String identifier) {
        this.data = data;
    }

//    @ManyToOne(fetch=FetchType.EAGER)
//    @JoinColumn(name="id", insertable=false, updatable=false)
//    @ManyToMany(mappedBy = "definitions")
//    private List<JMDictWord> jmDictWord;

    public Integer getRowId() {
        return rowId;
    }

    // do not serialize
//    @XmlTransient
//    public List<JMDictWord> getJmDictWord() {
//        return jmDictWord;
//    }

    //    @Query("from jmdict_definition a join a.category c where c.name=:categoryName")
//    public Iterable<Auction> findByCategory(@Param("categoryName") String categoryName);
}
