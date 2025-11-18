package net.safedata.microservices.training.restaurant.domain.model.query;

import net.safedata.microservices.training.restaurant.domain.model.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "ticket_view")
public class TicketView extends AbstractEntity {
}
