CREATE TABLE IF NOT EXISTS users (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(30) NOT NULL UNIQUE,
  password VARCHAR(64) NOT NULL,
  created_at DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS news (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(128) NOT NULL,
  content TEXT NOT NULL,
  author_id BIGINT NOT NULL,
  heat_score BIGINT NOT NULL DEFAULT 0,
  created_at DATETIME NOT NULL,
  updated_at DATETIME NOT NULL,
  CONSTRAINT fk_news_user FOREIGN KEY (author_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS user_follow (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  follow_user_id BIGINT NOT NULL,
  created_at DATETIME NOT NULL,
  UNIQUE KEY uk_follow (user_id, follow_user_id)
);

CREATE TABLE IF NOT EXISTS news_view (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  news_id BIGINT NOT NULL,
  user_id BIGINT NULL,
  viewed_at DATETIME NOT NULL,
  KEY idx_news_view_time (news_id, viewed_at)
);
