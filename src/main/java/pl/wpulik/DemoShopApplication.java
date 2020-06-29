package pl.wpulik;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import pl.wpulik.model.Category;
import pl.wpulik.model.Gender;
import pl.wpulik.model.Order;
import pl.wpulik.model.Picture;
import pl.wpulik.model.Producer;
import pl.wpulik.model.Product;
import pl.wpulik.model.Shipment;
import pl.wpulik.model.User;
import pl.wpulik.service.CategoryService;
import pl.wpulik.service.OrderService;
import pl.wpulik.service.ProducerService;
import pl.wpulik.service.ProductService;
import pl.wpulik.service.UserService;
import pl.wpulik.service.ShipmentService;

@SpringBootApplication
public class DemoShopApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(DemoShopApplication.class);
		
		ProductService productService = ctx.getBean(ProductService.class);
		ProducerService producerService = ctx.getBean(ProducerService.class);
		UserService userService = ctx.getBean(UserService.class);
		ShipmentService shipmentService = ctx.getBean(ShipmentService.class);
		OrderService orderService = ctx.getBean(OrderService.class);
		CategoryService categoryService = ctx.getBean(CategoryService.class);
		
		List<Shipment> maxiShip = new ArrayList<>();
		List<Shipment> mediumShip = new ArrayList<>();
		
		Shipment shipment = shipmentService.getById(2L);
		Shipment shipment1 = shipmentService.getById(3L);
		Shipment shipment2 = shipmentService.getById(4L);
		Shipment shipment3 = shipmentService.getById(5L);
		
		Category category = categoryService.getById(3L);
		List<Category> reels = new ArrayList<>();
		reels.add(category);
		
		maxiShip.add(shipment);
		maxiShip.add(shipment1);
		mediumShip.add(shipment2);
		mediumShip.add(shipment3);
		
		shipmentService.addCategoryToShipment(4L, reels);
		shipmentService.addCategoryToShipment(5L, reels);
		
		ctx.close();
	}

}
