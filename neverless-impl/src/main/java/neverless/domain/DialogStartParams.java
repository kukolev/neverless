package neverless.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DialogStartParams extends AbstractCommandParams {

    private int npcX;
    private int npcY;
}
