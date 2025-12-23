INSERT INTO client (id, name) VALUES
                                  (1, 'Иван Иванов'),
                                  (2, 'Петр Петров'),
                                  (3, 'Мария Сидорова');

INSERT INTO address (street, client_id) VALUES
                                            ('ул. Ленина, 10', 1),
                                            ('пр. Мира, 25', 2);

INSERT INTO phone (number, client_id) VALUES
                                          ('+7-999-111-2233', 1),
                                          ('+7-999-222-3344', 1),
                                          ('+7-999-333-4455', 2),
                                          ('+7-999-444-5566', 3);