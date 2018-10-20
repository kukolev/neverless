package neverless.dto.screendata.inventory;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class WeaponDto {

    private String title;
    private Integer power;
}
