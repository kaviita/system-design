-- 1. Insert Partners
INSERT INTO partners (id, name, mobile_number, email_address, onboarding_date) VALUES
                                                                                   (1, 'PVR Cinemas', '9876543210', 'admin@pvr.com', '2026-01-01'),
                                                                                   (2, 'Inox Leisure', '9876543211', 'ops@inox.com', '2026-01-15');

-- 2. Insert Theatres (FK: partner_id)
INSERT INTO theatres (id, name, address, city, partner_id) VALUES
                                                               (1, 'PVR Vega City', 'Bannerghatta Road', 'Bengaluru', 1),
                                                               (2, 'Inox Garuda Mall', 'Magrath Road', 'Bengaluru', 2);

-- 3. Insert Screens (FK: theatre_id)
INSERT INTO screens (id, name, theatre_id) VALUES
                                               (1, 'Audi 1', 1),
                                               (2, 'Audi 2', 1),
                                               (3, 'Screen 1', 2);

-- 4. Insert Physical Seats (FK: screen_id)
-- Audi 1 Layout
INSERT INTO seats (id, seat_number, category, screen_id) VALUES
                                                             (1, 'A1', 'GOLD', 1), (2, 'A2', 'GOLD', 1), (3, 'A3', 'GOLD', 1),
                                                             (4, 'B1', 'SILVER', 1), (5, 'B2', 'SILVER', 1), (6, 'B3', 'SILVER', 1);

-- 5. Insert Movies
INSERT INTO movies (id, title, description, duration, language, genre) VALUES
                                                                           (1, 'Inception', 'Stealing secrets through dreams.', 148, 'English', 'Sci-Fi'),
                                                                           (2, 'KGF 2', 'Rocky vs Adheera.', 168, 'Kannada', 'Action');

-- 6. Insert Movie Shows (FK: movie_id, screen_id)
-- 2:00 PM Show (Afternoon Discount)
INSERT INTO shows (id, start_time, end_time, base_price, movie_id, screen_id, status) VALUES
    (1, '2026-04-10 14:00:00', '2026-04-10 16:58:00', 250.00, 1, 1, 'ACTIVE');

-- 7. Insert Show Seats (FK: show_id, seat_id, booking_id)
-- Mapping physical seats to the specific show instance
INSERT INTO show_seats (id, show_id, seat_id, status, price_multiplier, version, booking_id) VALUES
                                                                                                 (1, 1, 1, 'AVAILABLE', 1.5, 0, NULL), -- Gold
                                                                                                 (2, 1, 2, 'AVAILABLE', 1.5, 0, NULL), -- Gold
                                                                                                 (3, 1, 3, 'AVAILABLE', 1.5, 0, NULL), -- Gold
                                                                                                 (4, 1, 4, 'AVAILABLE', 1.0, 0, NULL), -- Silver
                                                                                                 (5, 1, 5, 'AVAILABLE', 1.0, 0, NULL), -- Silver
                                                                                                 (6, 1, 6, 'AVAILABLE', 1.0, 0, NULL); -- Silver

-- 8. Insert Customers
INSERT INTO customers (id, first_name, last_name, email_address) VALUES
                                                                     (1, 'Kavita', 'Pandya', 'kavita@example.com'),
                                                                     (2, 'Ananya', 'Iyer', 'ananya@example.com');