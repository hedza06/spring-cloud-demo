package com.hedza06.springcloud.user.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "user")
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 896289853448210489L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "full_name")
    private String fullName;

    @Column
    private String address;

    @Column
    private String email;

    @ToString.Exclude
    @OneToMany(mappedBy = "user")
    private List<UserProduct> products = new ArrayList<>();
}
