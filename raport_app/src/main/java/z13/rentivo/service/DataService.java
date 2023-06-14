package z13.rentivo.service;


import java.util.*;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
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

    public List<Car> getFictionalCars() {
        List<Car> carsList = new ArrayList<>();
        Car car1 = new Car(1l, 100, "WSI ŻABA", 2002, 20.21f, 21.37f, 0.9f , true, "petrol", 90f, "Yaris", "Toyota", 4, "Automatic", new Segment(1l,"D", 21f, 2f, 3f));
        Car car2 = new Car(2l, 2300, "WS 2137", 2008, 21.34f, 31.69f, 0.4f , false, "diesel", 65f, "Ford", "Focus", 4, "Manual", new Segment(1l,"D", 21f, 2f, 3f));
        carsList.add(car1);
        carsList.add(car2);

        return carsList;
    }

    public Number getSumBillAmount() {
        return Objects.requireNonNullElse(billRepository.getAmountSum(), 0);
    }

    public Number getAverageBillAmount(){
        return Objects.requireNonNullElse(billRepository.getAverageAmount(), 0);
    }

    public Number getPaidPaymentCount() {
        return Objects.requireNonNullElse(paymentRepository.getPaidCount(), 0);
    }

    public Number getPendingCount() {
        return Objects.requireNonNullElse(paymentRepository.getPendingCount(), 0);
    }

    public Number getDeclinedCount() {
        return Objects.requireNonNullElse(paymentRepository.getDeclinedCount(), 0);
    }

    public Long getNotPaidPaymentCount(){
        return paymentRepository.getCount() - paymentRepository.getPaidCount();
    }

    public Number getPaidPaymentAmount() {
        return Objects.requireNonNullElse(paymentRepository.getPaidAmount(), 0);
    }

    public Number getPendingPaymentAmount() {
        return Objects.requireNonNullElse(paymentRepository.getPendingAmount(), 0);
    }

    public Number getDeclinedPaymentAmount() {
        return Objects.requireNonNullElse(paymentRepository.getDeclinedAmount(), 0);
    }

    public Number getPaymentCountByMonth(Integer month, String status) {
        return Objects.requireNonNullElse(paymentRepository.getCountByMonth(month, status), 0);
    }

    public Number getPaymentAmountByMonth(Integer month, String status) {
        return Objects.requireNonNullElse(paymentRepository.getAmountByMonth(month, status), 0);
    }

    public Client getMostActiveClient()
    {
        long maxRentals = 0;
        Client bestClient = null;
        for (Client client: clientRepository.findAll()) {
            Long numRentals = rentalRepository.countByClient(client);
            if(numRentals >= maxRentals){
                maxRentals = numRentals;
                bestClient = client;
            }
        }
        return bestClient;
    }

    public Long countClientRentals(Client client){
        return rentalRepository.countByClient(client);
    }

    public Long countComments(){return commentRepository.count();}


    public double calculateRentalAmount(Rental rental)
    {
        RentalStart start = rental.getRentalStart();
        RentalEnd end = rental.getRentalEnd();
        Segment segment = rental.getCar().getSegment();
        float mileage = end.getEndMileage() - start.getStartMileage();

        long diffMilliseconds = end.getEndTime().getTime() - start.getStartTime().getTime();
        long diffHours = TimeUnit.MILLISECONDS.toHours(diffMilliseconds);
        return  segment.getRentalFee() + segment.getHourRate() * diffHours + segment.getKmRate() * mileage;
    }
    public Double averageDiscountAmount() {
        Double sumDiscounts = 0.0;
        List<Discount> discounts = discountRepository.findAll();
        for (Discount discount : discounts) {
            Rental rental = rentalRepository.findByRentalId(discount.getRental().getRentalId()).get(0);
            Double discountAmount = calculateRentalAmount(rental) * discount.getPercent() / 100;
            sumDiscounts += discountAmount;
        }
        return sumDiscounts / discounts.size();
    }
}