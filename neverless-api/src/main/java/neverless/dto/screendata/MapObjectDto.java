package neverless.dto.screendata;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.MapObjectMetaType;
import neverless.PlatformShape;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class MapObjectDto {

    private String uniqueName;
    private String signature;
    private Integer x;
    private Integer y;
    private MapObjectMetaType metaType;
    private PlatformShape platformShape;
    private Integer platformWidth;
    private Integer platformHeight;
    private Integer PlatformCenterX;
    private Integer PlatformCenterY;
    private List<CoordinateDto> platformCoordinates = new ArrayList<>();
}
