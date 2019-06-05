package neverless.domain.view;

import javafx.scene.control.TextArea;
import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.model.entity.inventory.Inventory;
import neverless.domain.model.entity.mapobject.Profile;

@Data
@Accessors(chain = true)
public class ProfileWidget {

    private String strength;
    private String speed;
    private String intelligence;
    private String luck;
    private String experience;
    private String money;

    /**
     * Converts given profile data into inner representation, adopted to drawing.
     *
     * @param profile   player profile data.
     * @param inventory player's inventory.
     */
    public void mapFromProfile(Profile profile, Inventory inventory) {
        strength = "    Strength - " + profile.getStrength();
        speed = "    Speed - " + profile.getSpeed();
        intelligence = "    Intelligence - " + profile.getIntelligence();
        luck = "    Luck - " + profile.getLuck();
        experience = "    Experience - " + profile.getExperience();
        money = "Money - " + inventory.getMoney().getAmount();
    }

    public void draw(TextArea textArea) {
        textArea.setText("Player stats:\n\n" +
                strength + "\n"
                + speed + "\n"
                + intelligence + "\n"
                + luck + "\n"
                + experience);
    }
}
