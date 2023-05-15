create table bills (
    bill_id  bigserial not null,
    amount float4,
    date_due timestamp,
    payment_id int8,
    primary key (bill_id)
);

create table cars (
    car_id  bigserial not null,
    brand varchar(255),
    fuel_capacity float4,
    fuel_level float4,
    fuel_type varchar(255),
    is_available_for_rent boolean,
    latitude float4,
    longitude float4,
    mileage int4,
    model varchar(255),
    production_year int4,
    registration_number varchar(255),
    seats int4,
    transmission varchar(255),
    segment_id int8 not null,
    primary key (car_id)
);


create table clients (
    client_id  bigserial not null,
    birth_date timestamp,
    gender varchar(255),
    is_verified boolean,
    name varchar(255),
    phone_number varchar(255),
    surname varchar(255),
    licence_id int8 not null,
    user_id int8 not null,
    primary key (client_id)
);


create table comments (
    comment_id  bigserial not null,
    content varchar(255),
    rental_id int8 not null,
    primary key (comment_id)
);


create table discounts (
    discount_id  bigserial not null,
    description varchar(255),
    percent float4,
    rental_id int8,
    primary key (discount_id)
);


create table driver_licences (
    licence_id  bigserial not null,
    category varchar(255),
    date_obtained timestamp,
    expiration_date timestamp,
    number varchar(255),
    client_id int8 not null,
    primary key (licence_id)
);


create table location_history (
    location_id  bigserial not null,
    latitude float4,
    longitude float4,
    registration_timestamp timestamp,
    car_id int8 not null,
    primary key (location_id)
);

create table payment_types (
    type_id  bigserial not null,
    name varchar(255),
    primary key (type_id)
);


create table payments (
    payment_id  bigserial not null,
    status varchar(255),
    bill_id int8 not null,
    type_id int8 not null,
    primary key (payment_id)
);


create table penalties (
    penalty_id  bigserial not null,
    amount float4,
    description varchar(255),
    rental_id int8,
    primary key (penalty_id)
);


create table rental_end (
    end_id  bigserial not null,
    end_mileage float4,
    end_time timestamp,
    primary key (end_id)
);


create table rental_start (
    start_id  bigserial not null,
    start_mileage float4,
    start_time timestamp,
    primary key (start_id)
);


create table rentals (
    rental_id  bigserial not null,
    car_id int8 not null,
    client_id int8 not null,
    end_id int8,
    start_id int8 not null,
    primary key (rental_id)
);


create table segments (
    segment_id  bigserial not null,
    hour_rate float4,
    km_rate float4,
    name varchar(255),
    rental_fee float4,
    primary key (segment_id)
);


create table user_roles (
    role_id  bigserial not null,
    name varchar(255),
    primary key (role_id)
);


create table users (
    user_id  bigserial not null,
    email varchar(255),
    hashed_password varchar(255),
    login varchar(255),
    role_id int8 not null,
    primary key (user_id)
);


alter table clients
    add constraint clients_licence_fk_unique unique (licence_id);


alter table clients
    add constraint clients_user_fk_unique unique (user_id);


alter table driver_licences
    add constraint driver_licences_client_fk_unique unique (client_id);


alter table rentals
    add constraint rentals_rental_start_fk_unique unique (start_id);


alter table bills
    add constraint bills_payments_fk
    foreign key (payment_id)
    references payments;


alter table cars
    add constraint cars_segments_fk
    foreign key (segment_id)
    references segments;


alter table clients
    add constraint clients_driver_licences_fk
    foreign key (licence_id)
    references driver_licences;


alter table clients
    add constraint clients_users_fk
    foreign key (user_id)
    references users;


alter table comments
    add constraint comments_rentals_fk
    foreign key (rental_id)
    references rentals;


alter table discounts
    add constraint discounts_rentals_fk
    foreign key (rental_id)
    references rentals;


alter table driver_licences
    add constraint driver_licences_clients_fk
    foreign key (client_id)
    references clients;


alter table location_history
    add constraint location_history_cars_fk
    foreign key (car_id)
    references cars;


alter table payments
    add constraint payments_bills_fk
    foreign key (bill_id)
    references bills;


alter table payments
    add constraint payments_payment_types_fk
    foreign key (type_id)
    references payment_types;


alter table penalties
    add constraint penalties_rentals_fk
    foreign key (rental_id)
    references rentals;


alter table rentals
    add constraint rentals_cars_fk
    foreign key (car_id)
    references cars;


alter table rentals
    add constraint rentals_clients_fk
    foreign key (client_id)
    references clients;


alter table rentals
    add constraint rentals_rental_end_fk
    foreign key (end_id)
    references rental_end;


alter table rentals
    add constraint rentals_rental_start_fk
    foreign key (start_id)
    references rental_start;


alter table users
    add constraint users_user_roles_fk
    foreign key (role_id)
    references user_roles;


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