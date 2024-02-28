package bg.duosoft.bpo.patent_classification.be.domain.entity.patent_ipc;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Embeddable
public class PatentEntityPK implements Serializable {

  @Column(name = "FILE_SEQ")
  private String fileSeq;

  @Column(name = "FILE_TYP")
  private String fileTyp;

  @Column(name = "FILE_SER")
  private Integer fileSer;

  @Column(name = "FILE_NBR")
  private Long fileNbr;

  @Override
  public String toString() {
    return fileSeq + '/' + fileTyp + '/' + fileSer + '/' + fileNbr;
  }

}
