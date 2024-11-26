CREATE TABLE comments
(
    id                  INT AUTO_INCREMENT PRIMARY KEY,                                  -- Unique identifier for the comment
    content             TEXT NOT NULL,                                                   -- Comment content
    episode_id          INT  NOT NULL,                                                   -- Foreign key referencing the episode
    member_id           INT  NOT NULL,                                                   -- ID of the user who created the comment (FK to member)
    nickname_created_by VARCHAR(255),                                                    -- Nickname of the user who created the comment
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,                             -- Timestamp when the comment is created
    updated_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- Timestamp when the comment is last updated
    CONSTRAINT fk_episode FOREIGN KEY (episode_id) REFERENCES episodes (id),             -- Foreign key constraint for episode
    CONSTRAINT fk_member FOREIGN KEY (member_id) REFERENCES members (member_id)          -- Foreign key constraint for member
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;