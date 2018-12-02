package neverless.service.reader.impl;

import neverless.dto.screendata.player.GameStateDto;
import neverless.service.reader.AbstractCommandReader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class DialogStartCommandReader extends AbstractCommandReader {

    private String CONTRACT = COMMAND_PREFIX + "/dialog_start/npcX/%s/npcY/%s";

    @Override
    public ResponseEntity<GameStateDto> read(RestTemplate restTemplate) throws IOException {
        String npcX = input("npcX");
        String npcY = input("npcY");
        String contract = String.format(CONTRACT, npcX, npcY);

        return restTemplate.postForEntity(contract, null, GameStateDto.class);
    }
}
