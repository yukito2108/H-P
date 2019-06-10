package com.snezana.doctorpractice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.snezana.doctorpractice.models.RecordType;

public interface RecordTypeRepository extends JpaRepository<RecordType, Long>{
	
	RecordType findByComponentCode(String componentCode);

}
