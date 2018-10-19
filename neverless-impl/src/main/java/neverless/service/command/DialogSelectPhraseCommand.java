package neverless.service.command;

import neverless.domain.DialogSelectPhraseParams;
import neverless.service.screendata.DialogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DialogSelectPhraseCommand extends AbstractCommand<DialogSelectPhraseParams> {

    @Autowired
    private DialogService dialogService;

    @Override
    public void execute(DialogSelectPhraseParams params) {
        dialogService.selectPhrase(params.getPhraseNumber());
        registerEvent(eventFactory.createDialogPhraseSelectedEvent(params.getPhraseNumber()));
    }
}
