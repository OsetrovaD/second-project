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