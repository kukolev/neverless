package neverless.service.behavior;

import neverless.domain.entity.AbstractGameObject;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;

@Service
public abstract class AbstractBehaviorService<T extends AbstractGameObject> {

    private final Class typeClass = (Class) ((ParameterizedType)getClass().getGenericSuperclass())
            .getActualTypeArguments()[0];

    /**
     * Processes command for object.
     *
     * @param object    object whose command should be processed.
     */
    public abstract void processObject(T object);

    /**
     * Returns true if current service is able to process object.
     * Calculation is based on reflection.
     *
     * @param object    object that should be analyzed.
     */
    public boolean canProcessObject(AbstractGameObject object) {
        return typeClass == object.getClass();
    }
}
