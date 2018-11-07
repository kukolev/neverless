package neverless.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true)
@Entity
public abstract class AbstractGameObject implements Serializable {

    @ElementCollection(targetClass=String.class)
    @Column
    private Map<String, String> params = new HashMap<>();

    @EmbeddedId
    private GameObjectId id = new GameObjectId(null, null);

    public void setUniqueName(String uniqueName) {
        id.setUniqueName(uniqueName);
    }

    public String getParamStr(String key) {
        Object val = params.get(key);
        if (val == null) {
            val = "";
        }
        return (String) val;
    }

    public Integer getParamInt(String key) {
        Object val = params.get(key);
        if (val == null) {
            val = 0;
        }
        return (Integer) val;
    }

    public Boolean getParamBool(String key) {
        Object val = params.get(key);
        if (val == null) {
            val = false;
        }
        return (Boolean) val;
    }

    public void setParam(String key, Object val) {
        params.put(key, val.toString());
    }
}
