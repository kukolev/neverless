package neverless.domain.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.cache.annotation.CachePut;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true)
@Entity
public abstract class AbstractGameObject implements Serializable {

    @Id
    @GeneratedValue(generator = "generic-uuid")
    @GenericGenerator(name = "generic-uuid", strategy = "uuid")
    private String uniqueName;
}
