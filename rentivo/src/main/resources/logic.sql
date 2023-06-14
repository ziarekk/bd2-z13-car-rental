CREATE OR REPLACE FUNCTION rent_car(
    p_client_id INT,
    p_car_id INT
)
RETURNS BOOLEAN -- Add the return type
    LANGUAGE plpgsql
AS $$
DECLARE
    v_start_id INT;
    v_rental_id INT;
    v_bill_id INT;
    v_payment_id INT;
    v_start_mileage INT;
    v_is_verified BOOLEAN;
    v_is_available BOOLEAN;
BEGIN

    -- Check if the car is available
    SELECT is_available_for_rent INTO v_is_available
    FROM cars
    WHERE car_id = p_car_id;

    IF NOT v_is_available THEN
        RETURN FALSE;
    END IF;

    -- Check if the user is verified
    SELECT is_verified INTO v_is_verified
    FROM clients
    WHERE client_id = p_client_id;

    IF NOT v_is_verified THEN
        RETURN FALSE;
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

    -- Return true to indicate successful completion
    RETURN TRUE;
END;
$$;


CREATE OR REPLACE FUNCTION return_car (
    p_client_id INT,
    p_car_id INT
)
RETURNS BOOLEAN
    LANGUAGE plpgsql
AS $$
DECLARE
    v_rental_id INT;
    v_end_id INT;
    v_end_mileage INT;
    v_start_mileage INT;
    v_rental_duration INTERVAL;
    v_rental_cost FLOAT;
    v_km_cost FLOAT;
    v_hour_cost FLOAT;
    v_initial_fee FLOAT;
    v_discount FLOAT;
    v_penalty FLOAT;

BEGIN
    -- Retrieve rental_id from rentals record
    SELECT rental_id INTO v_rental_id
    FROM rentals
    WHERE client_id = p_client_id
    AND car_id = p_car_id
    AND end_id IS NULL;

    -- Retrieve start_mileage from rental_start record
    SELECT start_mileage INTO v_start_mileage
    FROM rental_start
    WHERE start_id = (SELECT start_id FROM rentals WHERE rental_id = v_rental_id);

    -- Retrieve end_mileage from car record
    SELECT mileage INTO v_end_mileage
    FROM cars
    WHERE car_id = p_car_id;

    -- Calculate rental_duration
    SELECT CURRENT_TIMESTAMP - (SELECT start_time FROM rental_start WHERE start_id = (SELECT start_id FROM rentals WHERE rental_id = v_rental_id))
    INTO v_rental_duration;

    -- Get discount
    SELECT percent INTO v_discount
    FROM discounts
    WHERE discount_id = (SELECT discount_id FROM rentals WHERE rental_id = v_rental_id);

    -- Get penalty
    SELECT amount INTO v_penalty
    FROM penalties
    WHERE penalty_id = (SELECT penalty_id FROM rentals WHERE rental_id = v_rental_id);

    -- Calculate rental_cost
    SELECT hour_rate INTO v_hour_cost
    FROM segments
    WHERE segment_id = (SELECT segment_id FROM cars WHERE car_id = p_car_id);

    SELECT km_rate INTO v_km_cost
    FROM segments
    WHERE segment_id = (SELECT segment_id FROM cars WHERE car_id = p_car_id);

    SELECT rental_fee INTO v_initial_fee
    FROM segments
    WHERE segment_id = (SELECT segment_id FROM cars WHERE car_id = p_car_id);

    SELECT v_discount * ((v_end_mileage - v_start_mileage) * v_km_cost + EXTRACT(EPOCH FROM v_rental_duration) / 3600 * v_hour_cost + v_initial_fee) + v_penalty INTO v_rental_cost;

    -- Insert rental_end record
    INSERT INTO rental_end(end_mileage, end_time)
    VALUES (v_end_mileage, CURRENT_TIMESTAMP)
    RETURNING end_id INTO v_end_id;

    -- Update rentals record
    UPDATE rentals
    SET end_id = v_end_id
    WHERE rental_id = v_rental_id;

    -- Update cars record
    UPDATE cars
    SET is_available_for_rent = TRUE,
        mileage = v_end_mileage
    WHERE car_id = p_car_id;

    -- Print confirmation message
    RAISE NOTICE 'Car returned successfully. Rental ID: %', v_rental_id;

    RETURN TRUE;
END;
$$;


