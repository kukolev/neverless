package neverless.resource;

import neverless.dto.ResponseDto;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/commands")
public interface GameControllerResource {

    @GetMapping(value = "/")
    ResponseDto getState();

    @PostMapping(value = "/start_new_game")
    ResponseDto cmdStartNewGame();

    @PostMapping(value = "/map_go_down")
    ResponseDto cmdMoveDown();

    @PostMapping(value = "/map_go_up")
    ResponseDto cmdMoveUp();

    @PostMapping(value = "/map_go_left")
    ResponseDto cmdMoveLeft();

    @PostMapping(value = "/map_go_right")
    ResponseDto cmdMoveRight();

    @PostMapping(value = "/dialog_start/npcX/{npcX}/npcY/{npcY}")
    ResponseDto cmdDialogStart(@PathVariable Integer npcX,
                               @PathVariable Integer npcY);

    @PostMapping(value = "/dialog_select_phrase/phraseNumber/{phraseNumber}")
    ResponseDto cmdDialogSelectPhrase(@PathVariable Integer phraseId);
}