package bg.duosoft.bpo.patent_classification.be.domain.entity.patent_ipc;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Slf4j
@Table(name = "IP_PATENT_IPC_CLASSES", schema = "IPASPROD")
public class PatentIpcEntity implements Serializable {

    @EmbeddedId
    private PatentIpcEntityPK pk;

    @Column(name = "ROW_VERSION")
    private Integer rowVersion;

    @Column(name = "IPC_SYMBOL_POSITION")
    private String ipcSymbolPosition;

    @Column(name = "IPC_SYMBOL_CAPTURE_DATE")
    private LocalDateTime ipcSymbolCaptureDate;

    @Column(name = "IPC_WPUBLISH_VALIDATED")
    private String ipcWPublishValidated;

    @Override
    public String toString() {
        return "[" + pk.getPatent().getPk().toString() + " - " + pk.getIpc().getIpcEntityPK().toString() + "]";
    }
}