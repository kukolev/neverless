package neverless.domain.model.dialog.event;

@FunctionalInterface
public interface AbstractDialogEvent {

    void execute();
}
