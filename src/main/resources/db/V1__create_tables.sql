CREATE TABLE "user" (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE profile (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    nickname VARCHAR(255),
    name_en VARCHAR(255),
    intro VARCHAR(255),
    bio TEXT,
    mail VARCHAR(255),
    github VARCHAR(255)
);

CREATE TABLE hobby (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE career (
    id SERIAL PRIMARY KEY,
    year VARCHAR(255),
    name VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE events (
    id SERIAL PRIMARY KEY,
    year VARCHAR(255),
    name VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE certificate (
    id SERIAL PRIMARY KEY,
    year VARCHAR(255),
    name VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE language (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    level VARCHAR(255),
    experience VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE framework (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    level VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE other_skill (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    level VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE work (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    tech_stack VARCHAR(255),
    url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO "user" (username, password) VALUES
('admin','$2a$08$mELZ4fCCdV09zRm8DmZIYutbJzXGYYWywyiidv4w7Ue81y1YNioXm');

INSERT INTO profile (name, nickname, name_en, intro, bio, mail, github) VALUES
('木村勇紀', 'kimu', 'Yuki Kimura', '駆け出しエンジニア', '東京のIT企業に勤めています。Javaを中心にバックエンドの開発をしてきましたが、最近はVue、TypeScriptを使用したフロントエンドの開発もしています。詳しいスキルや経験はヘッダーのスキルを見てください。ポートフォリオ兼、今後制作するであろう成果物をまとめるために制作しました。', 'keykimu1999@gmail.com', 'https://github.com/keykimu');

INSERT INTO hobby (name) VALUES
('アニメ・ゲーム（Key）'),
('ライブ参戦（水樹奈々）'),
('ボードゲーム（麻雀・将棋・花札）');

INSERT INTO career (year, name) VALUES
('2018', '商業高校　卒業'),
('2022', '専門学校　卒業');

INSERT INTO events (year, name) VALUES
('2022', '(TEST)Hackathon Winner'),
('2023', '(TEST)Tech Conference Speaker');

INSERT INTO certificate (year, name) VALUES
('2017', '経済産業省　ITパスポート試験'),
('2019', '経済産業省　基本情報技術者試験'),
('2020', '経済産業省　応用情報技術者試験'),
('2021', '普通自動車第一種運転免許');


INSERT INTO language (name, level, experience) VALUES
('Java', '実務経験あり(Java Silver取得できる程度)', '実務4年'),
('TypeScript', '型を意識して書ける', '実務1年');

INSERT INTO framework (name, level) VALUES
('Spring Boot', 'API,Spring MVCでの実務経験あり'),
('Vue.js', 'フロント開発経験あり');

INSERT INTO other_skill (name, level) VALUES
('PostgreSQL', '実務経験あり'),
('Docker', '実務経験あり');


INSERT INTO work (title, description, tech_stack, url) VALUES
('成果物1', '説明文1', 'Vue, TypeScript, Spring Boot', '@/assets/no_image.png'),
('成果物2', '説明文2', 'Vue, TypeScript, Spring Boot', '@/assets/no_image.png');
