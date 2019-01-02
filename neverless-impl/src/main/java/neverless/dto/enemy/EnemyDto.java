package neverless.dto.enemy;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.dto.inventory.WeaponDto;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class EnemyDto {
    private String uniqueName;
    private String name;
    private Integer x;
    private Integer y;
    private Integer healthPoints;
    private List<WeaponDto> weapons = new ArrayList<>();
}
