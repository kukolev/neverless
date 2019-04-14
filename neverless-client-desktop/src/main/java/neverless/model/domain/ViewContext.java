package neverless.model.domain;

import lombok.Data;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Container for all objects needed for display on screen.
 */
@Data
public class ViewContext {
    /** Screen horizontal coordinate */
    private int screenX;

    /** Screen vertical coordinate */
    private int screenY;

    private DestinationMarkerData marker;

    private ReentrantLock lock = new ReentrantLock();

    /**
     * Sets both screen coordinates.
     *
     * @param screenX   screen horizontal coordinate.
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
