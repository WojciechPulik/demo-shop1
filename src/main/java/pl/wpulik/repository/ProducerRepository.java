package pl.wpulik.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import pl.wpulik.model.Producer;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, Long>{

}
