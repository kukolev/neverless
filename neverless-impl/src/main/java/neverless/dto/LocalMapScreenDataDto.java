package neverless.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class LocalMapScreenDataDto {

    private List<MapObjectDto> objects = new ArrayList<>();
    private String signature;
}