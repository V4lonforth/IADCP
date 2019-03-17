package com.iad.courseProject.data.repositories;

import com.iad.courseProject.data.entities.Sector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SectorRepository extends JpaRepository<Sector, Long> {
}
