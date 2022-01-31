package org.com.iurdapp.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class MemberDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    @NotBlank
    @Length(max = 255)
    private String name;
    private LocalDate dateOfBirth;
    @Length(max = 11)
    private String cpf;
    @Length(max = 4)
    private String yearOfEntry;
    private AddressDTO address;
}
