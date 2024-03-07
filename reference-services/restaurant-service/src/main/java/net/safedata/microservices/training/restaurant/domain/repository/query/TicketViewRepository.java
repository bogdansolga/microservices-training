package net.safedata.microservices.training.restaurant.domain.repository.query;

import net.safedata.microservices.training.restaurant.domain.model.query.TicketView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketViewRepository extends JpaRepository<TicketView, Long> {
}
