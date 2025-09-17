-- 1. Đổi tên bảng
ALTER TABLE images RENAME TO media;

-- 2. Thêm cột type để phân biệt media (image, video, audio,...)
ALTER TABLE media
ADD COLUMN media_type VARCHAR(50) NOT NULL DEFAULT 'image';

-- Đổi tên bảng trung gian
ALTER TABLE post_images RENAME TO post_media;

ALTER TABLE post_hashtags RENAME TO post_hashtag;


-- Đổi cột image_id thành media_id
ALTER TABLE post_media RENAME COLUMN image_id TO media_id;

-- Cập nhật foreign key
ALTER TABLE post_media
DROP CONSTRAINT post_images_image_id_fkey,  -- tên constraint cũ, thay bằng tên thực tế
ADD CONSTRAINT post_media_media_id_fkey
FOREIGN KEY (media_id) REFERENCES media(id) ON DELETE CASCADE;
