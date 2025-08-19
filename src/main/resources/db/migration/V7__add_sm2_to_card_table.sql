ALTER TABLE card
    ADD COLUMN repetition_number INT DEFAULT 0,
    ADD COLUMN easiness DECIMAL(20, 5),
    ADD COLUMN interval INT DEFAULT 0;