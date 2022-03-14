package com.hedza06.springcloud.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO implements Serializable {

    private Integer id;
    private String name;
    private String description;
    private String sourceIdentifier;

}
