CREATE DATABASE IF NOT EXISTS reservation_service;

USE reservation_service;


CREATE TABLE payment_types (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               name TEXT NOT NULL,
                               duration_from TIMESTAMP,
                               duration_to TIMESTAMP,
                               added_by BIGINT NOT NULL,
                               deleted_by BIGINT
);


CREATE TABLE status (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name TEXT NOT NULL,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        added_by BIGINT NOT NULL
);


CREATE TABLE payment (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         payment_type_id BIGINT NOT NULL,
                         status TEXT NOT NULL,
                         date_paid TIMESTAMP,
                         amount DECIMAL(10, 2) NOT NULL,
                         FOREIGN KEY (payment_type_id) REFERENCES payment_types(id)
);


CREATE TABLE reservation (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             date TIMESTAMP NOT NULL,
                             id_company BIGINT NOT NULL,
                             id_service BIGINT NOT NULL,
                             id_customer INT NOT NULL,
                             send_sms TIMESTAMP,
                             `2FA_code` BIGINT,
                             hidden BOOLEAN DEFAULT FALSE,
                             customer_email VARCHAR(255),
                             customer_phone_number BIGINT,
                             customer_full_name VARCHAR(255),
                             status_id BIGINT,
                             payment_id BIGINT,
                             FOREIGN KEY (status_id) REFERENCES status(id),
                             FOREIGN KEY (payment_id) REFERENCES payment(id)
);
