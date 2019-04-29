package neverless.domain.entity.mapobject.tree;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.util.Signatures;


@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public final class FirTree extends AbstractTree {

    @Override
    public String getSignature() {
        return Signatures.IMG_TREE;
    }
}
