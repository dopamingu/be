CREATE TABLE episodes
(
    episode_id INT AUTO_INCREMENT PRIMARY KEY,                                                                          -- Unique identifier for the episode
    title      VARCHAR(255)                 NOT NULL,                                                                   -- Title of the episode
    content    TEXT                         NOT NULL,                                                                   -- Content of the episode
    type       ENUM ('썸', '고백', '이별', '재회') NOT NULL,                                                                   -- Type of the episode
    status     ENUM ('draft', 'published', 'archived', 'deleted') DEFAULT 'draft',                                      -- Status of the episode
    member_id INT,
    created_at TIMESTAMP                                          DEFAULT CURRENT_TIMESTAMP,                            -- Timestamp when the episode is created
    updated_at TIMESTAMP                                          DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- Timestamp when the episode is last updated
    CONSTRAINT fk_member FOREIGN KEY (member_id) REFERENCES members (id) -- Foreign Key Constraint
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
