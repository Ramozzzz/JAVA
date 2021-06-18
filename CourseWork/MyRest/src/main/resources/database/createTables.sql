DROP TABLE IF EXISTS person, route, ticket, train;

CREATE TABLE person (
                        person_id     BIGINT PRIMARY KEY,
                        first_name    VARCHAR(20) NOT NULL,
                        last_name     VARCHAR(25) NOT NULL,
                        birth_date    DATE NOT NULL,
                        phone_number  NUMERIC(11) NOT NULL
);

CREATE TABLE train (
                       train_id          BIGINT PRIMARY KEY,
                       name              VARCHAR(15) NOT NULL,
                       type              VARCHAR(20) NOT NULL,
                       number_of_places  INTEGER NOT NULL
);

CREATE TABLE route (
                       route_id         BIGINT PRIMARY KEY,
                       route_date       DATE NOT NULL,
                       departure_point  VARCHAR(20) NOT NULL,
                       destination      VARCHAR(20) NOT NULL,
                       departure_time   TIME NOT NULL,
                       arrival_time     TIME NOT NULL,
                       train_id	        BIGINT NOT NULL,
                       FOREIGN KEY (train_id) REFERENCES train (train_id)
);

CREATE TABLE ticket (
                        ticket_id        BIGINT PRIMARY KEY,
                        contract_date    DATE NOT NULL,
                        sitting_place    INTEGER NOT NULL,
                        price            INTEGER NOT NULL,
                        person_id	     BIGINT NOT NULL,
                        route_id	     BIGINT NOT NULL,
                        FOREIGN KEY (person_id) REFERENCES person (person_id),
                        FOREIGN KEY (route_id) REFERENCES route (route_id)
);