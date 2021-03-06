package pl.wpulik.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.wpulik.model.Category;
import pl.wpulik.model.Shipment;
import pl.wpulik.repository.CategoryRepository;
import pl.wpulik.repository.ShipmentRepository;

@Transactional
@Service
public class ShipmentRepoService {
	
	private ShipmentRepository shipmentRepository;
	private CategoryRepository categoryRepository;
	
	@Autowired
	public ShipmentRepoService(ShipmentRepository shipmentRepository, CategoryRepository categoryRepository) {
		this.shipmentRepository = shipmentRepository;
		this.categoryRepository = categoryRepository;
	}
	
	public ShipmentRepoService() {}
	
	public Shipment addShipment(Shipment shipment) {
		shipmentRepository.save(shipment);
		return shipment;
	}
	
	public Shipment getById(Long id) {
		Shipment shipment = shipmentRepository.findById(id).get();
		return shipment;
	}
	
	public List<Shipment> getAllShipments(){
		return shipmentRepository.findAll();
	}
	/*
	 * To gówno coś nie działa:
	 */
	public void addShipmentsToCategory(Long categoryId, List<Shipment> shipments) {
		Category category = categoryRepository.findById(categoryId).get();
		for(Shipment shipm: shipments)
			category.addShipments(shipm);
		category = categoryRepository.save(category);
		System.out.println(category.toString());
	}
	/*
	 * Działa tak jak trza!
	 * No, może z tej list się zrezygnuje na rzecz pojedyńczego obiektu
	 */
	public void addCategoryToShipment(Long shipmentId, List<Category> categories) {
		Shipment shipment = shipmentRepository.findById(shipmentId).get();
		for(Category cat: categories)
			shipment.addCategories(cat);
		shipment = shipmentRepository.save(shipment);
	}
	
	public Shipment updateShipment(Shipment shipment) {
		return shipmentRepository.save(shipment);
	}
	
	
	
}
