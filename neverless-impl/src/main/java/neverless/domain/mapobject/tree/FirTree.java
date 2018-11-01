package neverless.domain.mapobject.tree;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public final class FirTree extends AbstractTree {

    @Override
    public String getSignature() {
        return "FIRTREE_";
    }
}
