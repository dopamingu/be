CREATE TABLE places
(
    id         INT AUTO_INCREMENT PRIMARY KEY,                                  -- Unique identifier for the place
    name       VARCHAR(255)   NOT NULL,                                         -- Name of the place
    latitude   DECIMAL(10, 8) NOT NULL,                                         -- Latitude for geolocation
    longitude  DECIMAL(11, 8) NOT NULL,                                         -- Longitude for geolocation
    address    TEXT           NOT NULL,                                         -- Address of the place
    location   GEOMETRY       NOT NULL SRID 4326,
    member_id  INT            NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,                             -- Timestamp when the like is created
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- Timestamp when the comment is last updated
    CONSTRAINT fk_member FOREIGN KEY (member_id) REFERENCES members (id),       -- Foreign Key Constraint
    SPATIAL INDEX (location)                                                    -- Index for spatial queries
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;