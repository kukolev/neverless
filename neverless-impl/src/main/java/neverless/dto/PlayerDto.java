package neverless.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PlayerDto {

    private Integer healthPoints;
    private Integer x;
    private Integer y;
    private Integer platformCenterX;
    private Integer platformCenterY;
    private String location;
}
