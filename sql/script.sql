CREATE DATABASE computer_games_e_shop encoding='UTF8';

CREATE SCHEMA computer_games_e_shop_storage;

CREATE TABLE computer_games_e_shop_storage.user_data (
  id BIGSERIAL PRIMARY KEY,
  login CHARACTER VARYING(256) UNIQUE NOT NULL,
  password CHARACTER VARYING(256),
  first_name CHARACTER VARYING(256),
  last_name CHARACTER VARYING(256),
  e_mail CHARACTER VARYING(256) UNIQUE NOT NULL,
  address TEXT,
  role CHARACTER VARYING(128),
  phone_number CHARACTER VARYING(128)
);

CREATE TABLE computer_games_e_shop_storage.game_developer_company (
  id SERIAL PRIMARY KEY,
  name CHARACTER VARYING(256) UNIQUE NOT NULL
);

CREATE TABLE computer_games_e_shop_storage.genre (
  id SERIAL PRIMARY KEY,
  name CHARACTER VARYING(256) UNIQUE NOT NULL
);

CREATE TABLE computer_games_e_shop_storage.game (
  id BIGSERIAL PRIMARY KEY,
  name CHARACTER VARYING(256) NOT NULL,
  description TEXT,
  company_id INTEGER REFERENCES computer_games_e_shop_storage.game_developer_company (id),
  issue_year INTEGER,
  minimal_system_requirements TEXT,
  recommended_system_requirements TEXT,
  image_url TEXT,
  age_limit CHARACTER VARYING(128)
);

CREATE TABLE computer_games_e_shop_storage.comment (
  id BIGSERIAL PRIMARY KEY,
  game_id BIGINT REFERENCES computer_games_e_shop_storage.game (id),
  text TEXT,
  user_id BIGINT REFERENCES computer_games_e_shop_storage.user_data (id),
  date DATE
);

CREATE TABLE computer_games_e_shop_storage.game_game_platform (
  game_id BIGINT NOT NULL REFERENCES computer_games_e_shop_storage.game (id),
  price INTEGER,
  id BIGSERIAL PRIMARY KEY,
  game_platform CHARACTER VARYING(256),
  UNIQUE (game_id, game_platform)
);

CREATE TABLE computer_games_e_shop_storage.storage (
  number SMALLINT,
  last_addition_date DATE,
  id BIGSERIAL PRIMARY KEY,
  game_game_platform_id BIGINT REFERENCES computer_games_e_shop_storage.game_game_platform (id)
);

CREATE TABLE computer_games_e_shop_storage.order_data (
  id              BIGSERIAL PRIMARY KEY,
  user_id         BIGINT NOT NULL REFERENCES computer_games_e_shop_storage.user_data (id),
  delivery_method CHARACTER VARYING(128),
  payment_form    CHARACTER VARYING(128),
  condition       CHARACTER VARYING(128),
  date            DATE,
  delivery_date   DATE
);

CREATE TABLE computer_games_e_shop_storage.items_in_order (
  order_id BIGINT NOT NULL REFERENCES computer_games_e_shop_storage.order_data (id),
  number INTEGER,
  game_game_platfrom_id BIGINT REFERENCES computer_games_e_shop_storage.game_game_platform (id),
  id BIGSERIAL PRIMARY KEY,
  UNIQUE (game_game_platfrom_id, order_id)
);

CREATE TABLE computer_games_e_shop_storage.game_screenshot (
  game_id BIGINT NOT NULL REFERENCES computer_games_e_shop_storage.game (id),
  screenshot_url TEXT NOT NULL,
  PRIMARY KEY (game_id, screenshot_url)
);

CREATE TABLE computer_games_e_shop_storage.subgenre (
  id SERIAL PRIMARY KEY,
  name CHARACTER VARYING (256) UNIQUE NOT NULL,
  genre_id INTEGER REFERENCES computer_games_e_shop_storage.genre (id)
);

CREATE TABLE computer_games_e_shop_storage.game_subgenre (
  game_id BIGINT NOT NULL REFERENCES computer_games_e_shop_storage.game (id),
  subgenre_id INTEGER NOT NULL REFERENCES computer_games_e_shop_storage.subgenre (id),
  UNIQUE (game_id, subgenre_id)
);

CREATE TABLE computer_games_e_shop_storage.admin_detail (
  user_id BIGINT UNIQUE NOT NULL REFERENCES computer_games_e_shop_storage.user_data(id),
  salary SERIAL NOT NULL
);
CREATE TABLE computer_games_e_shop_storage.simple_user_detail (
  user_id BIGINT UNIQUE NOT NULL REFERENCES computer_games_e_shop_storage.user_data(id),
  last_visit_date DATE
);

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

INSERT INTO computer_games_e_shop_storage.user_data (login, password, first_name, last_name, e_mail, address, role, phone_number)
VALUES
  ('Admin', 'Admin', 'Peter', 'Ivanov', 'admin@admin.com', '', 'ADMIN', '80020001100'),
  ('User', 'User', 'Peter', 'Ivanov', 'user1@user.com', 'Minsk', 'USER', '55500002110'),
  ('abc', 'abc', 'Пётр', 'Петров', 'abc@abc.abc', 'Minsk', 'USER', '12304567891'),
  ('DemoLogin', '12345', 'Борис', 'Симонов', 'email@email.email', '', 'USER', '12032569812'),
  ('userDemo', '123456', 'Peter', 'Ivanov', 'userdemo@mail.ru', '', 'USER', ''),
  ('Cake', 'sweetcake', 'Name', '', 'cake@cake.cake', '', 'USER', '');

INSERT INTO computer_games_e_shop_storage.game (name, description, company_id, issue_year, minimal_system_requirements, recommended_system_requirements, image_url, age_limit)
VALUES
  ('The Elder Scrolls V: Skyrim',
   'Это новая жизнь. Мир, где можно быть вором, разбойником, наемным убийцей — или привычно отправиться спасать вселенную от нависшей мистической угрозы. Или не отправляться, забить на все и просто обосноваться в небольшом уютном домике в самой северной точке Тамриэля, завести семью. Бесполезно описывать все, что тут можно, важно понять, что ограничений нет никаких. Разработчики не вмешиваются в ваши дела, но дают миллион возможностей. Это настоящая РОЛЕВАЯ игра в самом правильном значении этого слова. Действие происходит спустя 200 лет после событий Oblivion. Группа нордов под названием Братья Бури во главе с Ульфриком Буревестником устроила мятеж против Империи, развязав в Скайриме Гражданскую войну. Однако погруженная в хаос империя и не догадывается, что вскоре ей придётся столкнуться с куда более опасной силой.',
   (SELECT id FROM computer_games_e_shop_storage.game_developer_company WHERE name = 'Bethesda Softworks'),
   2011,
  'ОС: Windows7, Vista, XP (32 или 64-бит), Процессор: Dual 2,0 ГГц Core или эквивалентный процессор, Оперативная память: 2 ГБ оперативной памяти, Графика: 512 МБ оперативной памяти с поддержкой DirectX 9.0c, DirectX: 9.0c, Жесткий диск: 6 ГБ свободного пространства, Звук: Cовместимая звуковая карта c DirectX',
  'ОС: Windows7, Vista, XP (32 или 64-бит)Процессор: Четырехъядерный процессор Intel или AMD CPU,Память: 4 Гб оперативной памяти,Графика: 1 Гб оперативной памяти с поддержкой DirectX 9.0c (NVIDIA GeForce GTX 260 или выше; ATI Radeon 4890 или выше),DirectX: 9.0c,Жесткий диск: 6 ГБ свободного пространства,Звук: Cовместимая звуковая карта c DirectX',
  '/PostersAndScreenshots/TheElderScrollsVSkyrim/Skyrim.jpg',
   'MATURE'),
  ('Mass Effect',
   'Согласно сюжету игры, в XXII веке человечество освоило технологии сверхсветовых полётов,  в том числе вынесенный в название игры эффект массы, и вступило в контакт с внеземными цивилизациями. Главный герой или героиня игры, капитан Шепард, становится Спектром — специальным агентом на службе межзвёздного совета Цитадели — и вместе с группой соратников, как землян, так и инопланетян, посещает различные области и звёздные системы Млечного пути. В ходе сюжета игры Шепард и члены его (её) экипажа, преследуя изменника Сарена, сталкиваются с истинной угрозой для галактики — Жнецами, древней цивилизацией разумных машин.',
   (SELECT id FROM computer_games_e_shop_storage.game_developer_company WHERE name = 'BioWare'),
   2007,
  'ОС: Windows XP с SP2 или Windows Vista, Процессор:  Intel Pentium 4 2,4 ГГц или быстрее / AMD 2,0 ГГц, Оперативная память: 1 ГБ оперативной памяти, Видеокарта: с поддержкой DirectX 9.0c, ATI X1300 XT или выше, Жесткий диск: 12 ГБ свободного пространства',
  'ОС: Windows XP/Vista,Процессор: Core 2 Duo/Athlon X2 2.5 ГГц,Память: 2 Гб оперативной памяти,Видеокарта: GeForce 7900/ATI X1800 XL,Жесткий диск: 12 ГБ свободного пространства',
  '/PostersAndScreenshots/MassEffect/MassEffect.jpg',
   'MATURE'),
  ('Mass Effect 2',
   'Это вторая часть серии Mass Effect, являющаяся непосредственным продолжением первой игры. Её действие происходит в галактике Млечный Путь в XXII веке, когда человечество, осваивая космос, сталкивается с враждебной инопланетной расой — Жнецами. Главным героем является капитан Шепард — опытный офицер Космического флота Земли. По поручению организации «Цербер» он собирает команду специалистов (как людей, так и инопланетян), чтобы бороться со Жнецами. Игроки имеют возможность перенести сохранение из предыдущей части, влияя таким образом на сюжет игры.',
   (SELECT id FROM computer_games_e_shop_storage.game_developer_company WHERE name = 'BioWare'),
   2010,
  'ОС: Windows7, Vista SP1, XP SP3, Процессор:  1,8 ГГц  Intel Core2 Duo или эквивалентный AMD, Оперативная память: 1 ГБ для Windows XP/ 2 ГБ для Windows Vista и Windows7, Видеокарта:  256 МБ видеопамяти (с поддержкой Pixel Shader 3.0). Поддердиваемые чипсеты — NVIDIA GeForce 6800 или лучше; ATI Radeon X1600 или лучше, Жесткий диск: 15 ГБ свободного пространства, DVD ROM: 1x, DirectX: DirectX Август 2008, Звук: Cовместимая звуковая карта c Direct 9.0с, Устройства ввода:  клавиатура, мышь',
  'ОС: Windows7, Vista SP1, XP SP3 ,Процессор: 2,6+ ГГц Intel Core2 Duo или эквивалентный AMD,Память: 2 Гб оперативной памяти,Видеокарта: 256 МБ видеопамяти (с поддержкой Pixel Shader 3.0). Поддердиваемые чипсеты — NVIDIA GeForce 6800 или лучше; ATI Radeon X1600 или лучше,Жесткий диск: 15 ГБ свободного пространства, DirectX: DirectX Август 2008, Звук: Cовместимая звуковая карта c Direct 9.0с, Устройства ввода:  клавиатура, мышь',
  '/PostersAndScreenshots/MassEffect2/MassEffect2.png',
   'MATURE'),
  ('Mass Effect 3',
   'Третья часть одноимённой серии игр',
   (SELECT id FROM computer_games_e_shop_storage.game_developer_company WHERE name = 'BioWare'),
   2012,
  'ОС: Windows XP SP3/Vista SP1, 7, Процессор:  1,8 ГГц Intel Core 2 Duo (аналогичный AMD), Оперативная память: 1 ГБ, Видеокарта:  256 МБ (с поддержкой Pixel Shader 3.0), Жесткий диск: 15 ГБ свободного пространства, ',
  'ОС: Windows XP SP3/Vista SP1, 7,Процессор: 2,4 ГГц Intel Core 2 Duo (или аналогичный  AMD),Оперативная память: 2 Гб,Видеокарта: AMD/ATI Radeon HD 4850 512 МБ или лучше, NVIDIA GeForce 9800 GT 512 МБ или лучше,Жесткий диск: 15 ГБ свободного пространства, ',
  '/PostersAndScreenshots/MassEffect3/MassEffect3.jpg',
   'MATURE'),
  ('Deus Ex: Human Revolution',
   'Это третья по счёту игра во вселенной Deus Ex, являющаяся косвенным приквелом оригинальных двух игр, ибо события Human Revolution разворачиваются за четверть века до оригинальной дилогии Главный герой Адам Дженсен — начальник службы безопасности детройтской компании «Шариф Индастриз», одного из ведущих мировых производителей механических имплантатов для людей. В мире образца 2027 года человечество фактически раскололось на два лагеря - одни ратуют за кибернетические улучшения, вторые же решительно против того, чтобы люди занимались аугментацией. Дженсен, вставший на пути загадочных нападавших, фактически гибнет, однако благодаря имплантам перерождается и начинает поиск преступников, которые почти убили его самого и украли группу ведущих ученых "Шариф Индастрис".',
   (SELECT id FROM computer_games_e_shop_storage.game_developer_company WHERE name = 'Eidos'),
   2011,
  'ОС: Windows XP, Vista, 7, Процессор:  Intel Core 2 Duo 2 GHz, Оперативная память: 1 ГБ, Видеокарта:  ATI Radeon HD 2600 Pro/NVIDIA GeForce 8600 GT, Жесткий диск: 8.5 ГБ свободного пространства ',
  'ОС: Windows 7,Процессор: AMD Phenom II X4 или Intel Core 2 Quad или лучше,Оперативная память: 2 Гб,Видеокарта: ATI Radeon HD 5850/NVIDIA GeForce GTX 460,Жесткий диск: 8.5 ГБ свободного пространства, ',
  '/PostersAndScreenshots/DeusExHumanRevolution/DeusExHumanRevolution.jpg',
   'MATURE'),
  ('Dragon Age: Origins',
   'Вымышленному королевству Ферелден, где происходит действие игры, угрожает вторжение демонических «порождений тьмы». Чтобы спасти страну от гибели, главный герой, вступивший в орден Серых Стражей, должен собрать группу соратников, примирить враждующие группировки и остановить гражданскую войну. В ходе Dragon Age: Origins игрок обследует обширный мир игры, вступая в сражения с врагами и выполняя разнообразные задания.',
   (SELECT id FROM computer_games_e_shop_storage.game_developer_company WHERE name = 'BioWare'),
   2009,
  'ОС: Windows XP SP3, Vista SP1, 7, Процессор:  Intel Core 2 Single 1,6 ГГц (XP) или 1,8 ГГц, (Vista/Win 7)/AMD X2 (или эквивалент) 1,8 ГГц (XP) или 2,2 ГГц (Vista/Win 7), Оперативная память: 1 Гбайт (XP), 1,5 Гбайт (Vista/Win 7), Видеокарта:  ATI Radeon X850 128MB или больше, NVIDIA GeForce 6600 GT 128MB или больше (XP), ATI Radeon X1550 256MB или больше, NVIDIA GeForce 7600 GT 256MB или больше (Vista/Win 7), Жесткий диск: 20 ГБ свободного пространства ',
  'ОС: Windows XP SP3, Vista SP1, 7,Процессор: Intel Core 2 Duo 2,4 ГГц, AMD Phenom II X2 Dual-Core 2,7 ГГц,Оперативная память: 2 Гбайт (XP), 3 Гбайт (Vista/Win 7),Видеокарта: ATI 3850 512 MB или больше, NVIDIA 8800GTS 512 MB или больше.,Жесткий диск: 20 ГБ свободного пространства ',
  '/PostersAndScreenshots/DragonAgeOrigins/DragonAgeOrigins.jpg',
   'MATURE'),
  ('Diablo III',
   'Игра является частью серии игр Diablo и прямым продолжением Diablo II. Действие игры, как и в предыдущих частях серии, происходит в мире тёмного фэнтези, называемом Санктуарий (англ. Sanctuary — святилище, убежище). События развиваются вокруг борьбы армии Небес с армией Преисподней за мир Санктуария. Персонажи игроков не являются прямыми последователями этих фракций, однако тяготеют к силам Небес, так как армии Преисподней стремятся поработить и разрушить Санктуарий — их родной мир.',
   (SELECT id FROM computer_games_e_shop_storage.game_developer_company WHERE name = 'Blizzard Entertainment'),
   2012,
  'ОС: Windows XP, Vista, Процессор:  Intel Pentium D 2.8 ГГц или AMD Athlon 64 X2 Dual Core 4400+, Оперативная память: 1.5 ГБ, Видеокарта:  NVIDIA GeForce 7800 GT или ATI Radeon X1950 Pro, Жесткий диск: 12 ГБ свободного пространства ',
  'ОС: Windows XP SP3, Vista SP1, 7,Процессор: Core 2 Duo 2.4ГГц или Athlon 64 X2 5600+,Оперативная память: 2 ГБ,Видеокарта: Nvidia GeForce 260 или AMD Radeon HD 4870,Жесткий диск: 12 ГБ свободного пространства ',
  '/PostersAndScreenshots/Diablo3/Diablo3.jpg',
   'MATURE'),
  ('The Witcher 2: Assassins of Kings',
   'Действия игры «Ведьмак 2: Убийцы королей» начинаются вскоре после событий игры «Ведьмак» и также проходят в мире книг Анджея Сапковского. Главный герой по-прежнему ведьмак Геральт из Ривии. Сюжет, по сравнению с первой частью игры, стал более развит. Также известно, что по сюжету главный герой будет много путешествовать, а в игровом сюжете будет замешана мощная магия. Конец сюжета пролога заканчивался покушением на короля Фольтеста. Геральт не смог предотвратить покушение и упустил таинственного убийцу, который имел «ведьмачьи» глаза. В «The Witcher 2: Assassins of Kings» Геральта обвинили в убийстве Фольтеста и ему предстоит отправиться расследовать данное преступление и восстановить свою репутацию.',
   (SELECT id FROM computer_games_e_shop_storage.game_developer_company WHERE name = 'CD Project RED'),
   2011,
  'ОС: Windows XP, Vista, 7, Процессор:  Процессор Intel Core 2 Duo c тактовой частотой 2,2 ГГц или AMD Athlon 64 X2 5000+, Оперативная память: 1 Гб (для XP)/2 Гб (для Vista/7), Видеокарта:  512 Мб видеопамяти и поддержкой Pixel Shader 3.0 GeForce 8800 или ATI Radeon HD 3850, Жесткий диск: 16 ГБ свободного пространства, Звуковая карта, совместимая с DirectX',
  'ОС: Windows XP, Vista, 7,Процессор: Intel Core 2 Quad или AMD Phenom X4,Оперативная память: 3 Гб (для XP)/4 Гб (для Vista/7),Видеокарта: 1 Гб видеопамяти и поддержкой Pixel Shader 3.0 GeForce 260 или ATI Radeon HD 4850,Жесткий диск: 16 ГБ свободного пространства, Звуковая карта, совместимая с DirectX',
  '/PostersAndScreenshots/Witcher2/Witcher2.jpg',
   'MATURE'),
  ('Warcraft 3: The Frozen Throne',
   'События дополнения продолжают сюжет Warcraft III: Reign of Chaos. После битвы на горе Хиджал расы Азерота стали возвращаться к мирной жизни. Однако последствия Третьей Войны порождают новые конфликты. Как и в предыдущей части, сюжетная линия представлена в виде кампаний, которые образуют общий сюжет. Каждая кампания состоит из глав.',
   (SELECT id FROM computer_games_e_shop_storage.game_developer_company WHERE name = 'Blizzard Entertainment'),
   2003,
  'ОС: Windows 98, 2000, ME, XP, Процессор:  Intel Pentium, 0.4 ГГц, Оперативная память: 1 Гб, Видеокарта:  совместимая с DirectX 8.1, Жесткий диск: 1 ГБ свободного пространства',
  'ОС: Windows 7, 8, 10,Процессор: 1 ГГц,Оперативная память: 32-Bit Windows: 1 ГБ /64-Bit Windows: 2 ГБ,Видеокарта: совместимая с DirectX и поддерживающая разрешение 800x600 (1024x786 для Windows 8),Жесткий диск: 1.3 ГБ свободного пространства ',
  '/PostersAndScreenshots/Warcraft3theFrozenThrone/warcraft3.jpg',
   'TEEN'),
  ('Fallout: New Vegas',
   'Действие игры происходит в 2281 году в постапокалиптической Америке, спустя двести лет после мировой ядерной войны и вскоре после событий игры Fallout 3. Игрового персонажа, известного просто как Курьер, нанимают для доставки в город Нью-Вегас (бывший Лас-Вегас) ценной посылки, но Курьер становится жертвой ограбления и едва не погибает, а в течение дальнейшей игры пытается вернуть посылку и отомстить своим обидчикам. Игра предоставляет игроку возможность свободно исследовать Нью-Вегас и его окрестности, самостоятельно находить себе задания и участвовать в противостоянии нескольких сторон, сражающихся за власть над городом и дамбой Гувера.',
   (SELECT id FROM computer_games_e_shop_storage.game_developer_company WHERE name = 'Obsidian Entertainment'),
   2010,
  'ОС: Windows XP, 7, Vista, Процессор:  Intel Pentium 4.2 GHz, Оперативная память: 1 Гб, Видеокарта:  ATI Radeon X850 Pro/NVIDIA GeForce 6800, Жесткий диск: 7 ГБ свободного пространства',
  'ОС: Windows XP, 7, Vista, Процессор: Intel Core 2 Duo 2 GHz,Оперативная память: 2 ГБ,Видеокарта:  ATI Radeon HD 2900 GT/NVIDIA GeForce 8800 GT,Жесткий диск: 7 ГБ свободного пространства ',
  '/PostersAndScreenshots/FalloutNewVegas/FalloutNewVegas.jpg',
   'MATURE'),
  ('The Witcher 3: Wild Hunt',
   'Действие игры происходит в вымышленном фэнтезийном мире, напоминающем средневековую Европу. Главный герой Геральт из Ривии, «ведьмак» — профессиональный охотник на чудовищ — отправляется в путешествие в поисках девушки по имени Цири, обладающей сверхъестественными способностями. В отличие от предыдущих игр серии, «Ведьмак 3: Дикая Охота» — игра с открытым миром: игрок может свободно путешествовать по обширным территориям, самостоятельно находя новые места и задания.',
   (SELECT id FROM computer_games_e_shop_storage.game_developer_company WHERE name = 'CD Project RED'),
   2015,
  'ОС:  Windows 7 (64-bit), 8(8.1)(64-bit), 10 (64-bit), Процессор:  Intel CPU Core i5-2500K 3.3GHz / AMD CPU Phenom II X4 940, Оперативная память: 6 Гб, Видеокарта:  Nvidia GPU GeForce GTX 660 / AMD GPU Radeon HD 7870, Жесткий диск: 35 ГБ свободного пространства',
  'ОС: Windows 7 (64-bit), 8(8.1)(64-bit), 10 (64-bit), Процессор: Intel CPU Core i7 3770 3.4 GHz / AMD CPU AMD FX-8350 4 GHz,Оперативная память: 8 ГБ,Видеокарта:  Nvidia GPU GeForce GTX 770 / AMD GPU Radeon R9 290,Жесткий диск: 35 ГБ свободного пространства ',
  '/PostersAndScreenshots/Witcher3/Witcher3.jpg',
   'MATURE'),
  ('Tom Clancy''s The Division',
   'В 2012 году группа американских учёных и политиков запустила проект под названием «Тёмная зима» — особую программу, призванную проверить способность общества быстро реагировать на атаки био-террористов. Симуляция выявила то, как быстро всё может разрушиться, приведя ко множеству смертей и полному развалу цивилизованного общества. Эпидемия начинается во время Чёрной пятницы — дня наиболее массовых покупок в году. Прыгая с денежных банкнот и монет на людей, с игрушек на еду, от родителей к детям и обратно. А после следует распад. На первый день госпитали достигли предела. На второй день целые области становятся карантинными. На третий день биржа рушится. День четвёртый: электричество вырубилось. Вода не поступает. Полки пусты. На пятый день каждый человек становится угрозой Человечества!» Источник — Отечественное стратегическое подразделение (англ. Strategic Homeland Division) — отряд специально подготовленных агентов. Члены Подразделения, обученные действовать независимо в чрезвычайных случаях, в обычное время живут и работают как рядовые граждане: врачами, военными, инженерами, учёными, полицейскими, и т. д. Сражаясь за сохранение сообщества, агенты Подразделения оказываются втянутыми в грандиозный заговор. Теперь им необходимо не только сражаться с ужасным вирусом, но и противостоять угрозе, стоящей за его появлением.',
   (SELECT id FROM computer_games_e_shop_storage.game_developer_company WHERE name = 'Ubisoft'),
   2016,
  'ОС:  Windows 7, 8.1, 10, Процессор:  Intel Core i5-2400 / AMD FX-6100, Оперативная память: 6 Гб, Видеокарта: NVIDIA GeForce GTX 560 с 2 GB VRAM, Жесткий диск: 40 ГБ свободного пространства',
  'ОС: Windows 7, 8.1, 10, Процессор: Intel Core i7-3770 / AMD FX-8350,Оперативная память: 8 ГБ,Видеокарта: NVIDIA GeForce GTX 970 / AMD Radeon R9 290,Жесткий диск: 40 ГБ свободного пространства ',
  '/PostersAndScreenshots/TomClancyTheDivision/TomClancy''sTheDivision.jpeg',
   'MATURE'),
  ('Bastion',
   'Действие игры происходит в вымышленном мире, в котором, незадолго до начала событий игры, произошёл неизвестный катаклизм, поразивший большую часть мира. Малец (англ. The Kid), главный герой игры, после случившегося просыпается в неизвестном ему месте и начинает путь к Бастиону — месту в городе, которое должно было уцелеть. Там он встречает спасшегося Рукса (англ. Rucks, The Stranger), от лица которого ведётся повествование. Рукс рассказывает Мальцу о том, что Бастион разрушен, и для его восстановления требуется найти Ядра.',
   (SELECT id FROM computer_games_e_shop_storage.game_developer_company WHERE name = 'Supergiant Games'),
   2011,
  'ОС:  Windows XP, Vista, 7, Процессор: двухъядерный с частотой 1,7 ГГц или лучше, Оперативная память: 2 Гб, Видеокарта: с 512 МБ видеопамяти, поддержкой DirectX 9.0c и шейдеров 2.0, Жесткий диск: 1 ГБ свободного пространства',
  'ОС: Windows Vista, 7, Процессор: Intel Core 2 Duo (2.5 GHz) / AMD Athlon 64 X2 5200+,Оперативная память: 2 ГБ,Видеокарта: NVIDIA GeForce 8800 / AMD Radeon HD 3850 / 512 Mb / DirectX 9,Жесткий диск: 1 ГБ свободного пространства ',
  '/PostersAndScreenshots/Bastion/Bastion.jpg',
   'EVERYONE_10_AND_OLDER'),
  ('South Park: The Stick of Truth',
   'Ролевая игра по мультсериалу от гуру жанра Obsidian Entertainment, в России известного, как "Южный парк". Это "эпическое ролевое приключение" позволит вам лично встретится со всеми главными героями сериала, и исследовать все потаенные уголки грозного города самостоятельно. Игроки берут на себя роль ребенка, переехавшего в Южный парк и вам предстоит помочь обрести друзей и спасти Южный парк от различного рода опасностей.',
   (SELECT id FROM computer_games_e_shop_storage.game_developer_company WHERE name = 'Obsidian Entertainment'),
   2014,
  'ОС:  Windows XP SP3 / Vista SP2 / 7 SP1 / 8, Процессор: Intel Pentium Dual-Core E2180 2.0 Ghz / AMD Athlon64 X2 3800+ 2.0 Ghz, Оперативная память: 2 Гб, Видеокарта: Nvidia GeForce 8800GT / AMD Radeon HD2600XT с 512 Mb памяти, Жесткий диск: 6 ГБ свободного пространства, Звуковая карта: Совместимая с DirectX Версия DirectX: 9.0c',
  'ОС: Windows XP SP3 / Vista SP2 / 7 SP1 / 8, Процессор: Intel Core2Duo E4400 2.0 Ghz / AMD Athlon64 X2 4400+ 2.3 Ghz,Оперативная память: 4 ГБ,Видеокарта: Nvidia GeForce 9800GT / AMD Radeon HD4870 с 512 Mb памяти,Жесткий диск: 6 ГБ свободного пространства, Звуковая карта: Совместимая с DirectX Версия DirectX: 9.0c',
  '/PostersAndScreenshots/SouthParkTheStickOfTruth/SouthParkTheStickOfTruth.jpg',
   'MATURE'),
  ('Kingdom Come: Deliverance',
   'Действие игры происходит в королевстве Богемия в 1403 году на фоне конфликта между королём Вацлавом IV и его братом Сигизмундом. В начале игры половецкие наёмники Сигизмунда разоряют шахтёрское поселение Серебряная Скалица (чешск.)русск.. Главный герой Индржих, сын кузнеца, теряет родителей при налёте и поступает на службу к пану Радцигу Кобыле, возглавляющему сопротивление против Сигизмунда. В попытке отомстить убийцам родителей Индржих оказывается втянутым в заговор с целью восстановить на троне законного короля Вацлава IV. Как игра с открытым миром, Kingdom Come: Deliverance позволяет игроку свободно путешествовать по доступной ему области средневековой Чехии, самостоятельно находя интересные места и задания.',
   (SELECT id FROM computer_games_e_shop_storage.game_developer_company WHERE name = 'Warhorse'),
   2018,
  'ОС:  Windows 7 64-bit / 8(8.1) 64-bit, Процессор: Intel CPU Core i5-2500K 3,3 ГГц, AMD CPU Phenom II X4 940, Оперативная память: 6 Гб, Видеокарта: Nvidia GPU GeForce GTX 660, AMD GPU Radeon HD 7870, Жесткий диск: 30 ГБ свободного пространства ',
  'ОС: Windows 7 64-bit / 8(8.1) 64-bit, Процессор: Intel CPU Core i7 3770 3,4 ГГц, AMD CPU AMD FX-8350 4 ГГц,Оперативная память: 8 ГБ,Видеокарта: Nvidia GPU GeForce GTX 1060, AMD GPU Radeon RX 580, Жесткий диск: 30 ГБ свободного пространства ',
  '/PostersAndScreenshots/KingdomComeDeliverance/KingdomComeDeliverance.jpg',
   'MATURE'),
  ('Halo 5: Guardians',
   'Это пятая часть научно-фантастического шутера от Microsoft, продолжающая историю Мастера Чифа и его сражений с врагами человечества на просторах космоса. С момента событий Halo 4: Spartan Ops прошло восемь месяцев. В центре сюжета - две команды "спартанцев", Синяя Команда Мастера Чифа, и отряд "Осирис" Джеймсона Лока, которые противостоят друг другу. В центре противостояния - AI Кортана, которая считалась погибшей после событий четвертой части.',
   (SELECT id FROM computer_games_e_shop_storage.game_developer_company WHERE name = '343 Industries'),
   2015,
  '',
  '',
  '/PostersAndScreenshots/Halo5/Halo5.png',
   'MATURE'),
  ('Far Cry 3',
   'Действие игры происходит на тропическом острове, омываемом Индийским и Тихим океанами. Игрок принимает роль Джейсона Броди, молодого американца, отправившегося с друзьями на отдых, но в дальнейшем попавшего в плен банды пиратов. После побега он прилагает все усилия, чтобы спасти своих друзей и убить главаря банды пиратов, Вааса Монтенегро.',
   (SELECT id FROM computer_games_e_shop_storage.game_developer_company WHERE name = 'Ubisoft'),
   2012,
  'ОС:  Windows XP, Vista, 7, Процессор:  Intel Core2 Duo E6700 с тактовой частотой 2,6 ГГц или AMD Athlon64 X2 6000+ с тактовой частотой 3 ГГц или лучше, Оперативная память: 2 Гб, Видеокарта: с 512 МБ видеопамяти (с 1 ГБ видеопамяти), с поддержкой DirectX 9.0c (DirectX 11) и шейдеров моделей 3.0 (шейдеров моделей 5.0) , Жесткий диск: 15 ГБ свободного пространства ',
  'ОС: Windows XP, Vista, 7, Процессор: Intel Core2 Duo E6700 с тактовой частотой 2,6 ГГц или AMD Athlon64 X2 6000+ с тактовой частотой 3 ГГц или лучше,Оперативная память: 4 ГБ,Видеокарта: с 512 МБ видеопамяти (с 1 ГБ видеопамяти), с поддержкой DirectX 9.0c (DirectX 11) и шейдеров моделей 3.0 (шейдеров моделей 5.0), Жесткий диск: 15 ГБ свободного пространства ',
  '/PostersAndScreenshots/FarCry3/FarCry3.jpeg',
   'MATURE'),
  ('Need for Speed: Most Wanted',
   'Действия игры происходят в вымышленном городе Рокпорт, в котором игроку предоставлена свобода передвижения. По сюжету главный герой выигрывает гонки и продвигается вверх по «Чёрному списку» гонщиков, чтобы вернуть свой автомобиль BMW M3 GTR, отвоёванный Рэйзором обманным путём. Need for Speed: Most Wanted сочетает в себе уличные гонки, а также тюнинг автомобилей с оптимизацией настроек и полномасштабные полицейские преследования. Для продвижения по сюжету игрок должен выигрывать гонки «Чёрного списка» и уходить от полицейских преследований, за что зарабатывает очки, тем самым набирая рейтинг среди других гонщиков «Чёрного списка».',
   (SELECT id FROM computer_games_e_shop_storage.game_developer_company WHERE name = 'Electronic Arts'),
   2005,
  'Windows 2000/XP, процессор Pentium 4 или аналогичный с тактовой частотой 1.4 ГГц или лучше, 256 Мб оперативной памяти, 3 Гб пространства на жёстком диске, DVD-ROM дисковод, видеокарта с 32 Мб памяти, DirectX 9.0, звуковая карта с поддержкой DirectX',
  'Windows 2000/XP, процессор Pentium 4 или аналогичный с тактовой частотой 1.4 ГГц или лучше, 256 Мб оперативной памяти, 3 Гб пространства на жёстком диске, DVD-ROM дисковод, видеокарта с 32 Мб памяти, DirectX 9.0, звуковая карта с поддержкой DirectX',
  '/PostersAndScreenshots/NFSMostWanted/NFSMostWanted2012Front.jpg',
   'TEEN'),
  ('L.A. Noire',
   'Действие игры происходит в 1947 году в городе  Лос-Анджелес. Игроку, выступающему в роли детектива полиции Лос-Анджелеса  по имени Коул Фелпс, предстоит раскрыть ряд преступлений. Для этого потребуется осмотреть места, где они произошли, в поисках улик и всевозможных зацепок, которые могут привести к преступнику. Именно от успешности следственных действий и ОРМ игрока будет зависеть, насколько полно будет раскрыта картина происшествия.',
   (SELECT id FROM computer_games_e_shop_storage.game_developer_company WHERE name = 'Team Bondi'),
   2011,
  'Операционная система: Windows 7 / Windows Vista (с пакетом обновлений 1) / Windows XP (с пакетом обновлений 3)   Процессор: минимум двухъядерные (с тактовой частотой от 2,2 ГГц), вплоть до четырехъядерных (с тактовой частотой 3,2 ГГц)  Видеокарта: минимум NVIDIA GeForce 8600 GT (с 512 МБ видеопамяти), вплоть до NVIDIA GeForce GTX 580 (с 1536 МБ видеопамяти), или: минимум Radeon HD3000 (с 512 МБ видеопамяти), вплоть до Radeon HD 6850 (с 1024 МБ видеопамяти)  Оперативная память (Гб): 2.0  Свободное место на HDD (Гб): 16.0',
  'Операционная система: Windows 7 / Windows Vista (с пакетом обновлений 1) / Windows XP (с пакетом обновлений 3)   Процессор: минимум двухъядерные (с тактовой частотой от 2,2 ГГц), вплоть до четырехъядерных (с тактовой частотой 3,2 ГГц)  Видеокарта: минимум NVIDIA GeForce 8600 GT (с 512 МБ видеопамяти), вплоть до NVIDIA GeForce GTX 580 (с 1536 МБ видеопамяти), или: минимум Radeon HD3000 (с 512 МБ видеопамяти), вплоть до Radeon HD 6850 (с 1024 МБ видеопамяти)  Оперативная память (Гб): 2.0  Свободное место на HDD (Гб): 16.0',
  '/PostersAndScreenshots/LANoire/LANoire.jpg',
   'MATURE'),
  ('StarCraft 2: Wings of Liberty',
   'Сюжет игры разворачивается спустя четыре года после событий освещенных в Brood War, где королева зергов Сара Керриган нанесла сильнейший удар по войскам Объединенного Земного Директората, Вместе с Джимом Рейнором и его отрядом головорезов игрокам придется пройти множество испытаний, узнать новые истории об этом мире, а так же наконец разобраться с чувствами самого Рейнора. В Wings of Liberty игроки поближе познакомятся с Рейнором и экипажем его корабля "Гиперион", узнают о событиях, которые происходили после Войны Выводка, а так же начнут разгадку страшной тайны, которая грозит уничтожением всей Вселенной.',
   (SELECT id FROM computer_games_e_shop_storage.game_developer_company WHERE name = 'Blizzard Entertainment'),
   2010,
  'Операционная система: Windows XP/Windows 71/Windows Vista                                               Процессор: Intel Pentium 4 1 GHz                  Видеокарта: NVIDIA GeForce 7200 GS Оперативная память (Гб): 1.0',
  'Операционная система: Windows XP/Windows 71/Windows Vista                                             Процессор: Intel Pentium D 2 GHz                        Видеокарта: ATI Radeon X1600 XT/NVIDIA GeForce 7600 GT                                             Оперативная память (Гб): 2.0',
  '/PostersAndScreenshots/StarCraft2/StarCraftIIWings.jpg',
   'MATURE'),
  ('The Last of Us',
   'Действие игры происходит в  постапокалиптическом будущем на территории бывших Соединённых Штатов Америки спустя двадцать лет после глобальной пандемии, вызванной опасно мутировавшим грибком кордицепс. В течение игры её герои, контрабандист Джоэл и девочка-подросток Элли, пытаются пересечь континент, сталкиваясь в обезлюдевших городах с различными опасностями — охотниками, каннибалами и обезумевшими жертвами кордицепса, напоминающими зомби. Игру отличает высокая сложность и необходимость действовать скрытно — игрок должен по возможности избегать встреч с врагами, действуя незаметно и экономно расходуя боеприпасы. ',
   (SELECT id FROM computer_games_e_shop_storage.game_developer_company WHERE name = 'Naughty Dog Software'),
   2013,
  '',
  '',
  '/PostersAndScreenshots/TheLastOfUs/TheLastOfUs.jpg',
   'MATURE'),
  ('Heroes of Might and Magic III: The Restoration of Erathia',
   'Игровой процесс Heroes III происходит в вымышленном фэнтезийном мире, где игрок управляет основными персонажами - героями. В стратегической составляющей игры герои возглавляют отряды воинов и путешествуют по игровому миру, исследуя его и захватывая всевозможные объекты. Тактическая часть игры заключается в сражениях героев с вражескими войсками на отдельном экране. Так же игроки могут строить собственные города, развивая и укрепляя свою империю. По сравнению со второй частью, HoMM 3 получила несколько важных изменений - в частности, в игру были добавлены подземный уровень и артефакт Грааль.',
   (SELECT id FROM computer_games_e_shop_storage.game_developer_company WHERE name = 'New World Computing'),
   1999,
  'Windows 95/98 или NT 4.0 с установелнным SP4, Процессор: 133 MHz, RAM 32Mb, Звуковая карта: совмесимая с DirectX 6.0 или выше  CD-ROM Драйв: 4-х скоростной',
  'Windows 95/98 или NT 4.0 с установелнным SP4, Процессор: 133 MHz (Pentium 166) RAM 32 Mb, Звуковая карта: совмесимая с DirectX 6.0 или выше  CD-ROM Драйв: 4-х скоростной',
  '/PostersAndScreenshots/HMM3Erathia/HeroesofMightandMagic.jpg',
   'EVERYONE'),
  ('Doom',
   'Пробужденный из заточения главный герой застает Марс в руинах из-за атаки демонов, и его цель - найти и запечатать портал в параллельное измерение, откуда лезут твари. Для этого Солдату Рока предстоит пересечь множество зданий UAC, уничтожить сотни и тысячи демонов, свершив, наконец, свою тысячелетнюю месть',
   (SELECT id FROM computer_games_e_shop_storage.game_developer_company WHERE name = 'id Software'),
   2016,
  'Операционная система: Windows 7 64-бит / Windows 8.1, Процессор: Intel Core i5 с 3,3 ГГц или AMD FX-8350 4 ГГц, Видеокарта: NVIDIA GeForce GTX 660/AMD Radeon HD 7870, Оперативная память (Гб): 8.0, Свободное место на HDD (Гб): 6.0',
  'Операционная система: Windows 7 64-бит /Windows 8.1, Процессор: Intel Core i7 3,4 ГГц или AMD FX-8350 4 ГГц, Видеокарта:NVIDIA GeForce GTX 780/ AMD Radeon R9 280, Оперативная память (Гб): 8.0, Свободное место на HDD (Гб): 6.0',
  '/PostersAndScreenshots/Doom3/Doom.jpg',
   'MATURE'),
  ('Burnout Paradise',
   'Место действия Burnout Paradise - огромный открытый Парадиз-сити, в котором кроме игроков и их соперников никого больше нет. В Paradise игроки получают доступ к автопарку, в котором есть сотни машин и мотоциклов разных моделей и модификаций, к которым, впрочем, имеют доступ и соперники, что превращает каждую гонку в испытание, ибо предугадать поведение соперников становится фактически невозможно. В игре есть несколько режимов, в том числе и и режим карьеры, в которой игроки могут открывать себе новые средства передвижения.',
   (SELECT id FROM computer_games_e_shop_storage.game_developer_company WHERE name = 'Criterion Games'),
   2008,
  'Операционная система:Windows XP/Windows Vista, Процессор: Intel Pentium 4 2 GHz, Видеокарта: ATI Radeon X1300/NVIDIA GeForce 6600, Оперативная память (Гб): 1.0',
  'Операционная система: Windows XP/Windows Vista, Процессор: Intel Core 2 Duo 2 GHz, Видеокарта: ATI Radeon HD 2900 GT/NVIDIA GeForce 8800 GS, Оперативная память (Гб): 2.0',
  '/PostersAndScreenshots/BurnoutParadise/BurnoutParadise.jpg',
   'EVERYONE_10_AND_OLDER'),
  ('Mortal Kombat X',
   'Сюжет начинается с атаки демонов Нижнего Мира и нежити, поднятой Братством Тени, на Землю, чудом пережившую вторжение Шао Кана Кейдж, оставивший профессию актёра и служащий в Спецназе полевым агентом, вместе со слепым самураем-телепатом Кэнси, майором Соней Блейд и группой оперативников летят к порталу, ведущему к Храму Райдэна, где находится Дзинь-Сей — сущность Жизни и источник силы Земли. Они подвергаются атаке  Скорпиона и Призрака (Саб-Зиро), ставшим обратно человеком из-за магии Куан Чи. Бойцы спецназа погибают от рук ниндзя, а вертолёты падают, но герои добираются до портала и переносятся к Храму, где Райдэн и его соратник  Фудзин с трудом сдерживают натиск низложенного Старшего Бога Шиннока и некроманта Куан Чи, а также демонов и бывших Защитников Земли, воскрешенных в виде зомби, но все таки Шиннок добирается до Дзинь-сея и пытается заключить Райдэна и Фудзина в свой амулет, но в это время Джонни Кейдж толкает Шиннока и начинается схватка.',
   (SELECT id FROM computer_games_e_shop_storage.game_developer_company WHERE name = 'NetherRealm Studios'),
   2015,
  'ОС: Windows Vista/7/8 (только 64-разрядные системы); Процессор: Intel Core i5-750 с частотой 2,67 ГГц или AMD Phenom II X4 965 с чстотой 3,4 ГГц; Оперативная память: 3 Гбайт; Видеокарта: NVIDIA GeForce GTX 460 или AMD Radeon HD 5850; версия DirectX: 11; Свободное место на жёстком диске: 25 Гбайт',
  'ОС: Windows 7/8 (только 64-разрядные системы); Процессор: Intel Core i7-3770 с частотой 3,4 ГГц или AMD FX-8350 с частотой 4,0 ГГц; Оперативная память: 8 Гбайт; Видеокарта: NVIDIA GeForce GTX 660 или AMD Radeon HD 7950; версия DirectX: 11; Свободное место на жёстком диске: 40 Гбайт',
  '/PostersAndScreenshots/MortalKombatX/mortalkombat.jpg',
   'MATURE'),
  ('Syberia 3',
   'После того как Кейт покинула Сибирь, она едва не погибает на дрейфующей льдине, но спасается благодаря народу «юколов». Вместе они бегут от общих врагов, и Кейт принимает участие в странном обычае юколов — сопровождении «снежных страусов» в их сезонной миграции. ',
   (SELECT id FROM computer_games_e_shop_storage.game_developer_company WHERE name = 'Microids'),
   2017,
  'Операционная система: Windows XP/Windows 71/Windows Vista Процессор: Intel Celeron 2 GHz  Видеокарта: NVIDIA GeForce 6200  Оперативная память (Гб): 1.0',
  'Операционная система: Windows XP/Windows 71/Windows Vista  Процессор: Intel Celeron 2 GHz  Видеокарта: ATI Radeon X1300  Оперативная память (Гб): 1.0',
  '/PostersAndScreenshots/Syberia3/Syberia3.jpg',
   'TEEN'),
  ('NHL 18',
   'NHL 18 - это очередная часть симулятора хоккея, где появятся новые схемы управления, режимы матчей и многое другое.',
   (SELECT id FROM computer_games_e_shop_storage.game_developer_company WHERE name = 'Electronic Arts'),
   2017,
  '',
  '',
  '/PostersAndScreenshots/NHL18/nhl18.jpg',
   'EVERYONE_10_AND_OLDER');

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
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Dragon Age: Origins'), 7, 'PC'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Diablo III'), 19, 'XBOX_ONE'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Diablo III'), 12, 'PC'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Diablo III'), 18, 'XBOX_360'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Diablo III'), 20, 'PLAYSTATION_4'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Diablo III'), 17, 'PLAYSTATION_3'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Witcher 2: Assassins of Kings'), 23, 'PC'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Witcher 2: Assassins of Kings'), 25, 'XBOX_360'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Warcraft 3: The Frozen Throne'), 5, 'PC'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Fallout: New Vegas'), 15, 'PC'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Fallout: New Vegas'), 17, 'XBOX_360'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Fallout: New Vegas'), 18, 'PLAYSTATION_3'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Witcher 3: Wild Hunt'), 31, 'XBOX_ONE'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Witcher 3: Wild Hunt'), 33, 'PLAYSTATION_4'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Witcher 3: Wild Hunt'), 28, 'PC'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Tom Clancy''s The Division'), 40, 'XBOX_ONE'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Tom Clancy''s The Division'), 40, 'PLAYSTATION_4'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Tom Clancy''s The Division'), 35, 'PC'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Bastion'), 17, 'XBOX_360'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Bastion'), 17, 'XBOX_ONE'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Bastion'), 14, 'PC'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Bastion'), 18, 'PLAYSTATION_4'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'South Park: The Stick of Truth'), 29, 'PC'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'South Park: The Stick of Truth'), 33, 'PLAYSTATION_3'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'South Park: The Stick of Truth'), 33, 'XBOX_360'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'South Park: The Stick of Truth'), 36, 'XBOX_ONE'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'South Park: The Stick of Truth'), 35, 'PLAYSTATION_4'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Kingdom Come: Deliverance'), 37, 'PC'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Kingdom Come: Deliverance'), 41, 'PLAYSTATION_4'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Kingdom Come: Deliverance'), 42, 'XBOX_ONE'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Halo 5: Guardians'), 25, 'XBOX_ONE'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Far Cry 3'), 24, 'PLAYSTATION_4'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Far Cry 3'), 17, 'PC'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Far Cry 3'), 24, 'XBOX_ONE'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Far Cry 3'), 21, 'PLAYSTATION_3'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Far Cry 3'), 22, 'XBOX_360'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Need for Speed: Most Wanted'), 10, 'PC'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Need for Speed: Most Wanted'), 14, 'PLAYSTATION_3'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Need for Speed: Most Wanted'), 15, 'XBOX_360'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'L.A. Noire'), 22, 'XBOX_ONE'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'L.A. Noire'), 27, 'PLAYSTATION_3'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'L.A. Noire'), 15, 'PC'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'L.A. Noire'), 23, 'PLAYSTATION_4'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'L.A. Noire'), 20, 'NINTENDO_SWITCH'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'L.A. Noire'), 18, 'XBOX_360'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'StarCraft 2: Wings of Liberty'), 20, 'PC'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Last of Us'), 33, 'PLAYSTATION_4'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Last of Us'), 32, 'PLAYSTATION_3'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Heroes of Might and Magic III: The Restoration of Erathia'), 7, 'PC'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Doom'), 16, 'PC'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Doom'), 20, 'NINTENDO_SWITCH'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Doom'), 23, 'PLAYSTATION_4'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Doom'), 22, 'XBOX_ONE'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Burnout Paradise'), 12, 'PC'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Burnout Paradise'), 11, 'XBOX_360'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Burnout Paradise'), 13, 'PLAYSTATION_3'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Burnout Paradise'), 14, 'XBOX_ONE'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Burnout Paradise'), 17, 'PLAYSTATION_4'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mortal Kombat X'), 37, 'PC'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mortal Kombat X'), 40, 'XBOX_ONE'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mortal Kombat X'), 40, 'PLAYSTATION_4'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Syberia 3'), 10, 'PLAYSTATION_4'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Syberia 3'), 10, 'PC'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Syberia 3'), 20, 'NINTENDO_SWITCH'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Syberia 3'), 22, 'XBOX_ONE'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'NHL 18'), 28, 'XBOX_ONE'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'NHL 18'), 29, 'PLAYSTATION_4');

INSERT INTO computer_games_e_shop_storage.comment (game_id, text, user_id, date)
VALUES
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Witcher 3: Wild Hunt'), 'Отличная игра!', (SELECT id FROM computer_games_e_shop_storage.user_data WHERE login = 'User'), '2018-05-16'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Bastion'), 'Понравилась. Играю с удовольствием.', (SELECT id FROM computer_games_e_shop_storage.user_data WHERE login = 'User'), '2018-01-10'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect 3'), 'Можно поиграть', (SELECT id FROM computer_games_e_shop_storage.user_data WHERE login = 'User'), '2018-09-09'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Witcher 3: Wild Hunt'), 'Классная игра!', (SELECT id FROM computer_games_e_shop_storage.user_data WHERE login = 'Admin'), '2018-09-17'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Halo 5: Guardians'), 'Неплохая игра', (SELECT id FROM computer_games_e_shop_storage.user_data WHERE login = 'DemoLogin'), '2018-09-20'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'L.A. Noire'), 'comment', (SELECT id FROM computer_games_e_shop_storage.user_data WHERE login = 'userDemo'), '2018-09-21');

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
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Dragon Age: Origins'), '/PostersAndScreenshots/DragonAgeOrigins/Screenshots/DragonAgeOriginsScreenshot2.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Diablo III'), '/PostersAndScreenshots/Diablo3/Screenshots/diablo3Screenshot1.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Diablo III'), '/PostersAndScreenshots/Diablo3/Screenshots/diablo3Screenshot2.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Diablo III'), '/PostersAndScreenshots/Diablo3/Screenshots/diablo3Screenshot3.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Witcher 2: Assassins of Kings'), '/PostersAndScreenshots/Witcher2/Screenshots/Witcher2Screenshot1.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Witcher 2: Assassins of Kings'), '/PostersAndScreenshots/Witcher2/Screenshots/Witcher2Screenshot2.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Warcraft 3: The Frozen Throne'), '/PostersAndScreenshots/Warcraft3theFrozenThrone/Screenshots/warcraft3Screenshot1.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Warcraft 3: The Frozen Throne'), '/PostersAndScreenshots/Warcraft3theFrozenThrone/Screenshots/warcraft3Screenshot2.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Fallout: New Vegas'), '/PostersAndScreenshots/FalloutNewVegas/Screenshots/FalloutNewVegasScreenshot1.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Fallout: New Vegas'), '/PostersAndScreenshots/FalloutNewVegas/Screenshots/FalloutNewVegasScreenshot2.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Fallout: New Vegas'), '/PostersAndScreenshots/FalloutNewVegas/Screenshots/FalloutNewVegasScreenshot3.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Witcher 3: Wild Hunt'), '/PostersAndScreenshots/Witcher3/Screenshots/Witcher3Screenshot1.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Witcher 3: Wild Hunt'), '/PostersAndScreenshots/Witcher3/Screenshots/Witcher3Screenshot2.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Tom Clancy''s The Division'), '/PostersAndScreenshots/TomClancyTheDivision/Screenshots/TomClancyScreenshot1.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Tom Clancy''s The Division'), '/PostersAndScreenshots/TomClancyTheDivision/Screenshots/TomClancyScreenshot2.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Bastion'), '/PostersAndScreenshots/Bastion/Screenshots/screenshotBastion3.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Bastion'), '/PostersAndScreenshots/Bastion/Screenshots/screenshotBastion1.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Bastion'), '/PostersAndScreenshots/Bastion/Screenshots/screenshotBastion2.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'South Park: The Stick of Truth'), '/PostersAndScreenshots/SouthParkTheStickOfTruth/Screenshots/SouthParkTheStickOfTruthScreenshot1.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'South Park: The Stick of Truth'), '/PostersAndScreenshots/SouthParkTheStickOfTruth/Screenshots/SouthParkTheStickOfTruthScreenshot2.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Kingdom Come: Deliverance'), '/PostersAndScreenshots/KingdomComeDeliverance/Screenshots/KingdomComeDeliveranceScreenshot1.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Kingdom Come: Deliverance'), '/PostersAndScreenshots/KingdomComeDeliverance/Screenshots/KingdomComeDeliveranceScreenshot2.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Kingdom Come: Deliverance'), '/PostersAndScreenshots/KingdomComeDeliverance/Screenshots/KingdomComeDeliveranceScreenshot3.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Halo 5: Guardians'), '/PostersAndScreenshots/Halo5/Screenshots/halo5screenshot1.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Halo 5: Guardians'), '/PostersAndScreenshots/Halo5/Screenshots/halo5screenshot2.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Far Cry 3'), '/PostersAndScreenshots/FarCry3/Screenshots/farcry3screenshot1.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Far Cry 3'), '/PostersAndScreenshots/FarCry3/Screenshots/farcry3screenshot2.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Need for Speed: Most Wanted'), '/PostersAndScreenshots/NFSMostWanted/Screenshots/nfsscreenshot1.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'L.A. Noire'), '/PostersAndScreenshots/LANoire/Screenshots/lanoirescreenshot1.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'L.A. Noire'), '/PostersAndScreenshots/LANoire/Screenshots/lanoirescreenshot2.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'StarCraft 2: Wings of Liberty'), '/PostersAndScreenshots/StarCraft2/Screenshots/starcraft2screenshot1.jpeg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'StarCraft 2: Wings of Liberty'), '/PostersAndScreenshots/StarCraft2/Screenshots/starcraft2screenshot2.jpeg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'StarCraft 2: Wings of Liberty'), '/PostersAndScreenshots/StarCraft2/Screenshots/starcraftscreenshot3.png'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Last of Us'), '/PostersAndScreenshots/TheLastOfUs/Screenshots/TheLastOfUsscreenshot1.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Last of Us'), '/PostersAndScreenshots/TheLastOfUs/Screenshots/TheLastOfUsscreenshot2.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Heroes of Might and Magic III: The Restoration of Erathia'), '/PostersAndScreenshots/HMM3Erathia/Screenshots/hmmscreenshot1.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Heroes of Might and Magic III: The Restoration of Erathia'), '/PostersAndScreenshots/HMM3Erathia/Screenshots/hmmscreenshot2.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Doom'), '/PostersAndScreenshots/Doom3/Screenshots/doomscreenshot1.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Doom'), '/PostersAndScreenshots/Doom3/Screenshots/doomscreenshot2.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Burnout Paradise'), '/PostersAndScreenshots/BurnoutParadise/Screenshots/burnoutparadise1.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Burnout Paradise'), '/PostersAndScreenshots/BurnoutParadise/Screenshots/burnoutparadise2.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mortal Kombat X'), '/PostersAndScreenshots/MortalKombatX/Screenshots/mortalkombatscreenshot1.jpeg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mortal Kombat X'), '/PostersAndScreenshots/MortalKombatX/Screenshots/mortalkombatscreenshot2.jpeg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Syberia 3'), '/PostersAndScreenshots/Syberia3/Screenshots/syberia3screenshot1.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Syberia 3'), '/PostersAndScreenshots/Syberia3/Screenshots/syberia3screenshot2.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'NHL 18'), '/PostersAndScreenshots/NHL18/Screenshots/nhl18screenshot1.jpg'),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'NHL 18'), '/PostersAndScreenshots/NHL18/Screenshots/nhl18screenshot2.jpg');

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
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Dragon Age: Origins'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'CRPG')),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Diablo III'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'Action/RPG')),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Witcher 2: Assassins of Kings'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'Hack and Slash')),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Witcher 2: Assassins of Kings'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'CRPG')),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Warcraft 3: The Frozen Throne'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'Стратегия в реальном времени')),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Fallout: New Vegas'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'Action/RPG')),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Witcher 3: Wild Hunt'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'Action/RPG')),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Tom Clancy''s The Division'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'Action/RPG')),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Tom Clancy''s The Division'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'Шутер от третьего лица')),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Bastion'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'Action/RPG')),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'South Park: The Stick of Truth'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'CRPG')),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Kingdom Come: Deliverance'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'Action/RPG')),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Halo 5: Guardians'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'Шутер от первого лица')),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Far Cry 3'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'Шутер от первого лица')),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Need for Speed: Most Wanted'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'Автогонки')),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'L.A. Noire'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'Action-adventure')),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'StarCraft 2: Wings of Liberty'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'Стратегия в реальном времени')),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Last of Us'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'Action-adventure')),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Heroes of Might and Magic III: The Restoration of Erathia'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'Пошаговая стратегия')),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Doom'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'Шутер от первого лица')),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Burnout Paradise'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'Автогонки')),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mortal Kombat X'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'Файтинг')),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Syberia 3'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'Квест')),
  ((SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'NHL 18'), (SELECT id FROM computer_games_e_shop_storage.subgenre WHERE name = 'Спортивный симулятор'));

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
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Dragon Age: Origins') AND game_platform = 'PLAYSTATION_3')),
  (8, '2018-02-01',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Diablo III') AND game_platform = 'PLAYSTATION_3')),
  (11, '2018-07-05',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Diablo III') AND game_platform = 'PC')),
  (10, '2018-07-09',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Diablo III') AND game_platform = 'XBOX_360')),
  (5, '2018-04-12',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Diablo III') AND game_platform = 'PLAYSTATION_4')),
  (4, '2018-04-29',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Diablo III') AND game_platform = 'XBOX_ONE')),
  (3, '2018-09-20',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Witcher 2: Assassins of Kings') AND game_platform = 'XBOX_360')),
  (19, '2018-09-20',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Witcher 2: Assassins of Kings') AND game_platform = 'PC')),
  (6, '2018-07-05',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Warcraft 3: The Frozen Throne') AND game_platform = 'PC')),
  (12, '2018-01-05',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Fallout: New Vegas') AND game_platform = 'PC')),
  (15, '2018-01-11',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Fallout: New Vegas') AND game_platform = 'XBOX_360')),
  (10, '2018-01-24',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Fallout: New Vegas') AND game_platform = 'PLAYSTATION_3')),
  (3, '2018-08-12',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Witcher 3: Wild Hunt') AND game_platform = 'PC')),
  (0, '2018-07-14',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Witcher 3: Wild Hunt') AND game_platform = 'PLAYSTATION_4')),
  (4, '2018-07-28',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Witcher 3: Wild Hunt') AND game_platform = 'XBOX_ONE')),
  (2, '2018-03-03',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Tom Clancy''s The Division') AND game_platform = 'PC')),
  (14, '2018-02-18',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Tom Clancy''s The Division') AND game_platform = 'XBOX_ONE')),
  (13, '2018-02-02',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Tom Clancy''s The Division') AND game_platform = 'PLAYSTATION_4')),
  (3, '2018-02-05',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Bastion') AND game_platform = 'PC')),
  (0, '2018-02-22',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Bastion') AND game_platform = 'XBOX_360')),
  (1, '2018-09-01',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Bastion') AND game_platform = 'PLAYSTATION_4')),
  (9, '2018-05-30',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Bastion') AND game_platform = 'XBOX_ONE')),
  (15, '2018-05-11',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'South Park: The Stick of Truth') AND game_platform = 'PC')),
  (4, '2018-08-01',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'South Park: The Stick of Truth') AND game_platform = 'PLAYSTATION_4')),
  (3, '2018-08-09',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'South Park: The Stick of Truth') AND game_platform = 'XBOX_ONE')),
  (17, '2018-08-14',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'South Park: The Stick of Truth') AND game_platform = 'XBOX_360')),
  (11, '2018-08-20',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'South Park: The Stick of Truth') AND game_platform = 'PLAYSTATION_3')),
  (2, '2018-03-07',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Kingdom Come: Deliverance') AND game_platform = 'PC')),
  (7, '2018-03-29',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Kingdom Come: Deliverance') AND game_platform = 'XBOX_ONE')),
  (12, '2018-03-30',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Kingdom Come: Deliverance') AND game_platform = 'PLAYSTATION_4')),
  (3, '2018-09-21',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Halo 5: Guardians') AND game_platform = 'XBOX_ONE')),
  (0, '2018-09-21',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Far Cry 3') AND game_platform = 'PC')),
  (3, '2018-09-20',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Far Cry 3') AND game_platform = 'PLAYSTATION_4')),
  (8, '2018-04-16',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Far Cry 3') AND game_platform = 'XBOX_360')),
  (15, '2018-05-08',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Far Cry 3') AND game_platform = 'PLAYSTATION_3')),
  (11, '2018-09-07',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Far Cry 3') AND game_platform = 'XBOX_ONE')),
  (2, '2018-09-20',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Need for Speed: Most Wanted') AND game_platform = 'PC')),
  (7, '2018-09-20',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Need for Speed: Most Wanted') AND game_platform = 'PLAYSTATION_3')),
  (5, '2018-09-20',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Need for Speed: Most Wanted') AND game_platform = 'XBOX_360')),
  (6, '2018-09-20',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'L.A. Noire') AND game_platform = 'XBOX_ONE')),
  (3, '2018-09-20',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'L.A. Noire') AND game_platform = 'NINTENDO_SWITCH')),
  (9, '2018-09-20',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'L.A. Noire') AND game_platform = 'PLAYSTATION_4')),
  (5, '2018-09-20',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'L.A. Noire') AND game_platform = 'PC')),
  (10, '2018-09-20',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'L.A. Noire') AND game_platform = 'PLAYSTATION_3')),
  (2, '2018-09-20',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'L.A. Noire') AND game_platform = 'XBOX_360')),
  (3, '2018-09-20',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'StarCraft 2: Wings of Liberty') AND game_platform = 'PC')),
  (4, '2018-09-20',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Last of Us') AND game_platform = 'PLAYSTATION_3')),
  (2, '2018-09-20',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Last of Us') AND game_platform = 'PLAYSTATION_4')),
  (7, '2018-09-21',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Heroes of Might and Magic III: The Restoration of Erathia') AND game_platform = 'PC')),
  (4, '2018-09-21',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Doom') AND game_platform = 'PLAYSTATION_4')),
  (7, '2018-09-21',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Doom') AND game_platform = 'NINTENDO_SWITCH')),
  (2, '2018-09-21',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Doom') AND game_platform = 'PC')),
  (0, '2018-09-21',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Doom') AND game_platform = 'XBOX_ONE')),
  (0, '2018-09-21',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Burnout Paradise') AND game_platform = 'PLAYSTATION_3')),
  (2, '2018-09-21',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Burnout Paradise') AND game_platform = 'XBOX_ONE')),
  (10, '2018-09-21',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Burnout Paradise') AND game_platform = 'XBOX_360')),
  (5, '2018-09-21',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Burnout Paradise') AND game_platform = 'PC')),
  (1, '2018-09-21',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Burnout Paradise') AND game_platform = 'PLAYSTATION_4')),
  (2, '2018-09-21',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mortal Kombat X') AND game_platform = 'PC')),
  (3, '2018-09-21',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mortal Kombat X') AND game_platform = 'XBOX_ONE')),
  (5, '2018-09-21',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mortal Kombat X') AND game_platform = 'PLAYSTATION_4')),
  (4, '2018-09-25',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Syberia 3') AND game_platform = 'PLAYSTATION_4')),
  (8, '2018-09-21',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Syberia 3') AND game_platform = 'PC')),
  (0, '2018-09-21',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Syberia 3') AND game_platform = 'NINTENDO_SWITCH')),
  (1, '2018-09-21',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Syberia 3') AND game_platform = 'XBOX_ONE')),
  (8, '2018-09-25',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'NHL 18') AND game_platform = 'XBOX_ONE')),
  (9, '2018-09-25',
   (SELECT id FROM computer_games_e_shop_storage.game_game_platform
   WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'NHL 18') AND game_platform = 'PLAYSTATION_4'));

INSERT INTO computer_games_e_shop_storage.order_data (user_id, delivery_method, payment_form, condition, date, delivery_date)
VALUES
  ((SELECT id FROM computer_games_e_shop_storage.user_data WHERE login = 'User'), 'PICKUP', 'CASH', 'DELIVERED_AND_PAID', '2017-12-15', '2017-12-17'),
  ((SELECT id FROM computer_games_e_shop_storage.user_data WHERE login = 'User'), 'COURIER', 'CARD', 'DELIVERED_AND_PAID', '2018-02-19', '2018-02-20'),
  ((SELECT id FROM computer_games_e_shop_storage.user_data WHERE login = 'Admin'), 'COURIER', 'CASH', 'COMPLETING', '2018-09-18', '2018-09-23'),
  ((SELECT id FROM computer_games_e_shop_storage.user_data WHERE login = 'User'), 'PICKUP', 'CARD', 'COMPLETING', '2018-09-18', '2018-09-20'),
  ((SELECT id FROM computer_games_e_shop_storage.user_data WHERE login = 'DemoLogin'), 'PICKUP', 'CASH', 'ACCEPTED', '2018-09-20', null),
  ((SELECT id FROM computer_games_e_shop_storage.user_data WHERE login = 'userDemo'), 'POST', 'CASH', 'COMPLETING', '2018-09-21', '2018-09-30'),
  ((SELECT id FROM computer_games_e_shop_storage.user_data WHERE login = 'Cake'), 'EXPRESS', 'CARD', 'COMPLETING', '2018-09-25', null);

INSERT INTO computer_games_e_shop_storage.items_in_order (order_id, number, game_game_platform_id)
VALUES
  ((SELECT id FROM computer_games_e_shop_storage.order_data WHERE user_id = (SELECT id FROM computer_games_e_shop_storage.user_data WHERE login = 'User') AND date = '2017-12-15'),
   1, (SELECT id FROM computer_games_e_shop_storage.game_game_platform
        WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Elder Scrolls V: Skyrim') AND game_platform = 'PLAYSTATION_4')),
  ((SELECT id FROM computer_games_e_shop_storage.order_data WHERE user_id = (SELECT id FROM computer_games_e_shop_storage.user_data WHERE login = 'User') AND date = '2017-12-15'),
   3, (SELECT id FROM computer_games_e_shop_storage.game_game_platform
        WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Diablo III') AND game_platform = 'PLAYSTATION_4')),
  ((SELECT id FROM computer_games_e_shop_storage.order_data WHERE user_id = (SELECT id FROM computer_games_e_shop_storage.user_data WHERE login = 'User') AND date = '2018-02-19'),
   1, (SELECT id FROM computer_games_e_shop_storage.game_game_platform
        WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Dragon Age: Origins') AND game_platform = 'PC')),
  ((SELECT id FROM computer_games_e_shop_storage.order_data WHERE user_id = (SELECT id FROM computer_games_e_shop_storage.user_data WHERE login = 'User') AND date = '2018-02-19'),
   1, (SELECT id FROM computer_games_e_shop_storage.game_game_platform
        WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Kingdom Come: Deliverance') AND game_platform = 'PC')),
  ((SELECT id FROM computer_games_e_shop_storage.order_data WHERE user_id = (SELECT id FROM computer_games_e_shop_storage.user_data WHERE login = 'User') AND date = '2018-02-19'),
   1, (SELECT id FROM computer_games_e_shop_storage.game_game_platform
        WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Bastion') AND game_platform = 'PC')),
  ((SELECT id FROM computer_games_e_shop_storage.order_data WHERE user_id = (SELECT id FROM computer_games_e_shop_storage.user_data WHERE login = 'Admin') AND date = '2018-09-18'),
   1, (SELECT id FROM computer_games_e_shop_storage.game_game_platform
        WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'The Elder Scrolls V: Skyrim') AND game_platform = 'PLAYSTATION_4')),
  ((SELECT id FROM computer_games_e_shop_storage.order_data WHERE user_id = (SELECT id FROM computer_games_e_shop_storage.user_data WHERE login = 'User') AND date = '2018-09-18'),
   1, (SELECT id FROM computer_games_e_shop_storage.game_game_platform
        WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Mass Effect 2') AND game_platform = 'PLAYSTATION_3')),
  ((SELECT id FROM computer_games_e_shop_storage.order_data WHERE user_id = (SELECT id FROM computer_games_e_shop_storage.user_data WHERE login = 'DemoLogin') AND date = '2018-09-20'),
   1, (SELECT id FROM computer_games_e_shop_storage.game_game_platform
        WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Far Cry 3') AND game_platform = 'PLAYSTATION_4')),
  ((SELECT id FROM computer_games_e_shop_storage.order_data WHERE user_id = (SELECT id FROM computer_games_e_shop_storage.user_data WHERE login = 'userDemo') AND date = '2018-09-21'),
   1, (SELECT id FROM computer_games_e_shop_storage.game_game_platform
        WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Halo 5: Guardians') AND game_platform = 'XBOX_ONE')),
  ((SELECT id FROM computer_games_e_shop_storage.order_data WHERE user_id = (SELECT id FROM computer_games_e_shop_storage.user_data WHERE login = 'userDemo') AND date = '2018-09-21'),
   1, (SELECT id FROM computer_games_e_shop_storage.game_game_platform
        WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Far Cry 3') AND game_platform = 'PC')),
  ((SELECT id FROM computer_games_e_shop_storage.order_data WHERE user_id = (SELECT id FROM computer_games_e_shop_storage.user_data WHERE login = 'Cake') AND date = '2018-09-25'),
   1, (SELECT id FROM computer_games_e_shop_storage.game_game_platform
        WHERE game_id = (SELECT id FROM computer_games_e_shop_storage.game WHERE name = 'Syberia 3') AND game_platform = 'PLAYSTATION_4'));

