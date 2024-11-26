CREATE TABLE media_urls
(
    id         INT AUTO_INCREMENT PRIMARY KEY,                                 -- Unique identifier for the media record
    type       ENUM ('episode', 'place', 'other') NOT NULL,                    -- Type associated with the media (e.g., episode, place)
    type_id    INT                                NOT NULL,                    -- Foreign key reference to the associated record
    url        VARCHAR(2048)                      NOT NULL,                    -- URL of the media
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,                            -- Timestamp when the record is created
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- Timestamp when the record is last updated
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;