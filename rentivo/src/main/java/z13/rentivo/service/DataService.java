package z13.rentivo.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import z13.rentivo.repositories.*;
import z13.rentivo.entities.*;

@Service
public class DataService {
    
    private final BillRepository billRepository;
    private final CarRepository carRepository;
    private final ClientRepository clientRepository;
    private final CommentRepository commentRepository;
    private final DiscountRepository discountRepository;
    private final DriverLicenceRepository driverLicenceRepository;
    private final LocationHistoryRepository locationHistoryRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentTypeRepository paymentTypeRepository;
    private final PenaltyRepository penaltyRepository;
    private final RentalEndRepository rentalEndRepository;
    private final RentalRepository rentalRepository;
    private final RentalStartRepository rentalStartRepository;
    private final SegmentRepository segmentRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;


    @Autowired
    public DataService(BillRepository billRepository,
                       CarRepository carRepository,
                       ClientRepository clientRepository,
                       CommentRepository commentRepository,
                       DiscountRepository discountRepository,
                       DriverLicenceRepository driverLicenceRepository,
                       LocationHistoryRepository locationHistoryRepository,
                       PaymentRepository paymentRepository,
                       PaymentTypeRepository paymentTypeRepository,
                       PenaltyRepository penaltyRepository,
                       RentalEndRepository rentalEndRepository,
                       RentalRepository rentalRepository,
                       RentalStartRepository rentalStartRepository,
                       SegmentRepository segmentRepository,
                       UserRepository userRepository,
                       UserRoleRepository userRoleRepository) {
                           this.billRepository = billRepository;
                           this.carRepository = carRepository;
                           this.clientRepository = clientRepository;
                           this.commentRepository = commentRepository;
                           this.discountRepository = discountRepository;
                           this.driverLicenceRepository = driverLicenceRepository;
                           this.locationHistoryRepository = locationHistoryRepository;
                           this.paymentRepository = paymentRepository;
                           this.paymentTypeRepository = paymentTypeRepository;
                           this.penaltyRepository = penaltyRepository;
                           this.rentalEndRepository = rentalEndRepository;
                           this.rentalRepository = rentalRepository;
                           this.rentalStartRepository = rentalStartRepository;
                           this.segmentRepository = segmentRepository;
                           this.userRepository = userRepository;
                           this.userRoleRepository = userRoleRepository;
                       }

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public List<Discount> getAllDiscounts() {
        return discountRepository.findAll();
    }

    public List<DriverLicence> getAllDriverLicences() {
        return driverLicenceRepository.findAll();
    }

    public List<LocationHistory> getAllLocationHistories() {
        return locationHistoryRepository.findAll();
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public List<PaymentType> getAllPaymentTypes() {
        return paymentTypeRepository.findAll();
    }

    public List<Penalty> getAllPenalties() {
        return penaltyRepository.findAll();
    }

    public List<RentalEnd> getAllRentalEnds() {
        return rentalEndRepository.findAll();
    }

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public List<RentalStart> getAllRentalStarts() {
        return rentalStartRepository.findAll();
    }

    public List<Segment> getAllSegments() {
        return segmentRepository.findAll();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<UserRole> getAllUserRoles() {
        return userRoleRepository.findAll();
    }

    public void addBill(Float amount, Date dateDue, Long paymentId) {
        billRepository.insertBill(amount, dateDue, paymentId);
    }

    public void addSegment(String name, Float rentalFee, Float kmRate, Float hourRate) {
        segmentRepository.insertSegment(name, rentalFee, kmRate, hourRate);
    }

    public void addCar(Integer mileage, String registrationNumber, Integer productionYear, Float longitude,
                       Float latitude, Float fuelLevel, Boolean isAvailableForRent, String fuelType, Float fuelCapacity,
                       String model, String brand, Integer seats, String transmission, Long segmentId) {

        carRepository.insertCar(mileage, registrationNumber, productionYear, longitude, latitude, fuelLevel,
                        isAvailableForRent, fuelType, fuelCapacity, model, brand, seats, transmission, segmentId);
    }

    public void addUser(String login, String password, String email, Long clientId, Long roleId) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
         
        userRepository.insertUser(login, hashedPassword, email, clientId, roleId);
    }

    public void addUser(String login, String password, String email, Long roleId) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
         
        userRepository.insertUser(login, hashedPassword, email, roleId);
    }

    public List<Car> getFictionalCars() {
        List<Car> carsList = new ArrayList<>();
        Car car1 = new Car(1l, 100, "WSI ŻABA", 2002, 20.21f, 21.37f, 0.9f , true, "petrol", 90f, "Yaris", "Toyota", 4, "Automatic", new Segment(1l,"D", 21f, 2f, 3f));
        Car car2 = new Car(2l, 2300, "WS 2137", 2008, 21.34f, 31.69f, 0.4f , false, "diesel", 65f, "Ford", "Focus", 4, "Manual", new Segment(1l,"D", 21f, 2f, 3f));
        carsList.add(car1);
        carsList.add(car2);

        return carsList;
    }

    public List<User> getUserByLogin(String name) {
        return userRepository.findByLogin(name);
    }

    public List<Rental> getRentalsByUser(String login){
        return rentalRepository.findByUser(login);
    }

    public List<Car> getCarById(Car car){
        return carRepository.findByCarId(car.getCarId());
    }

}