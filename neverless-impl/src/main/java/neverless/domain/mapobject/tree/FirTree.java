package neverless.domain.mapobject.tree;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public final class FirTree extends AbstractTree {

    public FirTree() {
        setWidth(1);
        setHeight(1);
        setSignature("FIRTREE_");
    }
}
