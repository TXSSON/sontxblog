-- V2__add_columns_to_images_table.sql

ALTER TABLE images
ADD COLUMN name VARCHAR(255),
ADD COLUMN size BIGINT,
ADD COLUMN type VARCHAR(50),
ADD COLUMN is_thumbnail BOOLEAN DEFAULT FALSE;
