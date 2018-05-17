package neverless.service.core;

import neverless.dto.command.CommandDto;
import neverless.service.command.AbstractCommand;
import neverless.service.command.DialogSelectPhraseCommand;
import neverless.service.command.DialogStartCommand;
import neverless.service.command.MapGoCommand;
import neverless.service.command.MenuStartNewGameCommand;
import neverless.service.command.RefreshCommand;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static neverless.dto.command.Direction.DOWN;
import static neverless.dto.command.Direction.LEFT;
import static neverless.dto.command.Direction.RIGHT;
import static neverless.dto.command.Direction.UP;
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
        when(context.getBean(MapGoCommand.class)).thenReturn(new MapGoCommand());

        AbstractCommand command = commandMapper.build(dto);

        assertEquals(command.getClass(), MapGoCommand.class);
        assertEquals(((MapGoCommand) command).getDirection() , UP);
    }

    @Test
    public void buildLocalMapGoDownTest() {
        CommandDto dto = new CommandDto()
                .setName("LOCAL_MAP_GO_DOWN");
        when(context.getBean(MapGoCommand.class)).thenReturn(new MapGoCommand());

        AbstractCommand command = commandMapper.build(dto);

        assertEquals(command.getClass(), MapGoCommand.class);
        assertEquals(((MapGoCommand) command).getDirection() , DOWN);
    }

    @Test
    public void buildLocalMapGoLeftTest() {
        CommandDto dto = new CommandDto()
                .setName("LOCAL_MAP_GO_LEFT");
        when(context.getBean(MapGoCommand.class)).thenReturn(new MapGoCommand());

        AbstractCommand command = commandMapper.build(dto);

        assertEquals(command.getClass(), MapGoCommand.class);
        assertEquals(((MapGoCommand) command).getDirection() , LEFT);
    }

    @Test
    public void buildLocalMapGoRightTest() {
        CommandDto dto = new CommandDto()
                .setName("LOCAL_MAP_GO_RIGHT");
        when(context.getBean(MapGoCommand.class)).thenReturn(new MapGoCommand());

        AbstractCommand command = commandMapper.build(dto);

        assertEquals(command.getClass(), MapGoCommand.class);
        assertEquals(((MapGoCommand) command).getDirection() , RIGHT);
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
                .setName("MAIN_MENU_START_NEW_GAME");
        when(context.getBean(MenuStartNewGameCommand.class)).thenReturn(new MenuStartNewGameCommand(null, null));

        AbstractCommand command = commandMapper.build(dto);

        assertEquals(command.getClass(), MenuStartNewGameCommand.class);
    }

    @Test
    public void builGlobalRefreshTest() {
        CommandDto dto = new CommandDto()
                .setName("GLOBAL_REFRESH");
        when(context.getBean(RefreshCommand.class)).thenReturn(new RefreshCommand());

        AbstractCommand command = commandMapper.build(dto);

        assertEquals(command.getClass(), RefreshCommand.class);
    }
}