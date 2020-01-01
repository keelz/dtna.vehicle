package com.dtna.vehicle.repository;

import com.dtna.vehicle.domain.Vehicle;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class VehicleRepository implements CommonRepository<Vehicle> {

    private Map<String, Vehicle> vehicles = new HashMap<>();

    private Comparator<Map.Entry<String, Vehicle>> entryComparator = (Map.Entry<String, Vehicle> o1, Map.Entry<String, Vehicle> o2) -> {
        LocalDateTime o1Created = o1.getValue().getCreated();
        LocalDateTime o2Created = o2.getValue().getCreated();
        return o1Created.compareTo(o2Created);
    };

    @Override
    public Vehicle save(Vehicle domain) {
        vehicles.put(domain.getId(), domain);
        return vehicles.get(domain.getId());
    }

    @Override
    public Iterable<Vehicle> save(Collection<Vehicle> domains) {
        domains.forEach(this::save);
        return findAll();
    }

    @Override
    public void delete(Vehicle domain) {
        vehicles.remove(domain.getId());
    }

    @Override
    public Vehicle findById(String id) {
        return vehicles.get(id);
    }

    @Override
    public Iterable<Vehicle> findAll() {
        return vehicles
                .entrySet()
                .stream()
                .sorted(entryComparator)
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
}
