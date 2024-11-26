CREATE TABLE episode_like
(
    id                  INT AUTO_INCREMENT PRIMARY KEY,                      -- Unique identifier for the like
    episode_id          INT NOT NULL,                                        -- Foreign key referencing the episode
    member_id           INT NOT NULL,                                        -- ID of the user who liked the episode
    nickname_created_by VARCHAR(255),                                        -- Nickname of the user who created the comment
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,                 -- Timestamp when the like is created
    CONSTRAINT fk_episode FOREIGN KEY (episode_id) REFERENCES episodes (id), -- Foreign key constraint
    CONSTRAINT fk_member FOREIGN KEY (member_id) REFERENCES members (id)     -- Foreign key constraint
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
