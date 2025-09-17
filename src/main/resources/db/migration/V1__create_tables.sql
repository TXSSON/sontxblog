-- Tạo bảng Categories
CREATE TABLE categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    parent_id INT REFERENCES categories(id) ON DELETE SET NULL,
    is_active BOOLEAN DEFAULT TRUE, -- để bật/tắt category
    sort_order INT DEFAULT NULL,
    slug VARCHAR(255) UNIQUE, 
    createdat TIMESTAMP DEFAULT NOW(),
    updatedat TIMESTAMP DEFAULT NOW()
);


-- Tạo bảng Posts
CREATE TABLE posts (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    featured_image_url VARCHAR(255),
    status VARCHAR(20) DEFAULT 'published' CHECK (status IN ('draft','published','archived')),
    slug VARCHAR(255) UNIQUE, 
    createdat TIMESTAMP DEFAULT NOW(),
    updatedat TIMESTAMP DEFAULT NOW(),
    category_id INT REFERENCES categories(id) ON DELETE SET NULL
);

-- Tạo bảng Images
CREATE TABLE images (
    id SERIAL PRIMARY KEY,
    url VARCHAR(255) NOT NULL,
    createdat TIMESTAMP DEFAULT NOW(),
    updatedat TIMESTAMP DEFAULT NOW(),
    post_id INT REFERENCES posts(id) ON DELETE CASCADE
);

-- Bảng trung gian Post_Images
CREATE TABLE post_images (
    post_id INT REFERENCES posts(id) ON DELETE CASCADE,
    image_id INT REFERENCES images(id) ON DELETE CASCADE,
    PRIMARY KEY (post_id, image_id)
);


-- Tạo bảng Hashtags
CREATE TABLE hashtags (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    slug VARCHAR(50) UNIQUE,
    createdat TIMESTAMP DEFAULT NOW(),
    updatedat TIMESTAMP DEFAULT NOW()
);

-- Tạo bảng trung gian cho mối quan hệ nhiều-nhiều giữa Posts và Hashtags
CREATE TABLE post_hashtags (
    post_id INT REFERENCES posts(id) ON DELETE CASCADE,
    hashtag_id INT REFERENCES hashtags(id) ON DELETE CASCADE,
    PRIMARY KEY (post_id, hashtag_id)
);

-- Hàm sinh slug
CREATE OR REPLACE FUNCTION generate_slug()
RETURNS TRIGGER AS $$
DECLARE
    source_text TEXT;
BEGIN
    -- xác định cột nào để tạo slug
    IF TG_OP = 'INSERT' THEN
        IF TG_TABLE_NAME = 'posts' AND NEW.title IS NOT NULL THEN
            source_text := NEW.title;
        ELSIF (TG_TABLE_NAME = 'categories' OR TG_TABLE_NAME = 'hashtags') AND NEW.name IS NOT NULL THEN
            source_text := NEW.name;
        ELSE
            source_text := NULL;
        END IF;
    END IF;

    -- nếu có text, tạo slug
    IF source_text IS NOT NULL AND NEW.slug IS NULL THEN
        IF TG_TABLE_NAME = 'posts' THEN
            NEW.slug := lower(
                regexp_replace(
                    unaccent(source_text),
                    '[^a-z0-9]+',
                    '-',
                    'g'
                )
            );
        ELSIF TG_TABLE_NAME = 'categories' OR TG_TABLE_NAME = 'hashtags' THEN
            NEW.slug := lower(
                regexp_replace(
                    unaccent(source_text),
                    '[^a-z0-9]+',
                    '-',
                    'g'
                )
            );
        END IF;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


-- Trigger cho posts
CREATE TRIGGER posts_slug_trigger
BEFORE INSERT ON posts
FOR EACH ROW
EXECUTE FUNCTION generate_slug();

-- Trigger cho categories
CREATE TRIGGER categories_slug_trigger
BEFORE INSERT ON categories
FOR EACH ROW
EXECUTE FUNCTION generate_slug();

-- Trigger cho hashtags
CREATE TRIGGER hashtags_slug_trigger
BEFORE INSERT ON hashtags
FOR EACH ROW
EXECUTE FUNCTION generate_slug();

