CREATE SEQUENCE admin.known_fruits_id_seq;
SELECT setval('admin."known_fruits_id_seq"', 3);
CREATE TABLE admin.known_fruits
(
    id   INT,
    name VARCHAR(40)
);
INSERT INTO admin.known_fruits(id, name)
VALUES (1, 'Cherry');
INSERT INTO admin.known_fruits(id, name)
VALUES (2, 'Apple');
INSERT INTO admin.known_fruits(id, name)
VALUES (3, 'Banana');

CREATE SEQUENCE user1.known_fruits_id_seq;
SELECT setval('user1."known_fruits_id_seq"', 3);
CREATE TABLE user1.known_fruits
(
    id   INT,
    name VARCHAR(40)
);
INSERT INTO user1.known_fruits(id, name)
VALUES (1, 'Avocado');
INSERT INTO user1.known_fruits(id, name)
VALUES (2, 'Apricots');
INSERT INTO user1.known_fruits(id, name)
VALUES (3, 'Blackberries');
