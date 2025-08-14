ALTER TABLE card
    ADD COLUMN num_reviews INT DEFAULT 0,
    ADD COLUMN num_reviews_correct INT DEFAULT 0;