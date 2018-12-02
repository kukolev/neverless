package neverless.service.reader.impl;

import neverless.dto.screendata.player.GameStateDto;
import neverless.service.reader.AbstractCommandReader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class WaitCommandReader extends AbstractCommandReader {

    private String CONTRACT = COMMAND_PREFIX + "/wait";

    @Override
    public ResponseEntity<GameStateDto> read(RestTemplate restTemplate) {
        return restTemplate.postForEntity(CONTRACT, null, GameStateDto.class);
    }
}
