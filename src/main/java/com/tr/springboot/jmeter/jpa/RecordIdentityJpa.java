package com.tr.springboot.jmeter.jpa;

import com.tr.springboot.jmeter.entity.RecordIdentity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: TR
 */
public interface RecordIdentityJpa extends JpaRepository<RecordIdentity, Integer> {
}
