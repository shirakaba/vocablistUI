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

    @Id
//    @GeneratedValue(strategy= GenerationType.TABLE)
    @GenericGenerator(name="kaugen" , strategy="increment")
    @GeneratedValue(generator="kaugen")
//    @NotNull
    private Integer id;

    private String data;

    public JMDictPronunciation() {
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

    @ManyToOne
    private JMDictWord jmDictWord;
}
