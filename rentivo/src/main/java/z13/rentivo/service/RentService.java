package z13.rentivo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.StoredProcedureQuery;

@NamedStoredProcedureQuery(
        name = "rentCar",
        procedureName = "rent_car",
        parameters = {
            @StoredProcedureParameter(name = "p_client_id", mode = ParameterMode.IN, type = Integer.class),
            @StoredProcedureParameter(name = "p_car_id", mode = ParameterMode.IN, type = Integer.class)
        }
    )
@Service
public class RentService {
    private EntityManagerFactory emf;

    @Autowired
    public RentService() {
        emf = Persistence.createEntityManagerFactory("rentivo");
    }
    
    public void rentCar(Long clientId, Long carId) {
        EntityManager em = emf.createEntityManager();
        try {
            StoredProcedureQuery query = em.createNamedStoredProcedureQuery("rentCar");
            query.setParameter("p_client_id", clientId);
            query.setParameter("p_car_id", carId);
            query.execute();
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {
        RentService rentalService = new RentService();
        rentalService.rentCar(1L, 1L);
    }
}

