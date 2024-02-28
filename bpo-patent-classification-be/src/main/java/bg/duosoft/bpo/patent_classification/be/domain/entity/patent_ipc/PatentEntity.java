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

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Slf4j
@Table(name = "IP_PATENT", schema = "IPASPROD")
public class PatentEntity implements Serializable {

    @EmbeddedId
    private PatentEntityPK pk;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "ENGLISH_TITLE")
    private String englishTitle;

    @Column(name = "REGISTRATION_NBR")
    private Long registrationNbr;

}