package neverless.service.reader.impl;

import neverless.dto.screendata.player.GameStateDto;
import neverless.service.reader.AbstractCommandReader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class InventoryClearLeftHandCommandReader extends AbstractCommandReader {

    private String CONTRACT = COMMAND_PREFIX + "/inventory_clear_left_hand";

    @Override
    public ResponseEntity<GameStateDto> read(RestTemplate restTemplate) throws IOException {
        return restTemplate.postForEntity(CONTRACT, null, GameStateDto.class);
    }
}
