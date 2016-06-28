package uk.co.birchlabs;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Based on http://stackoverflow.com/questions/13032948/how-to-create-and-handle-composite-primary-key-in-jpa
 */
@Embeddable
public class IdDataKey implements Serializable {
    @Column(name = "entryId", nullable = false, updatable=false)
    private Integer id;

    @Column(name = "data", nullable = false, updatable=false)
    private String data;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public int hashCode() {
        return id.hashCode() + data.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof IdDataKey){
            IdDataKey idDataKey = (IdDataKey) obj;

            if(!idDataKey.getId().equals(id)) return false;
            if(!idDataKey.getData().equals(data)) return false;
            return true;
        }
        return false;
    }
}
