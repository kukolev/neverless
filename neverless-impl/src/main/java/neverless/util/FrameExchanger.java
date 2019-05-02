package neverless.util;

import neverless.domain.view.Frame;
import org.springframework.stereotype.Component;

/**
 * Storage for last rendered frame.
 */
@Component
public class FrameExchanger {

    private Frame frame;

    /**
     * Sets frame into storage.
     * Methods are thread-safe according to Java language specification.
     *
     * @param frame frame that should be stored.
     */
    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    /**
     * Returns frame from storage.
     * Methods are thread-safe according to Java language specification.
     */
    public Frame getFrame() {
        return frame;
    }
}
