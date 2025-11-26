CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE profile (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    nickname VARCHAR(255),
    name_en VARCHAR(255),
    intro VARCHAR(255),
    bio TEXT,
    image_name VARCHAR(255),
    mail VARCHAR(255),
    github VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_profile_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE hobbies (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    name VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_hobby_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE careers (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    year VARCHAR(255),
    name VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_career_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE events (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    year VARCHAR(255),
    name VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_events_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE certificates (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    year VARCHAR(255),
    name VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_certificate_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);


CREATE TABLE languages (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    level VARCHAR(255),
    experience VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_language_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE frameworks (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    level VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_framework_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE other_skills (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    level VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_other_skill_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);


CREATE TABLE works (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    tech_stack VARCHAR(255),
    url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_work_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

--- 管理者
INSERT INTO users (username, password) VALUES
('kimu', '$2a$08$fOK0FtLxN9.x80fftzN35uJ.5SId/xoiCmVOURMBF4FbfSMBknG36')
;

INSERT INTO profile (user_id, name, nickname, name_en, intro, bio, image_name, mail, github) VALUES
(1, '木村勇紀', 'kimu', 'Yuki Kimura', '駆け出しエンジニア', '東京のIT企業に勤めています。Javaを中心にバックエンドの開発をしてきましたが、最近はVue、TypeScriptを使用したフロントエンドの開発もしています。詳しいスキルや経験はヘッダーのスキルを見てください。ポートフォリオ兼、今後制作するであろう成果物をまとめるために制作しました。', 'akagi.png', 'keykimu1999@gmail.com', 'https://github.com/keykimu')
;

INSERT INTO hobbies (user_id, name) VALUES
(1, 'アニメ・ゲーム（Key）'),
(1, 'ライブ参戦（水樹奈々）'),
(1, 'ボードゲーム（麻雀・将棋・花札）'),
(1, 'お酒（特に日本酒）')
;

INSERT INTO careers (user_id,year, name) VALUES
(1, '2022', '専門学校　卒業'),
(1, '2022', '都内IT系企業　入社')
;

INSERT INTO certificates (user_id, year, name) VALUES
(1, '2017', '経済産業省　ITパスポート試験'),
(1, '2019', '経済産業省　基本情報技術者試験'),
(1, '2020', '経済産業省　応用情報技術者試験'),
(1, '2022', ' Oracle Java Silver（Java SE 11 Programmer I）')
;


INSERT INTO languages (user_id, name, level, experience) VALUES
(1, 'Java', '実務経験あり(Java Silver取得できる程度)', '実務4年'),
(1, 'JavaScript', '実務経験あり', '実務2年'),
(1, 'TypeScript', '型を意識して書ける', '実務1年'),
(1, 'Python', '基本構文は書ける', '実務1年'),
(1, 'C#', '簡単なデスクトップアプリを制作', '趣味'),
(1, 'VB', '簡単なデスクトップアプリを制作', '趣味'),
(1, 'C', 'ポインタで挫折', '趣味'),
(1, 'Go', 'Hello Worldした程度', '趣味'),
(1, 'Rust', 'Hello Worldした程度', '趣味')
;

INSERT INTO frameworks (user_id, name, level) VALUES
(1, 'Spring Boot', 'API,Spring MVCでの実務経験あり'),
(1, 'Vue.js', 'フロント開発経験あり')
;

INSERT INTO other_skills (user_id, name, level) VALUES
(1, 'PostgreSQL', '実務経験あり'),
(1, 'Docker', '実務経験あり'),
(1, 'AWS(EC2)', '社内用サイトをデプロイした')
;


INSERT INTO works (user_id, title, description, tech_stack, url) VALUES
(1, 'ポートフォリオサイト作成', '本ポートフォリオサイトを作成', 'Vue, TypeScript, Spring Boot', 'portfolio.png')
;