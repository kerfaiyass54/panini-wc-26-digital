-- =========================================================
-- PostgreSQL Indexes for stickers table
-- =========================================================

-- Exact match / filtering indexes
CREATE INDEX IF NOT EXISTS idx_stickers_name
    ON stickers(name);

CREATE INDEX IF NOT EXISTS idx_stickers_type
    ON stickers(type);

CREATE INDEX IF NOT EXISTS idx_stickers_nationality
    ON stickers(nationality);

CREATE INDEX IF NOT EXISTS idx_stickers_place
    ON stickers(place);

-- Composite index for common filtering:
-- WHERE type = ? AND nationality = ?
CREATE INDEX IF NOT EXISTS idx_stickers_type_nationality
    ON stickers(type, nationality);

-- Composite index for:
-- WHERE type = ? AND place = ?
CREATE INDEX IF NOT EXISTS idx_stickers_type_place
    ON stickers(type, place);

-- =========================================================
-- Functional indexes for case-insensitive searches
-- =========================================================

CREATE INDEX IF NOT EXISTS idx_stickers_lower_name
    ON stickers (LOWER(name));

CREATE INDEX IF NOT EXISTS idx_stickers_lower_type
    ON stickers (LOWER(type));

CREATE INDEX IF NOT EXISTS idx_stickers_lower_nationality
    ON stickers (LOWER(nationality));

CREATE INDEX IF NOT EXISTS idx_stickers_lower_place
    ON stickers (LOWER(place));

-- =========================================================
-- Trigram indexes for fast ILIKE '%text%' searches
-- =========================================================

CREATE EXTENSION IF NOT EXISTS pg_trgm;

CREATE INDEX IF NOT EXISTS idx_stickers_name_trgm
    ON stickers
    USING gin (name gin_trgm_ops);

CREATE INDEX IF NOT EXISTS idx_stickers_type_trgm
    ON stickers
    USING gin (type gin_trgm_ops);

CREATE INDEX IF NOT EXISTS idx_stickers_nationality_trgm
    ON stickers
    USING gin (nationality gin_trgm_ops);

CREATE INDEX IF NOT EXISTS idx_stickers_place_trgm
    ON stickers
    USING gin (place gin_trgm_ops);