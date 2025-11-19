-- USERS
CREATE TABLE users (
                       id               BIGSERIAL PRIMARY KEY,
                       full_name        VARCHAR(120)        NOT NULL,
                       email            VARCHAR(160)        NOT NULL UNIQUE,
                       password_hash    VARCHAR(255)        NOT NULL,
                       phone            VARCHAR(40),
                       address          VARCHAR(255),
                       avatar_url       VARCHAR(512),
                       is_owner         BOOLEAN             NOT NULL DEFAULT FALSE,
                       public_bio       VARCHAR(500)
);

-- OWNER_PROFILES
CREATE TABLE owner_profiles (
                                id                 BIGSERIAL PRIMARY KEY,
                                user_id            BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
                                is_verified        BOOLEAN NOT NULL DEFAULT FALSE,
                                payout_email       VARCHAR(160),
                                bank_account_number VARCHAR(64),
                                yape_phone_number  VARCHAR(40)
);

-- RENTER_PROFILES
CREATE TABLE renter_profiles (
                                 id                   BIGSERIAL PRIMARY KEY,
                                 user_id              BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
                                 payment_method       VARCHAR(40),
                                 preferred_bike_type  VARCHAR(32),
                                 notifications_enabled BOOLEAN NOT NULL DEFAULT TRUE
);