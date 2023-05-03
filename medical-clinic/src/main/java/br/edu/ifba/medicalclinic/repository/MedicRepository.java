package br.edu.ifba.medicalclinic.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifba.medicalclinic.entity.Medic;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicRepository extends JpaRepository<Medic, Long>{
    public List<Medic> findByNameContains(String name, Pageable pageable);
}
