DELETE FROM ticket;
DELETE FROM person;
DELETE FROM train;
DELETE FROM route;

INSERT INTO person (person_id, first_name, last_name, birth_date, phone_number)
VALUES (100000, 'Дарья', 'Щукина', TO_DATE('1996-06-17', 'yyyy-mm-dd'), 84957359812);
INSERT INTO person (person_id, first_name, last_name, birth_date, phone_number)
VALUES (100001, 'Олег', 'Лысых', TO_DATE('1982-02-18', 'yyyy-mm-dd'), 89123410341);
INSERT INTO person (person_id, first_name, last_name, birth_date, phone_number)
VALUES (100002, 'Дмитрий', 'Голубев', TO_DATE('1993-05-03', 'yyyy-mm-dd'), 88140916345);
INSERT INTO person (person_id, first_name, last_name, birth_date, phone_number)
VALUES (100003, 'Анна', 'Шукшина', TO_DATE('1999-08-19', 'yyyy-mm-dd'), 81238276435);
INSERT INTO person (person_id, first_name, last_name, birth_date, phone_number)
VALUES (100004, 'Станислав', 'Лебедев', TO_DATE('2001-09-20', 'yyyy-mm-dd'), 89108173471);

INSERT INTO train (train_id, name, type, number_of_places)
VALUES (10000, 'Стриж', 'высокоскоростной', 40);
INSERT INTO train (train_id, name, type, number_of_places)
VALUES (10001, 'Ласточка', 'скоростной', 50);
INSERT INTO train (train_id, name, type, number_of_places)
VALUES (10002, 'Дятел', 'пассажирский', 65);
INSERT INTO train (train_id, name, type, number_of_places)
VALUES (10003, 'Грач', 'пассажирский', 45);
INSERT INTO train (train_id, name, type, number_of_places)
VALUES (10004, 'Воробей', 'высокоскоростной', 35);

INSERT INTO route (route_id, route_date, departure_point, destination, departure_time, arrival_time, train_id)
VALUES (1000000, TO_DATE('2021-03-11', 'yyyy-mm-dd'), 'Москва', 'Магнитогорск', to_timestamp('12:45', 'hh24:mi'), to_timestamp('17:45', 'hh24:mi'), 10000);
INSERT INTO route (route_id, route_date, departure_point, destination, departure_time, arrival_time, train_id)
VALUES (1000001, TO_DATE('2018-05-23', 'yyyy-mm-dd'), 'Москва', 'Санкт-Петербург', to_timestamp('09:00', 'hh24:mi'), to_timestamp('11:00', 'hh24:mi'), 10001);
INSERT INTO route (route_id, route_date, departure_point, destination, departure_time, arrival_time, train_id)
VALUES (1000002, TO_DATE('2015-11-09', 'yyyy-mm-dd'), 'Москва', 'Екатеринбург', to_timestamp('05:00', 'hh24:mi'), to_timestamp('08:00', 'hh24:mi'), 10002);
INSERT INTO route (route_id, route_date, departure_point, destination, departure_time, arrival_time, train_id)
VALUES (1000003, TO_DATE('2019-11-03', 'yyyy-mm-dd'), 'Москва', 'Астрахань', to_timestamp('11:00', 'hh24:mi'), to_timestamp('17:00', 'hh24:mi'), 10003);
INSERT INTO route (route_id, route_date, departure_point, destination, departure_time, arrival_time, train_id)
VALUES (1000004, TO_DATE('2020-01-13', 'yyyy-mm-dd'), 'Москва', 'Сочи', to_timestamp('20:00', 'hh24:mi'), to_timestamp('23:00', 'hh24:mi'), 10004);

INSERT INTO ticket (ticket_id, contract_date, sitting_place, price, person_id, route_id)
VALUES (10000000, TO_DATE('2021-03-10', 'yyyy-mm-dd'), 7, 700, 100000, 1000000);
INSERT INTO ticket (ticket_id, contract_date, sitting_place, price, person_id, route_id)
VALUES (10000001, TO_DATE('2018-05-22', 'yyyy-mm-dd'), 31, 1200, 100001, 1000001);
INSERT INTO ticket (ticket_id, contract_date, sitting_place, price, person_id, route_id)
VALUES (10000002, TO_DATE('2015-11-07', 'yyyy-mm-dd'), 28, 900, 100002, 1000002);
INSERT INTO ticket (ticket_id, contract_date, sitting_place, price, person_id, route_id)
VALUES (10000003, TO_DATE('2019-11-02', 'yyyy-mm-dd'), 2, 600, 100003, 1000003);
INSERT INTO ticket (ticket_id, contract_date, sitting_place, price, person_id, route_id)
VALUES (10000004, TO_DATE('2020-01-13', 'yyyy-mm-dd'), 34, 1000, 100004, 1000004);