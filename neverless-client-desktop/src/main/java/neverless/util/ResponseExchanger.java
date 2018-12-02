package neverless.util;

import neverless.dto.screendata.player.GameStateDto;
import org.springframework.stereotype.Component;

import java.util.concurrent.Exchanger;

@Component
public class ResponseExchanger extends Exchanger<GameStateDto> {
}
