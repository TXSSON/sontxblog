-- Xóa foreign key thừa trên bảng images
ALTER TABLE images
DROP CONSTRAINT IF EXISTS images_post_id_fkey;
