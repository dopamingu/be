CREATE TABLE comment_like
(
    id         INT AUTO_INCREMENT PRIMARY KEY,                               -- Unique identifier for the like
    comment_id INT NOT NULL,                                                 -- Foreign key referencing the comment
    member_id  INT NOT NULL,                                                 -- ID of the user who liked the comment
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,                          -- Timestamp when the like is created
    CONSTRAINT fk_comment FOREIGN KEY (comment_id) REFERENCES comments (id), -- Foreign key constraint
    CONSTRAINT fk_member FOREIGN KEY (member_id) REFERENCES members (id)     -- Foreign key constraint
) ENGINE = InnoDB;
