package com.avirantenterprises.infocollector.repository.business;

import com.avirantenterprises.infocollector.model.business.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageFile, Long> {
    // Spring Data JPA automatically implements methods like save(), findAll(), and more
}
