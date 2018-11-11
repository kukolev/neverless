package neverless.dto.screendata.enemy;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class EnemyScreenDataDto {

    private List<EnemyDto> enemies = new ArrayList<>();
}