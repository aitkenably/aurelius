CREATE TABLE card
(
    id    SERIAL PRIMARY KEY,
    deck_id INTEGER NOT NULL,
    question TEXT,
    answer TEXT,
    FOREIGN KEY (deck_id) REFERENCES deck(id)
);