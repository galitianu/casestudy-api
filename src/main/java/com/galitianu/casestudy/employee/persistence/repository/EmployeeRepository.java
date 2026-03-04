package com.galitianu.casestudy.employee.persistence.repository;

import com.galitianu.casestudy.base.persistence.repository.BaseRepository;
import com.galitianu.casestudy.employee.persistence.entity.EmployeeEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends BaseRepository<EmployeeEntity> {

}
