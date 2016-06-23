package uk.co.birchlabs;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.repository.query.Param;

import javax.persistence.*;
//import javax.validation.constraints.NotNull;

/**
 * eg. to do; to cause to become, to make (into), to turn (into); to serve as, to act as, to work as...
 */
@Entity
@Table(name="jmdict_definition")
public class JMDictDefinition {

    @Id
//    @GeneratedValue(strategy= GenerationType.TABLE)
    @GenericGenerator(name="kaugen" , strategy="increment")
    @GeneratedValue(generator="kaugen")
//    @NotNull
    private Integer id;
    private String sense;
    private String data;

    public JMDictDefinition() {
    }

    public String getSense() {
        return sense;
    }

    public void setSense(String sense) {
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

//    @ManyToOne
//    private JMDictWord jmDictWord;

//    @Query("from jmdict_definition a join a.category c where c.name=:categoryName")
//    public Iterable<Auction> findByCategory(@Param("categoryName") String categoryName);
}
