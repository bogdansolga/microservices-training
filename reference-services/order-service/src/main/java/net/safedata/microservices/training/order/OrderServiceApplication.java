package net.safedata.microservices.training.order;

import net.safedata.microservices.training.order.adapters.MessageCreator;
import net.safedata.microservices.training.order.channels.InboundChannels;
import net.safedata.microservices.training.order.message.CreateOrderMessage;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

	@Bean
	public ApplicationRunner publishTestCreateOrderMessage(final InboundChannels inboundChannels) {
		return args -> inboundChannels.createOrder()
									  .send(MessageCreator.create(
				new CreateOrderMessage(547L, "An useful item", 324L, 834L)));
	}
}
