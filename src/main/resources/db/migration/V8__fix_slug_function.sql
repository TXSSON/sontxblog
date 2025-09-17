-- V8: Fix slug generation with lower, unaccent, and trim

-- Drop old functions if exist
DROP FUNCTION IF EXISTS generate_slug_posts() CASCADE;
DROP FUNCTION IF EXISTS generate_slug_categories() CASCADE;
DROP FUNCTION IF EXISTS generate_slug_hashtags() CASCADE;

-- Function for posts
CREATE OR REPLACE FUNCTION generate_slug_posts()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.slug IS NULL AND NEW.title IS NOT NULL THEN
        NEW.slug := trim(both '-' from regexp_replace(
            lower(unaccent(NEW.title)),
            '[^a-z0-9]+',
            '-',
            'g'
        ));
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger for posts
DROP TRIGGER IF EXISTS posts_slug_trigger ON posts;
CREATE TRIGGER posts_slug_trigger
BEFORE INSERT ON posts
FOR EACH ROW
EXECUTE FUNCTION generate_slug_posts();


-- Function for categories
CREATE OR REPLACE FUNCTION generate_slug_categories()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.slug IS NULL AND NEW.name IS NOT NULL THEN
        NEW.slug := trim(both '-' from regexp_replace(
            lower(unaccent(NEW.name)),
            '[^a-z0-9]+',
            '-',
            'g'
        ));
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger for categories
DROP TRIGGER IF EXISTS categories_slug_trigger ON categories;
CREATE TRIGGER categories_slug_trigger
BEFORE INSERT ON categories
FOR EACH ROW
EXECUTE FUNCTION generate_slug_categories();


-- Function for hashtags
CREATE OR REPLACE FUNCTION generate_slug_hashtags()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.slug IS NULL AND NEW.name IS NOT NULL THEN
        NEW.slug := trim(both '-' from regexp_replace(
            lower(unaccent(NEW.name)),
            '[^a-z0-9]+',
            '-',
            'g'
        ));
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger for hashtags
DROP TRIGGER IF EXISTS hashtags_slug_trigger ON hashtags;
CREATE TRIGGER hashtags_slug_trigger
BEFORE INSERT ON hashtags
FOR EACH ROW
EXECUTE FUNCTION generate_slug_hashtags();
