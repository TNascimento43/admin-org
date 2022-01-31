package org.com.iurdapp.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class GroupDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Boolean isActive;
    @NotBlank
    private String name;
    @NotNull
    private Long memberId;
    private String memberName;
}
