CREATE TABLE analyzer_info (
                               analyzer_id IDENTITY PRIMARY KEY,
                               analyzer_ip VARCHAR(255) NOT NULL,
                               analyzer_port INTEGER NOT NULL,
                               analyzer_name VARCHAR(255) NOT NULL,
                               registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE event_risks (
                             risk_id IDENTITY PRIMARY KEY,
                             risk_level VARCHAR(30) NOT NULL
);

CREATE TABLE roles (
                       role_id IDENTITY PRIMARY KEY,
                       ROLE VARCHAR(30) NOT NULL,
                       role_name VARCHAR(30) NOT NULL
);

CREATE TABLE camera_group (
                              group_id IDENTITY PRIMARY KEY,
                              group_name VARCHAR(100) NOT NULL
);

CREATE TABLE directory_info (
                                dir_id IDENTITY PRIMARY KEY,
                                tile_map_json_dir VARCHAR(500),
                                tile_map_base_dir VARCHAR(500),
                                event_info_meta_dir VARCHAR(500),
                                event_info_image_dir VARCHAR(500)
);

CREATE TABLE server_settings (
                                 setting_id IDENTITY PRIMARY KEY,
                                 address VARCHAR(255),
                                 address_detail VARCHAR(255),
                                 login_logo_name VARCHAR(255),
                                 login_logo_image CLOB,
                                 login_content_image CLOB
);

CREATE TABLE event_codes (
                             code_id IDENTITY PRIMARY KEY,
                             code_name VARCHAR(255) NOT NULL,
                             sub_code INTEGER,
                             sub_code_name VARCHAR(255),
                             risk_id INTEGER NOT NULL,
                             FOREIGN KEY (risk_id) REFERENCES event_risks(risk_id) ON DELETE CASCADE
);

CREATE TABLE user_account (
                              user_id VARCHAR(100) PRIMARY KEY,
                              PASSWORD VARCHAR(255) NOT NULL,
                              name VARCHAR(30) NOT NULL,
                              department_name VARCHAR(100),
                              position_title VARCHAR(100),
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              role_id INTEGER NOT NULL,
                              FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE
);

CREATE TABLE monitoring_settings (
                                     setting_id IDENTITY PRIMARY KEY,
                                     setting_name VARCHAR(100) NOT NULL,
                                     camera_ids CLOB NOT NULL,
                                     split_type INTEGER NOT NULL,
                                     registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     user_id VARCHAR(100) NOT NULL,
                                     FOREIGN KEY (user_id) REFERENCES user_account(user_id) ON DELETE CASCADE
);

CREATE TABLE camera_info (
                             camera_id IDENTITY PRIMARY KEY,
                             camera_ip VARCHAR(255) NOT NULL,
                             camera_port INTEGER NOT NULL,
                             lat NUMERIC,
                             lon NUMERIC,
                             location_name VARCHAR(255),
                             location_address VARCHAR(255),
                             location_address_detail VARCHAR(255),
                             rtsp_id VARCHAR(100),
                             rtsp_password VARCHAR(255),
                             stream_path VARCHAR(500),
                             camera_name VARCHAR(255) NOT NULL,
                             detection_days CLOB DEFAULT '{"MON":1,"TUE":1,"WED":1,"THU":1,"FRI":1,"SAT":1,"SUN":1}',
                             detection_start_time TIME DEFAULT '00:00:00',
                             detection_end_time TIME DEFAULT '23:59:59',
                             registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             group_id INTEGER,
                             analyzer_id INTEGER NOT NULL,
                             FOREIGN KEY (group_id) REFERENCES camera_group(group_id) ON DELETE SET NULL,
                             FOREIGN KEY (analyzer_id) REFERENCES analyzer_info(analyzer_id)
);

CREATE TABLE camera_area_setting (
                                     area_setting_id IDENTITY PRIMARY KEY,
                                     polygon CLOB NOT NULL,
                                     area_name VARCHAR(100) NOT NULL,
                                     border_color VARCHAR(20) NOT NULL,
                                     fill_color VARCHAR(20) NOT NULL,
                                     fill_opacity INTEGER NOT NULL,
                                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     camera_id INTEGER NOT NULL,
                                     FOREIGN KEY (camera_id) REFERENCES camera_info(camera_id) ON DELETE CASCADE
);

CREATE TABLE camera_setting (
                                setting_id IDENTITY PRIMARY KEY,
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                camera_id INTEGER NOT NULL,
                                code_id INTEGER NOT NULL,
                                FOREIGN KEY (camera_id) REFERENCES camera_info(camera_id) ON DELETE CASCADE,
                                FOREIGN KEY (code_id) REFERENCES event_codes(code_id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX idx_camera_setting_unique ON camera_setting (camera_id, code_id);

CREATE TABLE camera_detail_setting (
                                       detail_setting_id IDENTITY PRIMARY KEY,
                                       setting_id INTEGER NOT NULL,
                                       area_setting_id INTEGER NOT NULL,
                                       FOREIGN KEY (setting_id) REFERENCES camera_setting(setting_id) ON DELETE CASCADE,
                                       FOREIGN KEY (area_setting_id) REFERENCES camera_area_setting(area_setting_id) ON DELETE CASCADE
);

CREATE TABLE event_info (
                            event_id IDENTITY PRIMARY KEY,
                            event_uuid VARCHAR(255),
                            camera_id INTEGER NOT NULL,
                            bounding_box CLOB NOT NULL,
                            start_frame_at TIMESTAMP NOT NULL,
                            end_frame_at TIMESTAMP,
                            code_id INTEGER NOT NULL,
                            FOREIGN KEY (camera_id) REFERENCES camera_info(camera_id),
                            FOREIGN KEY (code_id) REFERENCES event_codes(code_id) ON DELETE CASCADE
);

CREATE TABLE user_account_history (
                                      history_id IDENTITY PRIMARY KEY,
                                      target_user_id VARCHAR(100),
                                      actor_user_id VARCHAR(100) NOT NULL,
                                      action_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      action_ip VARCHAR(255) NOT NULL,
                                      action_type VARCHAR(30) NOT NULL,
                                      action_result VARCHAR(20) NOT NULL
);

CREATE TABLE video (
                       id IDENTITY PRIMARY KEY,
                       video_url VARCHAR(255),
                       start_time TIMESTAMP,
                       end_time TIMESTAMP,
                       camera_id BIGINT NOT NULL,
                       thumbnail_url VARCHAR(255),
                       event_id BIGINT,
                       FOREIGN KEY (camera_id) REFERENCES camera_info(camera_id),
                       FOREIGN KEY (event_id) REFERENCES event_info(event_id)
);

CREATE TABLE detected_object (
                                 id IDENTITY PRIMARY KEY,
                                 alias VARCHAR(255),
                                 crop_img_url VARCHAR(255),
                                 feature VARCHAR(255),
                                 code_id BIGINT NOT NULL,
                                 FOREIGN KEY (code_id) REFERENCES event_codes(code_id)
);

CREATE TABLE detection (
                           id IDENTITY PRIMARY KEY,
                           detected_object_id BIGINT,
                           appeared_time TIMESTAMP,
                           exit_time TIME,
                           video_id BIGINT NOT NULL,
                           FOREIGN KEY (video_id) REFERENCES video(id),
                           FOREIGN KEY (detected_object_id) REFERENCES detected_object(id)
);
