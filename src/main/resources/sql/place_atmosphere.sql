CREATE TABLE place_atmosphere
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,                     -- Primary Key
    place_id   BIGINT                              NOT NULL,          -- Foreign Key referencing Company
    atmosphere ENUM ('VALUE1', 'VALUE2', 'VALUE3') NOT NULL,          -- Enum values for Atmosphere
    CONSTRAINT fk_place FOREIGN KEY (place_id) REFERENCES places (id) -- Foreign Key Constraint
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;





