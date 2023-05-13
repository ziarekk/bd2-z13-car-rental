package z13.rentivo.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import z13.rentivo.entities.Car;

@Transactional @Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    @Query
    List<Car> findByCarId(Long carId);

    @Query
    List<Car> findByMileage(Integer mileage);

    @Query
    List<Car> findByRegistrationNumber(String registrationNumber);

    @Query
    List<Car> findByProductionYear(Integer productionYear);

    @Query
    List<Car> findByLongitude(Float longitude);

    @Query
    List<Car> findByLatitude(Float latitude);

    @Query
    List<Car> findByFuelLevel(Float fuelLevel);

    @Query
    List<Car> findByIsAvailableForRent(Boolean isAvailableForRent);

    @Query
    List<Car> findByFuelType(String fuelType);

    @Query
    List<Car> findByFuelCapacity(Float fuelCapacity);

    @Query
    List<Car> findByModel(String model);

    @Query
    List<Car> findByBrand(String brand);

    @Query
    List<Car> findBySeats(Integer seats);

    @Query
    List<Car> findByTransmission(String transmission);

    @Modifying @Query(value = "INSERT INTO cars (mileage, registration_number, production_year, longitude, latitude, fuel_level, is_available_for_rent, fuel_type, fuel_capacity, model, brand, seats, transmission, segment_id) VALUES (:mileage, :registration_number, :production_year, :longitude, :latitude, :fuelLevel, :is_available_for_rent, :fuel_type, :fuel_capacity, :model, :brand, :seats, :transmission, :segment_id)", nativeQuery = true)
    void insertCar(@Param("mileage") Integer mileage, 
                   @Param("registration_number") String registrationNumber, 
                   @Param("production_year") Integer productionYear, 
                   @Param("longitude") Float longitude, 
                   @Param("latitude") Float latitude, 
                   @Param("fuel_level") Float fuelLevel, 
                   @Param("is_available_for_rent") Boolean isAvailableForRent, 
                   @Param("fuel_type") String fuelType, 
                   @Param("fuel_capacity") Float fuelCapacity, 
                   @Param("model") String model, 
                   @Param("brand") String brand, 
                   @Param("seats") Integer seats, 
                   @Param("transmission") String transmission, 
                   @Param("segment_id") Long segmentId);
}