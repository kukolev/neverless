package neverless.service.core;

import neverless.dto.command.CommandDto;
import neverless.service.command.AbstractCommand;
import neverless.service.command.DialogSelectPhraseCommand;
import neverless.service.command.DialogStartCommand;
import neverless.service.command.MapGoDownCommand;
import neverless.service.command.MapGoLeftCommand;
import neverless.service.command.MapGoRightCommand;
import neverless.service.command.MapGoUpCommand;
import neverless.service.command.MenuStartNewGameCommand;
import neverless.service.command.GlobalRefreshCommand;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class CommandMapperTest {

    @Mock
    private ApplicationContext context;
    @InjectMocks
    private CommandMapper commandMapper;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void buildLocalMapStartDialogTest() {
        Map<String, String> bundle = new HashMap<>();
        bundle.put("npcX", "1");
        bundle.put("npcY", "1");
        CommandDto dto = new CommandDto()
                .setName("LOCAL_MAP_START_DIALOG")
                .setBundle(bundle);
        when(context.getBean(DialogStartCommand.class)).thenReturn(new DialogStartCommand());

        AbstractCommand command = commandMapper.build(dto);

        assertEquals(command.getClass(), DialogStartCommand.class);
    }

    @Test
    public void buildLocalMapGoUpTest() {
        CommandDto dto = new CommandDto()
                .setName("LOCAL_MAP_GO_UP");
        when(context.getBean(MapGoUpCommand.class)).thenReturn(new MapGoUpCommand());

        AbstractCommand command = commandMapper.build(dto);

        assertEquals(command.getClass(), MapGoUpCommand.class);
    }

    @Test
    public void buildLocalMapGoDownTest() {
        CommandDto dto = new CommandDto()
                .setName("LOCAL_MAP_GO_DOWN");
        when(context.getBean(MapGoDownCommand.class)).thenReturn(new MapGoDownCommand());

        AbstractCommand command = commandMapper.build(dto);

        assertEquals(command.getClass(), MapGoDownCommand.class);
    }

    @Test
    public void buildLocalMapGoLeftTest() {
        CommandDto dto = new CommandDto()
                .setName("LOCAL_MAP_GO_LEFT");
        when(context.getBean(MapGoLeftCommand.class)).thenReturn(new MapGoLeftCommand());

        AbstractCommand command = commandMapper.build(dto);

        assertEquals(command.getClass(), MapGoLeftCommand.class);
    }

    @Test
    public void buildLocalMapGoRightTest() {
        CommandDto dto = new CommandDto()
                .setName("LOCAL_MAP_GO_RIGHT");
        when(context.getBean(MapGoRightCommand.class)).thenReturn(new MapGoRightCommand());

        AbstractCommand command = commandMapper.build(dto);

        assertEquals(command.getClass(), MapGoRightCommand.class);
    }

    @Test
    public void buildDialogSelectPhraseTest() {
        Map<String, String> bundle = new HashMap<>();
        bundle.put("phraseNumber", "1");
        CommandDto dto = new CommandDto()
                .setName("DIALOG_SELECT_PHRASE")
                .setBundle(bundle);
        when(context.getBean(DialogSelectPhraseCommand.class)).thenReturn(new DialogSelectPhraseCommand());

        AbstractCommand command = commandMapper.build(dto);

        assertEquals(command.getClass(), DialogSelectPhraseCommand.class);
        assertEquals(((DialogSelectPhraseCommand) command).getPhraseNumber() , Integer.valueOf(1));
    }

    @Test
    public void buildMainMenuStartNewGameTest() {
        CommandDto dto = new CommandDto()
                .setName("MENU_START_NEW_GAME");
        when(context.getBean(MenuStartNewGameCommand.class)).thenReturn(new MenuStartNewGameCommand(null, null));

        AbstractCommand command = commandMapper.build(dto);

        assertEquals(command.getClass(), MenuStartNewGameCommand.class);
    }

    @Test
    public void builGlobalRefreshTest() {
        CommandDto dto = new CommandDto()
                .setName("GLOBAL_REFRESH");
        when(context.getBean(GlobalRefreshCommand.class)).thenReturn(new GlobalRefreshCommand());

        AbstractCommand command = commandMapper.build(dto);

        assertEquals(command.getClass(), GlobalRefreshCommand.class);
    }
}