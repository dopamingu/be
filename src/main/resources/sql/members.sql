CREATE TABLE `members`
(
    `id`                INT                     NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `oauth_provider_id` VARCHAR(100)            NULL,
    `oauth_email`       VARCHAR(255)            NOT NULL, -- Assuming email is required
    `oauth_provider`    ENUM ('APPLE', 'KAKAO') NULL,     -- Define possible values
    `created_at`        DATETIME                NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`        DATETIME                NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;