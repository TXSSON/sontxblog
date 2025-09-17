-- Xoá các trigger cũ nếu tồn tại
DROP TRIGGER IF EXISTS posts_slug_trigger ON posts;
DROP TRIGGER IF EXISTS categories_slug_trigger ON categories;
DROP TRIGGER IF EXISTS hashtags_slug_trigger ON hashtags;

-- Xoá function cũ nếu tồn tại
DROP FUNCTION IF EXISTS generate_slug();
DROP FUNCTION IF EXISTS generate_slug_posts();
DROP FUNCTION IF EXISTS generate_slug_categories();
DROP FUNCTION IF EXISTS generate_slug_hashtags();

-----------------------------------------------------
-- Function generate_slug cho bảng posts (dùng title)
-----------------------------------------------------
CREATE OR REPLACE FUNCTION generate_slug_posts()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.title IS NOT NULL AND NEW.slug IS NULL THEN
        NEW.slug := lower(
            regexp_replace(
                unaccent(NEW.title),
                '[^a-z0-9]+',
                '-',
                'g'
            )
        );
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER posts_slug_trigger
BEFORE INSERT OR UPDATE ON posts
FOR EACH ROW
EXECUTE FUNCTION generate_slug_posts();

---------------------------------------------------------
-- Function generate_slug cho bảng categories (dùng name)
---------------------------------------------------------
CREATE OR REPLACE FUNCTION generate_slug_categories()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.name IS NOT NULL AND NEW.slug IS NULL THEN
        NEW.slug := lower(
            regexp_replace(
                unaccent(NEW.name),
                '[^a-z0-9]+',
                '-',
                'g'
            )
        );
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER categories_slug_trigger
BEFORE INSERT OR UPDATE ON categories
FOR EACH ROW
EXECUTE FUNCTION generate_slug_categories();

--------------------------------------------------------
-- Function generate_slug cho bảng hashtags (dùng name)
--------------------------------------------------------
CREATE OR REPLACE FUNCTION generate_slug_hashtags()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.name IS NOT NULL AND NEW.slug IS NULL THEN
        NEW.slug := lower(
            regexp_replace(
                unaccent(NEW.name),
                '[^a-z0-9]+',
                '-',
                'g'
            )
        );
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER hashtags_slug_trigger
BEFORE INSERT OR UPDATE ON hashtags
FOR EACH ROW
EXECUTE FUNCTION generate_slug_hashtags();
