package com.tr.springboot.jmeter.jpa;

import com.tr.springboot.jmeter.entity.RecordUuid;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: TR
 */
public interface RecordUuidJpa extends JpaRepository<RecordUuid, String> {
}
