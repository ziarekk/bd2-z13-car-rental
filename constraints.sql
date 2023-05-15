alter table bills
    add constraint check_amount
    check (amount >= 0);

alter table cars
    add constraint check_transmission
    check (transmission in ('manualna', 'automatyczna'));
	
alter table cars
    add constraint check_seats
    check (seats >= 1);
	
alter table cars
    add constraint check_fuel_type
    check (fuel_type in ('benzyna', 'diesel', 'LPG', 'hybryda', 'elektryczny'));
	
alter table cars
    add constraint check_fuel_capacity
    check (fuel_capacity > 0);
	
alter table cars
    add constraint check_fuel_level
    check (fuel_level >= 0);
	
alter table cars
    add constraint check_mileage
    check (mileage >= 0);

alter table clients
    add constraint check_gender
    check (gender in ('K', 'M', 'O'));

alter table discounts
    add constraint check_percent
    check (percent >= 0 and percent <= 100);

alter table payments
    add constraint check_status
    check (status in ('przyjeta', 'oczekujaca', 'odrzucona'));

alter table penalties
    add constraint check_amount
    check (amount > 0);

alter table rental_end
    add constraint check_end_mileage
    check (end_mileage >= 0);

alter table rental_start
    add constraint check_start_mileage
    check (start_mileage >= 0);

alter table segments
    add constraint check_rental_fee
    check (rental_fee >= 0);

alter table segments
    add constraint check_km_rate
    check (km_rate >= 0);

alter table segments
    add constraint check_hour_rate
    check (hour_rate >= 0);
	
alter table user_roles
    add constraint check_name
    check (name in ('normal', 'employee', 'vip', 'superuser'));