package org.com.iurdapp.dto;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
public class AddressDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String addressType;
    private String number;
    private String district;
    private String city;
}
