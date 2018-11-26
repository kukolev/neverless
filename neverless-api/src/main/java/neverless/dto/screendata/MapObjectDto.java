package neverless.dto.screendata;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.dto.MapObjectMetaType;

@Data
@Accessors(chain = true)
public class MapObjectDto {

    private String uniqueName;
    private String signature;
    private Integer x;
    private Integer y;
    private Integer width;
    private Integer height;
    private Integer zOrder;
    private MapObjectMetaType metaType;
}
