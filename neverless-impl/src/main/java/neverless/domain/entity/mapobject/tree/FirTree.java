package neverless.domain.entity.mapobject.tree;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.Resources;

import javax.persistence.Entity;

@Data
@Accessors(chain = true)
@Entity
public final class FirTree extends AbstractTree {

    @Override
    public String getSignature() {
        return Resources.IMG_TREE;
    }
}
