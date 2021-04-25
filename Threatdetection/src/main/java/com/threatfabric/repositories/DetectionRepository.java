package com.threatfabric.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threatfabric.entities.Detection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface DetectionRepository extends JpaRepository<Detection, Long> {


    List<Detection> findByTypeOfApp(String typeOfApp);
}
