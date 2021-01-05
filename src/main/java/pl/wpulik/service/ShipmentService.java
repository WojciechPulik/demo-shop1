package pl.wpulik.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import pl.wpulik.model.Product;
import pl.wpulik.model.Shipment;

@Service
public class ShipmentService {

	public List<Shipment> orderShipment(List<Product> orderProducts){
		Set<Product> setProd = new HashSet<>(orderProducts);
		Set<String> suppliers = new HashSet<>();
		Map<String, Shipment> shipMap = new HashMap<>();
		List<Shipment> resultList = new ArrayList<>();
		//adds every shipment supplier to Set suppliers:
		//creates a Map with pairs: supplier - shipment with the biggest max weight:
		boolean containsKey = false;
		boolean isBigger = false;
		for(Product p: setProd) {
			for(Shipment sh: p.getShipments()) {
				suppliers.add(sh.getSupplier());
				containsKey = shipMap.containsKey(sh.getSupplier());
				if(shipMap.get(sh.getSupplier())!=null) {
					isBigger = sh.getMaxWeight() > (shipMap.get(sh.getSupplier())).getMaxWeight();
				}
				shipMap.put(sh.getSupplier(), containsKey ? (isBigger ?	sh : shipMap.get(sh.getSupplier()) ) : sh);					
			}
		}	
		//creates a Set of Sets with possible suppliers for each product:	
		Set<Set<String>> suppliersProdSet = productShipmentSuppliers(setProd);		
		//removes shipments witch cannot be applied to every product:
		for(String str : suppliers) {
			for(Set<String> set : suppliersProdSet) {
				if(!set.contains(str)) {
					shipMap.remove(str);
				}
			}
		}	
		//result list of shipments possible for this order:
		for(String str : shipMap.keySet()) {
			resultList.add(shipMap.get(str));
		}
		return resultList;
	}	
	
	private Set<Set<String>> productShipmentSuppliers (Set<Product> setProd){
		Set<String> prodSup = new HashSet<>();
		Set<Set<String>> suppliersProdSet = new HashSet<>();
		for(Product p : setProd) {
			for(Shipment sh : p.getShipments()) {
				prodSup.add(sh.getSupplier());
			}
			suppliersProdSet.add(prodSup);
			prodSup = new HashSet<>();
		}
		return suppliersProdSet;
	}
	
}
