package uk.co.birchlabs;

import com.google.common.collect.Lists;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
//import javax.validation.constraints.NotNull;

/**
 * eg. 為る
 */
@Entity
@Table(name="jmdict_word")
public class JMDictWord {

//    @Autowired
//    JMDictDefinitionRepository repository;
//
//    @Autowired
//    JMDictDefinitionRepository2 repository2;


    public JMDictWord(JMDictWord anotherInstance, JMDictDefinition definitions) {
        this.rowId = anotherInstance.rowId;
        this.id = anotherInstance.id;
        this.data = anotherInstance.data;
        this.definitions = Lists.newArrayList(definitions);
    }

    @Id
    @Column(name="_rowid_")
    private Integer rowId;

//    @Id
//    @GenericGenerator(name="kaugen" , strategy="increment")
//    @GeneratedValue(generator="kaugen")
    private Integer id;

    private String data;

//    @OneToMany(mappedBy="jmDictWord")
//    @ManyToMany()
//    @JoinTable(
////            targetEntity = JMDictDefinition.class,
////            mappedBy="jmDictWord"
//            name="jmdict_definition",
//            joinColumns=@JoinColumn(name="JMDictWordUniqueID", referencedColumnName="id"),
//            inverseJoinColumns=@JoinColumn(name="JMDictDefinitionUniqueID", referencedColumnName="id")
//    )

//    @JoinColumn(name="id", insertable=false, updatable=false)
//    @JoinTable(
//            name="jmdict_definition",
//            joinColumns = { @JoinColumn(
//                    // name is the name we assign to the column in our temporary many-to-many join table
//                    name="_rowid_",
//                    // referencedColumnName is the name of the column upon JMDictWord
//                    referencedColumnName="JMDictWordUniqueID"
//            ) },
//            inverseJoinColumns = { @JoinColumn(
//                    name="_rowid_",
//                    referencedColumnName="JMDictDefinitionUniqueID"
//            ) }
//    )
//    @Formula("(SELECT d.LABEL FROM jmdict_definition d WHERE ot1.CODE = CODE_FK_1)")
    @Transient
    private List<JMDictDefinition> definitions;

    public JMDictWord() {
    }

    public List<JMDictDefinition> getDefinitions() {
        return definitions;
    }

    public Integer getRowId() {

        return rowId;
    }

//    @PostLoad
//    private void initDefinitions() {
//        definitions = repository2.getAllWithId(Collections.singleton());
//    }

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
}
