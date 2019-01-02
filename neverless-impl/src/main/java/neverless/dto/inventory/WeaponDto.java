package neverless.dto.inventory;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class WeaponDto {

    private String title;
    private Integer power;
}
