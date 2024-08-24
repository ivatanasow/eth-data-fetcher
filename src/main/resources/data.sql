INSERT INTO users (login, password_hash) VALUES ('alice', '$2a$10$qMUCCJNIW2t6fdRSKaevVeWcvKQPEVh2QerLDR8uBp3rIsVVlPhYe') ON CONFLICT(login) DO NOTHING;
INSERT INTO users (login, password_hash) VALUES ('bob', '$2a$10$LN68uopGAFr/3gicrTEx.exnd8TmV/MsGAghynXxHBa21Zoag1ana') ON CONFLICT(login) DO NOTHING;
INSERT INTO users (login, password_hash) VALUES ('carol', '$2a$10$Z.g8wKMuEVN4WhrFx6CH.OTW/XvBJ8JlCpT.7Kf8mR63SfV.ufqda') ON CONFLICT(login) DO NOTHING;
INSERT INTO users (login, password_hash) VALUES ('dave', '$2a$10$4TEW8rCl8RlNcnDPZLXl6.c7qlSTvFR0sDQTDuZWtt5VJkzoLPdxa') ON CONFLICT(login) DO NOTHING;
