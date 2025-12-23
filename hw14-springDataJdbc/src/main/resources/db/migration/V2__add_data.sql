INSERT INTO address (id, street) VALUES
                                     (NEXTVAL('address_seq'), 'Москва, ул. Ленина, д. 10'),
                                     (NEXTVAL('address_seq'), 'Москва, пр. Мира, д. 25'),
                                     (NEXTVAL('address_seq'), 'Москва, ул. Пушкина, д. 7'),
                                     (NEXTVAL('address_seq'), 'Москва, ул. Гагарина, д. 12'),
                                     (NEXTVAL('address_seq'), 'Москва, пр. Строителей, д. 3');

INSERT INTO client (id, name, addressId) VALUES
                                             (NEXTVAL('client_seq'), 'Иван Иванович Иванов', 1),
                                             (NEXTVAL('client_seq'), 'Петр Петрович Петров', 2),
                                             (NEXTVAL('client_seq'), 'Сергей Сергеевич Сергеев', 3),
                                             (NEXTVAL('client_seq'), 'Наталья Борисовна Ковалева', 4),
                                             (NEXTVAL('client_seq'), 'Павел Владимирович Морозов', 5);

INSERT INTO phone (id, number, clientId) VALUES
                                             (NEXTVAL('phone_seq'), '+7 (999) 123-45-67', 1),
                                             (NEXTVAL('phone_seq'), '+7 (495) 777-88-99', 1);

INSERT INTO phone (id, number, clientId) VALUES
                                            (NEXTVAL('phone_seq'), '+7 (911) 222-33-44', 2);

INSERT INTO phone (id, number, clientId) VALUES
                                             (NEXTVAL('phone_seq'), '+7 (905) 555-55-55', 3),
                                             (NEXTVAL('phone_seq'), '+7 (906) 666-66-66', 3),
                                             (NEXTVAL('phone_seq'), '+7 (903) 777-77-77', 3);

INSERT INTO phone (id, number, clientId) VALUES
                                             (NEXTVAL('phone_seq'), '+7 (915) 888-88-88', 4),
                                             (NEXTVAL('phone_seq'), '+7 (916) 999-99-99', 4);

INSERT INTO phone (id, number, clientId) VALUES
                                             (NEXTVAL('phone_seq'), '+7 (925) 111-11-11', 5);