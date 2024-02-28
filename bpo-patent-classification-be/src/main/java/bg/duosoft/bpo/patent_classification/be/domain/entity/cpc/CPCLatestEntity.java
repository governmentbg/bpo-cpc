package bg.duosoft.bpo.patent_classification.be.domain.entity.cpc;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Slf4j
@Table(name = "CF_CLASS_CPC_LATEST_SPEC", schema = "IPASPROD")
public class CPCLatestEntity implements Serializable {

    @EmbeddedId
    private CPCEntityPK cpcEntityPK;

    @Column(name = "ROW_VERSION")
    private Integer rowVersion;

    @Column(name = "CPC_NAME")
    private String cpcName;

    @Column(name = "CPC_LATEST_VERSION")
    private String cpcLatestVersion;

    @Column(name = "SYMBOL")
    private String symbol;

}