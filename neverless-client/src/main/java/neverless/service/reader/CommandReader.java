package neverless.service.reader;

import neverless.dto.screendata.player.GameStateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public interface CommandReader {

    ResponseEntity<GameStateDto> read(RestTemplate restTemplate) throws IOException;
}