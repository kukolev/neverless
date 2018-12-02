package neverless.service.reader.impl;

import neverless.dto.screendata.player.GameStateDto;
import neverless.service.reader.AbstractCommandReader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class MapGoLeftCommandReader extends AbstractCommandReader {

    private String CONTRACT = COMMAND_PREFIX + "/map_go_left";

    @Override
    public ResponseEntity<GameStateDto> read(RestTemplate restTemplate) {
        return restTemplate.postForEntity(CONTRACT, null, GameStateDto.class);
    }
}
