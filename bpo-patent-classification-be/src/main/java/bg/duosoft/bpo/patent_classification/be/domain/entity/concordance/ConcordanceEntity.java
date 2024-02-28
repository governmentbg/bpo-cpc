package bg.duosoft.bpo.patent_classification.be.domain.entity.concordance;

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
@Table(name = "CF_CLASS_CPC_IPC_CONCORDANCE", schema = "IPASPROD")
public class ConcordanceEntity implements Serializable {

    @EmbeddedId
    private ConcordanceEntityPK pk;

    @Column(name = "CPC_SYMBOL")
    private String cpcSymbol;

    @Column(name = "IPC_SECTION_CODE")
    private String ipcSectionCode;

    @Column(name = "IPC_CLASS_CODE")
    private String ipcClassCode;

    @Column(name = "IPC_SUBCLASS_CODE")
    private String ipcSubclassCode;

    @Column(name = "IPC_GROUP_CODE")
    private String ipcGroupCode;

    @Column(name = "IPC_SUBGROUP_CODE")
    private String ipcSubgroupCode;

    @Column(name = "IPC_SYMBOL")
    private String ipcSymbol;

    @Column(name = "LATEST_VERSION")
    private String latestVersion;

}