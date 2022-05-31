package de.tum.flightsystem.repositories;

import de.tum.flightsystem.models.Flight;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FlightRepository extends MongoRepository<Flight, String> {
}
