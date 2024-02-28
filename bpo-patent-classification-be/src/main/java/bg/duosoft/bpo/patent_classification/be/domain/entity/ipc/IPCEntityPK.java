package bg.duosoft.bpo.patent_classification.be.domain.entity.ipc;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Embeddable
public class IPCEntityPK implements Serializable {

    @Column(name = "IPC_EDITION_CODE")
    private String ipcEditionCode;

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

    @Override
    public String toString() {
        return ipcEditionCode + ' ' + ipcSectionCode + ipcClassCode + ipcSubclassCode + ipcGroupCode + ipcSubgroupCode;
    }
}
