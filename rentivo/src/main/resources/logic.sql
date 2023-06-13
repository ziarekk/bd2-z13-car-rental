CREATE OR REPLACE PROCEDURE rent_car(
    p_client_id INT,
    p_car_id INT
)
LANGUAGE plpgsql
AS $$
DECLARE
    v_start_id INT;
    v_rental_id INT;
    v_bill_id INT;
    v_payment_id INT;
    v_start_mileage INT;
    v_is_verified BOOLEAN;
BEGIN
    -- Check if the user is verified
    SELECT is_verified INTO v_is_verified
    FROM clients
    WHERE client_id = p_client_id;

    IF NOT v_is_verified THEN
        RAISE EXCEPTION 'Cannot rent a car. User is not verified.';
    END IF;

    -- Retrieve start mileage from the car record
    SELECT mileage INTO v_start_mileage
    FROM cars
    WHERE car_id = p_car_id;

    -- Insert rental_start record
    INSERT INTO rental_start(start_mileage, start_time)
    VALUES (v_start_mileage, CURRENT_TIMESTAMP)
    RETURNING start_id INTO v_start_id;

    -- Insert rentals record
    INSERT INTO rentals(car_id, client_id, start_id)
    VALUES (p_car_id, p_client_id, v_start_id)
    RETURNING rental_id INTO v_rental_id;

    -- Update cars availability
    UPDATE cars
    SET is_available_for_rent = FALSE
    WHERE car_id = p_car_id;

    -- Print confirmation message
    RAISE NOTICE 'Car rented successfully. Rental ID: %', v_rental_id;

EXCEPTION
    WHEN OTHERS THEN
        -- Rollback the transaction
        ROLLBACK;
        -- Print error message
        RAISE EXCEPTION 'An error occurred while renting the car: %', SQLERRM;
END;
$$;

CALL rent_car(1, 1);