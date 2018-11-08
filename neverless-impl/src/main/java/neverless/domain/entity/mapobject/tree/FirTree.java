package neverless.domain.entity.mapobject.tree;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;

@Data
@Accessors(chain = true)
@Entity
public final class FirTree extends AbstractTree {

    @Override
    public String getSignature() {
        return "FIRTREE_";
    }
}
