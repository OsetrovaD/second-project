INSERT INTO computer_games_e_shop_storage.game (name, issue_year, age_limit)
VALUES
  ('Mass Effect 3', 2012, 'MATURE'),
  ('Diablo III', 2012, 'MATURE'),
  ('Far Cry 3', 2012, 'MATURE'),
  ('Mass Effect', 2007, 'MATURE'),
  ('Syberia 3', 2017, 'TEEN');

INSERT INTO computer_games_e_shop_storage.game_game_platform (game_id, price, game_platform)
VALUES
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect 3'), 47, 'XBOX_ONE'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Diablo III'), 15, 'PC'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Far Cry 3'), 36, 'PLAYSTATION_3'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect'), 40, 'PC'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Syberia 3'), 28, 'PLAYSTATION_4');

INSERT INTO computer_games_e_shop_storage.genre (name)
VALUES
  ('Action'),
  ('RPG');

INSERT INTO computer_games_e_shop_storage.subgenre (name, genre_id)
VALUES
  ('Шутер от первого лица', (SELECT id FROM computer_games_e_shop_storage.genre WHERE name = 'Action')),
  ('Шутер от третьего лица', (SELECT id FROM computer_games_e_shop_storage.genre WHERE name = 'Action')),
  ('Action/RPG', (SELECT id FROM computer_games_e_shop_storage.genre WHERE name = 'RPG')),
  ('Квест', (SELECT id FROM computer_games_e_shop_storage.genre WHERE name = 'Action'));

INSERT INTO computer_games_e_shop_storage.game_subgenre (game_id, subgenre_id)
VALUES
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect 3'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'Шутер от третьего лица')),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect 3'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'Action/RPG')),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'Шутер от третьего лица')),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'Action/RPG')),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Diablo III'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'Action/RPG')),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Far Cry 3'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'Шутер от первого лица')),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Syberia 3'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'Квест'));