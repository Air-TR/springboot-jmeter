package com.tr.springboot.jmeter.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @Author: TR
 */
@Data
@Entity
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class RecordUuid {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;

    private Long currentTimeMillis;

}
