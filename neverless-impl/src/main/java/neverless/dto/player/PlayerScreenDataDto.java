package neverless.dto.player;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.dto.PlayerDto;

@Data
@Accessors(chain = true)
public class PlayerScreenDataDto {

    private PlayerDto playerDto;
}
