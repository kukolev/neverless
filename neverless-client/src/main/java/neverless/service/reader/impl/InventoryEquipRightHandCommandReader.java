package neverless.service.reader.impl;

import neverless.dto.screendata.player.ResponseDto;
import neverless.service.reader.AbstractCommandReader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class InventoryEquipRightHandCommandReader extends AbstractCommandReader {

    private String CONTRACT = COMMAND_PREFIX + "/inventory_equip_right_hand/itemId/%s";

    @Override
    public ResponseEntity<ResponseDto> read(RestTemplate restTemplate) throws IOException {
        String itemId = input("itemId");
        String contract = String.format(CONTRACT, itemId);

        return restTemplate.postForEntity(contract, null, ResponseDto.class);
    }
}
