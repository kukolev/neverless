package neverless.service.screendata;

import neverless.domain.entity.mapobject.Player;
import neverless.dto.screendata.PlayerDto;
import neverless.dto.screendata.player.PlayerScreenDataDto;
import neverless.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public PlayerScreenDataDto getScreenData() {
        PlayerDto playerDto = new PlayerDto();
        PlayerScreenDataDto screenDataDto = new PlayerScreenDataDto();

        Player player = playerRepository.get();
        playerDto
                .setHealthPoints(player.getHealthPoints())
                .setLocation(player.getLocation())
                .setX(player.getX())
                .setY(player.getY());
        screenDataDto.setPlayerDto(playerDto);
        return screenDataDto;
    }
}