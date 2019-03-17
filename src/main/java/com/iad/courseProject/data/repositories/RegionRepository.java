package com.iad.courseProject.data.repositories;

import com.iad.courseProject.data.entities.Region;
import com.iad.courseProject.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region, Long> {
    Region findDistinctByCadastralNumber(String cadastralNumber);
}
