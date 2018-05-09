package neverless.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true)
public class AbstractGameObject {

    private Map<String, Object> params = new HashMap<>();
    private String uniqueName;

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
        params.put(key, val);
    }
}
