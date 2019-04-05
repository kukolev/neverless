package neverless.domain.entity.mapobject;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Profile {
    private int strength;
    private int agility;
    private int luck;
    private int intelligence;

    private int swords;
    private int shields;

    private int experience;
    private int level;
}