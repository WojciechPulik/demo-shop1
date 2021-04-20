package pl.wpulik.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import pl.wpulik.model.Product;
import pl.wpulik.model.Shipment;

@Service
public class ShipmentService {
	
	private Map<String, Shipment> shipMap = new HashMap<>();

	public List<Shipment> orderShipment(List<Product> orderProducts){
		List<Shipment> resultShipments = new ArrayList<>();
        orderProducts.forEach(this::setShipMap);
        shipmentToRemoveFromOption(orderProducts).forEach(shipMap::remove);
        shipMap.values().forEach(resultShipments::add);
        return resultShipments;
	}
	
	private  void setShipMap(Product product){
        product.getShipments().forEach(this::putIntoShipMap);
    }
	
	private void putIntoShipMap(Shipment sh){
        String supplier = sh.getSupplier();
        var containsSupplier = shipMap.containsKey(supplier);
        shipMap.put(supplier, containsSupplier ?
                ((shipMap.get(supplier).getMaxWeight() < sh.getMaxWeight()) ?
                        sh : shipMap.get(supplier)) : sh);
    }
	
	private Set<String> shipmentToRemoveFromOption(List<Product> orderProducts){
        Set<String> resultSet = new HashSet<>();
        for(Product p : orderProducts){
            for(Shipment sh : shipMap.values()){
                if(!containsSupplier(p.getShipments(), sh)){
                	resultSet.add(sh.getSupplier());
                }
            }
        }
        return resultSet;
    }
	
	private static Boolean containsSupplier(List<Shipment> shipments, Shipment shipment){
        return !shipments.stream()
                .map(s -> s.getSupplier())
                .filter(shipment.getSupplier()::equals)
                .collect(Collectors.toSet()).isEmpty();
    }
	
}
