package neverless.util;

import neverless.view.renderer.Frame;
import org.springframework.stereotype.Component;

import java.util.concurrent.Exchanger;

@Component
public class FrameExchanger extends Exchanger<Frame> {
}
