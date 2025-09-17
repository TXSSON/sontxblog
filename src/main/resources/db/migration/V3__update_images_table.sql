-- V3__update_columns_and_remove_post_id.sql

-- Xóa cột post_id trong bảng images
ALTER TABLE images
DROP COLUMN IF EXISTS post_id;

-- Đổi tên cột createdat -> createDate
ALTER TABLE categories
RENAME COLUMN createdat TO createDate;

ALTER TABLE posts
RENAME COLUMN createdat TO createDate;

ALTER TABLE images
RENAME COLUMN createdat TO createDate;

ALTER TABLE hashtags
RENAME COLUMN createdat TO createDate;

-- Đổi tên cột updatedat -> updateDate
ALTER TABLE categories
RENAME COLUMN updatedat TO updateDate;

ALTER TABLE posts
RENAME COLUMN updatedat TO updateDate;

ALTER TABLE images
RENAME COLUMN updatedat TO updateDate;

ALTER TABLE hashtags
RENAME COLUMN updatedat TO updateDate;
