package neverless.dto.screendata.player;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.dto.screendata.PlayerDto;

@Data
@Accessors(chain = true)
public class PlayerScreenDataDto {

    private PlayerDto playerDto;
}
