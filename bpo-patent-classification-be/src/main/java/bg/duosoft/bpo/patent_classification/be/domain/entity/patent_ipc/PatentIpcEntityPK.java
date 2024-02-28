package bg.duosoft.bpo.patent_classification.be.domain.entity.patent_ipc;

import bg.duosoft.bpo.patent_classification.be.domain.entity.ipc.IPCClassEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Embeddable
public class PatentIpcEntityPK implements Serializable {

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "IPC_EDITION_CODE", referencedColumnName = "IPC_EDITION_CODE"),
            @JoinColumn(name = "IPC_SECTION_CODE", referencedColumnName = "IPC_SECTION_CODE"),
            @JoinColumn(name = "IPC_CLASS_CODE", referencedColumnName = "IPC_CLASS_CODE"),
            @JoinColumn(name = "IPC_SUBCLASS_CODE", referencedColumnName = "IPC_SUBCLASS_CODE"),
            @JoinColumn(name = "IPC_GROUP_CODE", referencedColumnName = "IPC_GROUP_CODE"),
            @JoinColumn(name = "IPC_SUBGROUP_CODE", referencedColumnName = "IPC_SUBGROUP_CODE")
    })
    private IPCClassEntity ipc;

    @Column(name = "IPC_QUALIFICATION_CODE")
    private String ipcQualificationCode;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "FILE_SEQ", referencedColumnName = "FILE_SEQ"),
            @JoinColumn(name = "FILE_TYP", referencedColumnName = "FILE_TYP"),
            @JoinColumn(name = "FILE_SER", referencedColumnName = "FILE_SER"),
            @JoinColumn(name = "FILE_NBR", referencedColumnName = "FILE_NBR")
    })
    private PatentEntity patent;

}
