package bg.duosoft.bpo.patent_classification.be.domain.entity.log;

import jakarta.persistence.*;
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
@Table(name = "PATENT_CLASSIFICATION_LOG", schema = "EXT_CORE")
public class PatentClassificationLogEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "OPERATION")
    private String operation;

    @Column(name = "OPERATION_STATUS")
    private String operationStatus;

    @Column(name = "AFFECTED_TABLE")
    private String affectedTable;

    @Column(name = "OLD_DATA")
    private String oldData;

    @Column(name = "NEW_DATA")
    private String newData;

    @Column(name = "RESPONSIBLE_USER")
    private String responsibleUser;

    @Column(name = "NOTE")
    private String note;

}