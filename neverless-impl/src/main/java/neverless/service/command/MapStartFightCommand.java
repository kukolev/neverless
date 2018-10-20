package neverless.service.command;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class MapStartFightCommand extends AbstractCommand<EmptyParams> {

    @Getter
    private Integer npcX;
    @Getter
    private Integer npcY;

    @Override
    public void execute(EmptyParams params) {
        // todo: implement it;
    }
}
