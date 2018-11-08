package neverless.domain.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true)
@Entity
public abstract class AbstractGameObject implements Serializable {

    private static final String DEFAULT_BOOL = "false";
    private static final String DEFAULT_STRING = "";
    private static final String DEFAULT_INTEGER = "false";


    @ElementCollection(targetClass=String.class)
    @Column
    private Map<String, String> params = new HashMap<>();

    @EmbeddedId
    private GameObjectId id = new GameObjectId(null, null);

    /**
     * Sets unique name part of id.
     *
     * @param uniqueName    unique name of this object.
     */
    public void setUniqueName(String uniqueName) {
        id.setUniqueName(uniqueName);
    }

    /**
     * Returns param casted to String.
     *
     * @param key   param name.
     */
    public String getParamStr(String key) {
        String val = params.get(key);
        if (val == null) {
            val = DEFAULT_STRING;
        }
        return val;
    }

    /**
     * Returns param casted to Integer.
     *
     * @param key   param name.
     */
    public Integer getParamInt(String key) {
        String val = params.get(key);
        if (val == null) {
            val = DEFAULT_INTEGER;
        }
        return Integer.parseInt(val);
    }

    /**
     * Returns param casted to Boolean.
     *
     * @param key   param name.
     */
    public Boolean getParamBool(String key) {
        String val = params.get(key);
        if (val == null) {
            val = DEFAULT_BOOL;
        }
        return Boolean.parseBoolean((String) val);
    }

    /**
     * Sets param.
     *
     * @param key   param name.
     * @param val   param value.
     */
    public void setParam(String key, Object val) {
        params.put(key, val.toString());
    }
}
