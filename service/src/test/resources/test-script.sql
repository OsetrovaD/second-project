INSERT INTO computer_games_e_shop_storage.game (name, description, company_id, issue_year, minimal_system_requirements, recommended_system_requirements, image_url, age_limit)
VALUES
  ('The Elder Scrolls V: Skyrim',
   'description',
   (SELECT id FROM computer_games_e_shop_storage.game_developer_company WHERE name = 'Bethesda Softworks'),
   2011,
  'minimal requirements',
  'recommended requirements',
  '/PostersAndScreenshots/TheElderScrollsVSkyrim/Skyrim.jpg',
   'MATURE'),
  ('Mass Effect',
   'description',
   (SELECT id FROM computer_games_e_shop_storage.game_developer_company WHERE name = 'BioWare'),
   2007,
  'minimal requirements',
  'recommended requirements',
  '/PostersAndScreenshots/MassEffect/MassEffect.jpg',
   'MATURE'),
  ('Mass Effect 2',
   'description',
   (SELECT id FROM computer_games_e_shop_storage.game_developer_company WHERE name = 'BioWare'),
   2010,
  'minimal requirements',
  'recommended requirements',
  '/PostersAndScreenshots/MassEffect2/MassEffect2.png',
   'MATURE'),
  ('Mass Effect 3',
   'Третья часть одноимённой серии игр',
   (SELECT id FROM computer_games_e_shop_storage.game_developer_company WHERE name = 'BioWare'),
   2012,
  'minimal requirements',
  'recommended requirements',
  '/PostersAndScreenshots/MassEffect3/MassEffect3.jpg',
   'MATURE'),
  ('Deus Ex: Human Revolution',
   'description',
   (SELECT id FROM computer_games_e_shop_storage.game_developer_company WHERE name = 'Eidos'),
   2011,
  'minimal requirements',
  'recommended requirements',
  '/PostersAndScreenshots/DeusExHumanRevolution/DeusExHumanRevolution.jpg',
   'MATURE'),
  ('Dragon Age: Origins',
   'description',
   (SELECT id FROM computer_games_e_shop_storage.game_developer_company WHERE name = 'BioWare'),
   2009,
  'minimal requirements',
  'recommended requirements',
  '/PostersAndScreenshots/DragonAgeOrigins/DragonAgeOrigins.jpg',
   'MATURE');

INSERT INTO computer_games_e_shop_storage.game_game_platform (game_id, price, game_platform)
VALUES
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Elder Scrolls V: Skyrim'), 12, 'PLAYSTATION_3'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Elder Scrolls V: Skyrim'), 15, 'PLAYSTATION_4'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Elder Scrolls V: Skyrim'), 15, 'XBOX_ONE'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Elder Scrolls V: Skyrim'), 10, 'PC'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Elder Scrolls V: Skyrim'), 11, 'XBOX_360'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect'), 9, 'PC'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect'), 11, 'XBOX_ONE'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect'), 11, 'XBOX_360'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect'), 11, 'PLAYSTATION_3'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect 2'), 13, 'PLAYSTATION_3'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect 2'), 12, 'XBOX_ONE'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect 2'), 9, 'PC'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect 2'), 12, 'XBOX_360'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect 3'), 15, 'XBOX_360'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect 3'), 13, 'PC'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect 3'), 15, 'XBOX_ONE'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect 3'), 14, 'NINTENDO_WII_U'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect 3'), 15, 'PLAYSTATION_3'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Deus Ex: Human Revolution'), 15, 'XBOX_ONE'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Deus Ex: Human Revolution'), 12, 'PLAYSTATION_3'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Deus Ex: Human Revolution'), 15, 'XBOX_360'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Deus Ex: Human Revolution'), 11, 'PC'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Deus Ex: Human Revolution'), 19, 'NINTENDO_WII_U'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Dragon Age: Origins'), 14, 'PLAYSTATION_3'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Dragon Age: Origins'), 11, 'XBOX_360'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Dragon Age: Origins'), 7, 'PC');

INSERT INTO computer_games_e_shop_storage.game_developer_company (name)
VALUES
  ('Bethesda Softworks'),
  ('BioWare'),
  ('Eidos'),
  ('Blizzard Entertainment'),
  ('CD Project RED'),
  ('Obsidian Entertainment'),
  ('Ubisoft'),
  ('Supergiant Games'),
  ('Warhorse'),
  ('343 Industries'),
  ('Electronic Arts'),
  ('Team Bondi'),
  ('Naughty Dog Software'),
  ('New World Computing'),
  ('id Software'),
  ('Criterion Games'),
  ('NetherRealm Studios'),
  ('Microids');

INSERT INTO computer_games_e_shop_storage.genre (name)
VALUES
  ('Action'),
  ('Аркада'),
  ('Симулятор/Менеджер'),
  ('Стратегия'),
  ('Приключение'),
  ('RPG');

INSERT INTO computer_games_e_shop_storage.subgenre (name, genre_id)
VALUES
  ('Шутер от первого лица', (SELECT id FROM computer_games_e_shop_storage.genre WHERE name = 'Action')),
  ('Шутер от третьего лица', (SELECT id FROM computer_games_e_shop_storage.genre WHERE name = 'Action')),
  ('Файтинг', (SELECT id FROM computer_games_e_shop_storage.genre WHERE name = 'Action')),
  ('Слэшер', (SELECT id FROM computer_games_e_shop_storage.genre WHERE name = 'Action')),
  ('Спортивный симулятор', (SELECT id FROM computer_games_e_shop_storage.genre WHERE name = 'Симулятор/Менеджер')),
  ('Симулятор жизни', (SELECT id FROM computer_games_e_shop_storage.genre WHERE name = 'Симулятор/Менеджер')),
  ('Симулятор строительства и управления', (SELECT id FROM computer_games_e_shop_storage.genre WHERE name = 'Симулятор/Менеджер')),
  ('Спортивный менеджер', (SELECT id FROM computer_games_e_shop_storage.genre WHERE name = 'Симулятор/Менеджер')),
  ('Стратегия в реальном времени', (SELECT id FROM computer_games_e_shop_storage.genre WHERE name = 'Стратегия')),
  ('Пошаговая стратегия', (SELECT id FROM computer_games_e_shop_storage.genre WHERE name = 'Стратегия')),
  ('Action-adventure', (SELECT id FROM computer_games_e_shop_storage.genre WHERE name = 'Приключение')),
  ('Action/RPG', (SELECT id FROM computer_games_e_shop_storage.genre WHERE name = 'RPG')),
  ('Hack and Slash', (SELECT id FROM computer_games_e_shop_storage.genre WHERE name = 'RPG')),
  ('CRPG', (SELECT id FROM computer_games_e_shop_storage.genre WHERE name = 'RPG')),
  ('Стелс-экшен', (SELECT id FROM computer_games_e_shop_storage.genre WHERE name = 'Action')),
  ('Квест', (SELECT id FROM computer_games_e_shop_storage.genre WHERE name = 'Action')),
  ('Автогонки', (SELECT id FROM computer_games_e_shop_storage.genre WHERE name = 'Аркада'));

  INSERT INTO computer_games_e_shop_storage.game_screenshot (game_id, screenshot_url)
VALUES
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Elder Scrolls V: Skyrim'), '/PostersAndScreenshots/TheElderScrollsVSkyrim/Screenshots/SkyrimScreenshot1.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Elder Scrolls V: Skyrim'), '/PostersAndScreenshots/TheElderScrollsVSkyrim/Screenshots/SkyrimScreenshot2.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect'), '/PostersAndScreenshots/MassEffect/Screenshots/MassEffectScreenshot1.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect'), '/PostersAndScreenshots/MassEffect/Screenshots/MassEffectScreenshot2.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect 2'), '/PostersAndScreenshots/MassEffect2/Screenshots/MassEffect2Screenshot1.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect 2'), '/PostersAndScreenshots/MassEffect2/Screenshots/MassEffect2Screenshot2.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect 3'), '/PostersAndScreenshots/MassEffect3/Screenshots/screentemplate5.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect 3'), '/PostersAndScreenshots/MassEffect3/Screenshots/screenshot-5.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Deus Ex: Human Revolution'), '/PostersAndScreenshots/DeusExHumanRevolution/Screenshots/deuxExScreenshot1.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Deus Ex: Human Revolution'), '/PostersAndScreenshots/DeusExHumanRevolution/Screenshots/deuxExScreenshot2.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Deus Ex: Human Revolution'), '/PostersAndScreenshots/DeusExHumanRevolution/Screenshots/deuxExScreenshot3.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Dragon Age: Origins'), '/PostersAndScreenshots/DragonAgeOrigins/Screenshots/DragonAgeOriginsScreenshot1.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Dragon Age: Origins'), '/PostersAndScreenshots/DragonAgeOrigins/Screenshots/DragonAgeOriginsScreenshot2.jpg');

INSERT INTO computer_games_e_shop_storage.game_subgenre (game_id, subgenre_id)
VALUES
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Elder Scrolls V: Skyrim'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'Action/RPG')),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'Action/RPG')),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'Шутер от третьего лица')),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect 2'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'Action/RPG')),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect 2'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'Шутер от третьего лица')),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect 3'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'Action/RPG')),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect 3'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'Шутер от третьего лица')),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Deus Ex: Human Revolution'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'Action/RPG')),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Deus Ex: Human Revolution'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'Стелс-экшен')),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Dragon Age: Origins'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'CRPG'));

INSERT INTO computer_games_e_shop_storage.storage (number, last_addition_date, game_game_platform_id)
VALUES
  (3, '2018-01-17',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Elder Scrolls V: Skyrim') AND game_platform = 'PLAYSTATION_3')),
  (4, '2018-09-25',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Elder Scrolls V: Skyrim') AND game_platform = 'PLAYSTATION_4')),
  (12, '2018-09-17',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Elder Scrolls V: Skyrim') AND game_platform = 'XBOX_ONE')),
  (10, '2018-09-17',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Elder Scrolls V: Skyrim') AND game_platform = 'PC')),
  (7, '2018-04-22',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Elder Scrolls V: Skyrim') AND game_platform = 'XBOX_360')),
  (11, '2018-05-20',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect') AND game_platform = 'XBOX_360')),
  (1, '2018-01-24',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect') AND game_platform = 'PLAYSTATION_3')),
  (6, '2016-06-05',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect') AND game_platform = 'PC')),
  (7, '2018-06-01',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect') AND game_platform = 'XBOX_ONE')),
  (10, '2018-09-18',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect 2') AND game_platform = 'PLAYSTATION_3')),
  (9, '2018-06-21',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect 2') AND game_platform = 'XBOX_360')),
  (3, '2018-07-12',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect 2') AND game_platform = 'PC')),
  (11, '2018-09-17',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect 2') AND game_platform = 'XBOX_ONE')),
  (4, '2018-09-18',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect 3') AND game_platform = 'XBOX_ONE')),
  (13, '2018-08-16',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect 3') AND game_platform = 'XBOX_360')),
  (4, '2018-05-17',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect 3') AND game_platform = 'PC')),
  (10, '2017-04-22',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect 3') AND game_platform = 'PLAYSTATION_3')),
  (7, '2018-05-12',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect 3') AND game_platform = 'NINTENDO_WII_U')),
  (3, '2017-12-22',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Deus Ex: Human Revolution') AND game_platform = 'PLAYSTATION_3')),
  (6, '2018-01-20',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Deus Ex: Human Revolution') AND game_platform = 'XBOX_360')),
  (9, '2018-03-10',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Deus Ex: Human Revolution') AND game_platform = 'PC')),
  (0, '2018-05-19',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Deus Ex: Human Revolution') AND game_platform = 'XBOX_ONE')),
  (1, '2018-05-23',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Deus Ex: Human Revolution') AND game_platform = 'NINTENDO_WII_U')),
  (5, '2018-02-17',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Dragon Age: Origins') AND game_platform = 'XBOX_360')),
  (2, '2018-06-25',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Dragon Age: Origins') AND game_platform = 'PC')),
  (3, '2018-04-07',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Dragon Age: Origins') AND game_platform = 'PLAYSTATION_3'));