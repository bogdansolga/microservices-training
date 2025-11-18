package net.safedata.microservices.training.restaurant.domain.model.command;

import net.safedata.microservices.training.restaurant.domain.model.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "ticket")
public class Ticket extends AbstractEntity {
}
