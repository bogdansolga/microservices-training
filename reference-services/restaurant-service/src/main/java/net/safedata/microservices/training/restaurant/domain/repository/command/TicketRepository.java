package net.safedata.microservices.training.restaurant.domain.repository.command;

import net.safedata.microservices.training.restaurant.domain.model.command.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
