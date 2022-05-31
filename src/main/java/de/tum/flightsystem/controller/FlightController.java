package de.tum.flightsystem.controller;

import de.tum.flightsystem.models.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.FixedKeySet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import de.tum.flightsystem.repositories.FlightRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class FlightController {
    @Autowired
    FlightRepository flightRepository;

    @GetMapping(value = "/flights/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Flight>> getFlights(@PathVariable int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Flight> allProducts = flightRepository.findAll(pageable);
        return new ResponseEntity<>(allProducts.getContent(), HttpStatus.OK);
    }

    @GetMapping(value = "/flight/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Flight> getFlightsByID(@PathVariable String id) {

        Optional<Flight> flightOptional = flightRepository.findById(id);
        if (flightOptional.isPresent()) {
            return new ResponseEntity<>(flightOptional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/flight/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Flight> deleteFlightsByID(@PathVariable String id) {

        flightRepository.deleteById(id);
        Optional<Flight> flightOptional = flightRepository.findById(id);

        if (flightOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/flight", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Flight> createFlight(@RequestBody Flight flight) {
        try {
            Flight newFlight = flightRepository.save(flight);
            return new ResponseEntity<>(newFlight, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/flight/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Flight> updateFlight(@RequestBody Flight flight, @PathVariable String id) {
        try {
            Optional<Flight> flightOptional = flightRepository.findById(id);
            if (flightOptional.isPresent()) {
                Flight oldFlight = flightOptional.get();
                if (flight.getFirstName() != null && !StringUtils.isEmpty(flight.getFirstName())) {
                    oldFlight.setFirstName(flight.getFirstName());
                }
                if (flight.getLastName() != null && !StringUtils.isEmpty(flight.getLastName())) {
                    oldFlight.setLastName(flight.getLastName());
                }
                return new ResponseEntity<>(flightRepository.save(oldFlight), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
