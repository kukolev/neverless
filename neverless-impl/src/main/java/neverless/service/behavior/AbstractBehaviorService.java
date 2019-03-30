package neverless.service.behavior;

import neverless.domain.entity.BehaviorState;
import neverless.domain.entity.mapobject.AbstractMapObject;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;

@Service
public abstract class AbstractBehaviorService<T extends AbstractMapObject> {

    private final Class typeClass = (Class) ((ParameterizedType)getClass().getGenericSuperclass())
            .getActualTypeArguments()[0];

    /**
     * Processes command for object.
     *
     * @param object    object whose command should be processed.
     */
    public final void processObject(T object) {
        BehaviorState newState = onProcess(object);
        object.getBehavior().changeState(newState);
    }

    protected abstract BehaviorState onProcess(T object);

    /**
     * Returns true if current service is able to process object.
     * Calculation is based on reflection.
     *
     * @param object    object that should be analyzed.
     */
    public boolean canProcessObject(AbstractMapObject object) {
        return typeClass == object.getClass();
    }
}
