package neverless.api.resource;

import lombok.AllArgsConstructor;
import neverless.dto.CommandDto;
import neverless.dto.ResponseDto;
import neverless.service.CommandRouterService;
import neverless.service.RenderService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class GameControllerResourceImpl implements GameControllerResource {

    private CommandRouterService commandRouterService;
    private RenderService renderService;

    @RequestMapping(value = "/command", method = RequestMethod.POST,  consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto postCommand(@RequestBody CommandDto commandDto) {
        commandRouterService.execute(commandDto);
        return getState();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseDto getState() {
        return commandRouterService.getState();
    }

//    @ExceptionHandler(InvalidCommandException.class)
//    public ResponseEntity<ResponseDto> exceptionHandler() {
//        return ResponseEntity.badRequest().body(new ResponseDto());
//    }
}