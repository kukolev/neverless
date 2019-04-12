package neverless.view.drawer;

import lombok.Data;
import neverless.dto.GameStateDto;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.ReentrantLock;

@Component
@Data
public class ViewContext {
    /** Game state */
    private GameStateDto gameStateDto;

    /** Screen horizontal coordinate */
    private int screenX;

    /** Screen vertical coordinate */
    private int screenY;

    private ReentrantLock lock = new ReentrantLock();

    /**
     * Sets both screen coordinates.
     *
     * @param screenX   screen horizontal coordinate
     * @param screenY   screen vertical coordinate.
     */
    public void setScreenPoint(int screenX, int screenY) {
        lock.lock();
        try {
            this.screenX = screenX;
            this.screenY = screenY;
        } finally {
            lock.unlock();
        }
    }
}
