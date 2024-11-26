CREATE TABLE `members`
(
    `id`                INT                     NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `oauth_provider_id` VARCHAR(100)            NULL,
    `oauth_provider`    ENUM ('apple', 'kakao') NULL,     -- Define possible values
    `email`             VARCHAR(255)            NOT NULL, -- Assuming email is required
    `created_at`        DATETIME                NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`        DATETIME                NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;