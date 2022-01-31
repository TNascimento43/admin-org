package org.com.iurdapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class ResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String message;

    private String status;
}
