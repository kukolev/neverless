package neverless.service.command;

import lombok.Setter;
import neverless.domain.event.Event;
import neverless.service.DialogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DialogSelectPhraseCommand extends AbstractCommand {

    @Autowired
    private DialogService dialogService;


    @Setter
    private Integer phraseNumber;

    @Override
    public Event onExecute() {
        dialogService.selectPhrase(phraseNumber);
        return eventFactory.createDialogPhraseSelectedEvent(phraseNumber);
    }
}
