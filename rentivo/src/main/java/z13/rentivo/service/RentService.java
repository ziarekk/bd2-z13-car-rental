package z13.rentivo.service;

import org.hibernate.exception.GenericJDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
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
    @PersistenceContext
    private final EntityManager entityManager;

    public RentService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void rentCar(Integer clientId, Integer carId) {
        try {
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("rent_car");
            query.registerStoredProcedureParameter("p_client_id", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_car_id", Integer.class, ParameterMode.IN);
            query.setParameter("p_client_id", clientId);
            query.setParameter("p_car_id", carId);
            boolean result = query.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }
}


