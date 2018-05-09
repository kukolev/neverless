package neverless.resource;

import neverless.dto.command.CommandDto;
import neverless.dto.ResponseDto;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface GameControllerResource {

    @RequestMapping(value = "/command", method = RequestMethod.POST,  consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto postCommand(@RequestBody CommandDto commandDto);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseDto getState();
}