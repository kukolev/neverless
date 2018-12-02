package neverless.service.reader.impl;

import neverless.dto.screendata.player.GameStateDto;
import neverless.service.reader.AbstractCommandReader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class DialogSelectPhraseCommandReader extends AbstractCommandReader {

    private String CONTRACT = COMMAND_PREFIX + "/dialog_select_phrase/phraseNumber/%s";

    @Override
    public ResponseEntity<GameStateDto> read(RestTemplate restTemplate) throws IOException {
        String phraseNumber = input("phraseNumber");
        String contract = String.format(CONTRACT, phraseNumber);

        return restTemplate.postForEntity(contract, null, GameStateDto.class);
    }
}