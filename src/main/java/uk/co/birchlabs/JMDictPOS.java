package uk.co.birchlabs;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * e.g. adv-na
 */
@Entity
@Table(name="jmdict_type")
public class JMDictPOS {
    @Id
//    @GeneratedValue(strategy= GenerationType.TABLE)
    @GenericGenerator(name="kaugen" , strategy="increment")
    @GeneratedValue(generator="kaugen")
//    @NotNull
    private Integer id; // 214,245 (with some duplicated as two data are applicable: eg. both adv & adv-to)
    private Integer sense; // 193,861 (with some duplicated as two data are applicable: eg. both adv & adv-to)
    private String data; // eg. n, vs, adv-na...

    public JMDictPOS() {
    }

    public Integer getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public Integer getSense() { return sense; }

    public void setSense(Integer sense) { this.sense = sense; }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setData(String identifier) {
        this.data = data;
    }
}
