package com.avirantenterprises.infocollector.repository;
import com.avirantenterprises.infocollector.model.Data;
import org.springframework.data.jpa.repository.JpaRepository;
public interface DataRepository extends JpaRepository<Data, Long> {
}
