-- Đổi tên các cột createdat -> create_date
ALTER TABLE categories
RENAME COLUMN createdate TO create_date;

ALTER TABLE posts
RENAME COLUMN createdate TO create_date;

ALTER TABLE media
RENAME COLUMN createdate TO create_date;

ALTER TABLE hashtags
RENAME COLUMN createdate TO create_date;

-- Đổi tên các cột updatedat -> update_date
ALTER TABLE categories
RENAME COLUMN updatedate TO update_date;

ALTER TABLE posts
RENAME COLUMN updatedate TO update_date;

ALTER TABLE media
RENAME COLUMN updatedate TO update_date;

ALTER TABLE hashtags
RENAME COLUMN updatedate TO update_date;