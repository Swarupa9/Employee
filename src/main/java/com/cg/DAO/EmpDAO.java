package com.cg.DAO;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.EO.EmpEO;

@Repository
public interface EmpDAO extends JpaRepository<EmpEO, Long> {
	Optional<EmpEO> findById(Long id);
}

