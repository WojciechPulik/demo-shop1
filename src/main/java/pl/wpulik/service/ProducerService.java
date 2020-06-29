package pl.wpulik.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.wpulik.model.Producer;
import pl.wpulik.repository.ProducerRepository;

@Transactional
@Service
public class ProducerService {
	
	private ProducerRepository producerRepository;
	
	@Autowired
	public ProducerService (ProducerRepository producerRepository) {
		this.producerRepository = producerRepository;
	}
	
	public Producer addProducer(Producer producer) {
		producerRepository.save(producer);
		return producer;
	}
	
	public Producer getById(Long id) {
		return producerRepository.findById(id).get();
	}
}
