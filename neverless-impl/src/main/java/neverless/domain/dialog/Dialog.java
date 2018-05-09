package neverless.domain.dialog;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Dialog {

    private List<NpcPhrase> rootNpcPhrases = new ArrayList<>();
}
