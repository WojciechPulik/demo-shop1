package pl.wpulik.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.wpulik.model.Producer;
import pl.wpulik.repository.ProducerRepository;

@Transactional
@Service
public class ProducerRepoService {
	
	private ProducerRepository producerRepository;
	
	@Autowired
	public ProducerRepoService (ProducerRepository producerRepository) {
		this.producerRepository = producerRepository;
	}
	
	public ProducerRepoService () {}
	
	public Producer addProducer(Producer producer) {
		producerRepository.save(producer);
		return producer;
	}
	
	public Producer getById(Long id) {
		return producerRepository.findById(id).get();
	}
	
	public List<Producer> getAllProducers(){
		return producerRepository.findAll();
	}
}
