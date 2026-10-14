package my.gov.imi.niisesali_demo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PassportAdminDTO extends PassportDTO {

    private int id; // Or String / UUID depending on your Entity primary key type
}