CREATE TABLE camera (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        latitude DOUBLE,
                        longitude DOUBLE,
                        scenery VARCHAR(255)
);

CREATE TABLE categories (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            is_mobile BOOLEAN NOT NULL,
                            name VARCHAR(10) NOT NULL,
                            eng_name VARCHAR(20) NOT NULL
);

CREATE TABLE detected_object (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 category_id BIGINT,
                                 dtype VARCHAR(31) NOT NULL,
                                 alias VARCHAR(255),
                                 crop_img_url VARCHAR(255) NOT NULL,
                                 feature VARCHAR(255),
                                 FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE report (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        created_at TIMESTAMP,
                        content VARCHAR(255),
                        issuer VARCHAR(255)
);

CREATE TABLE video (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       created_at TIMESTAMP,
                       video_url VARCHAR(255) NOT NULL,
                       summary BLOB NOT NULL -- oid 대신 BLOB 사용
);

CREATE TABLE detection (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           appeared_time TIMESTAMP NOT NULL,
                           discovered_time TIMESTAMP NOT NULL,
                           camera_id BIGINT NOT NULL,
                           detected_object_id BIGINT NOT NULL,
                           video_id BIGINT NOT NULL,
                           thumbnail_url VARCHAR(255),
                           FOREIGN KEY (camera_id) REFERENCES camera(id),
                           FOREIGN KEY (detected_object_id) REFERENCES detected_object(id),
                           FOREIGN KEY (video_id) REFERENCES video(id)
);

CREATE TABLE video_report (
                              id BIGINT PRIMARY KEY,
                              report_id BIGINT,
                              video_id BIGINT,
                              FOREIGN KEY (report_id) REFERENCES report(id),
                              FOREIGN KEY (video_id) REFERENCES video(id)
);
