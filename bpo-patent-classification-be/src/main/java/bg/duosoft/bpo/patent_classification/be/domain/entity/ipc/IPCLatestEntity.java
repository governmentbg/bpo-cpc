package bg.duosoft.bpo.patent_classification.be.domain.entity.ipc;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Slf4j
@Table(name = "CF_CLASS_IPC_LATEST_SPEC", schema = "IPASPROD")
public class IPCLatestEntity implements Serializable {

    @EmbeddedId
    private IPCEntityPK ipcEntityPK;

    @Column(name = "ROW_VERSION")
    private Integer rowVersion;

    @Column(name = "IPC_NAME")
    private String ipcName;

    @Column(name = "XML_DESIGNER")
    private String xmlDesigner;

    @Column(name = "IPC_LATEST_VERSION")
    private String ipcLatestVersion;

    @Column(name = "SYMBOL")
    private String symbol;

}