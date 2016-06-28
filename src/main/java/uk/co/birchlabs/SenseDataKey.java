package uk.co.birchlabs;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Based on http://stackoverflow.com/questions/13032948/how-to-create-and-handle-composite-primary-key-in-jpa
 */
@Embeddable
public class SenseDataKey  implements Serializable {
    @Column(name = "sense", nullable = false, updatable=false)
    private Integer sense;

    @Column(name = "data", nullable = false, updatable=false)
    private String data;

    public Integer getSense() {
        return sense;
    }

    public void setSense(Integer sense) {
        this.sense = sense;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public int hashCode() {
        return sense.hashCode() + data.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof SenseDataKey){
            SenseDataKey senseDataKey = (SenseDataKey) obj;

            if(!senseDataKey.getSense().equals(sense)) return false;
            if(!senseDataKey.getData().equals(data)) return false;
            return true;
        }
        return false;
    }
}
