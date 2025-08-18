-- analyzer_info
DROP TABLE IF EXISTS analyzer_info CASCADE;
CREATE TABLE analyzer_info (
  analyzer_id SERIAL PRIMARY KEY,
  analyzer_ip VARCHAR(255) NOT NULL,
  analyzer_port INTEGER NOT NULL,
  analyzer_name VARCHAR(255) NOT NULL,
  registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- event_risks
DROP TABLE IF EXISTS event_risks CASCADE;
CREATE TABLE event_risks (
  risk_id SERIAL PRIMARY KEY,
  risk_level VARCHAR(30) NOT NULL
);

-- roles
DROP TABLE IF EXISTS roles CASCADE;
CREATE TABLE roles (
  role_id SERIAL PRIMARY KEY,
  ROLE VARCHAR(30) NOT NULL,
  role_name VARCHAR(30) NOT NULL
);

-- camera_group
DROP TABLE IF EXISTS camera_group CASCADE;
CREATE TABLE camera_group (
  group_id SERIAL PRIMARY KEY,
  group_name VARCHAR(100) NOT NULL
);

-- directory_info
DROP TABLE IF EXISTS directory_info CASCADE;
CREATE TABLE directory_info (
  dir_id SERIAL PRIMARY KEY,
  tile_map_json_dir VARCHAR(500),
  tile_map_base_dir VARCHAR(500),
  event_info_meta_dir VARCHAR(500),
  event_info_image_dir VARCHAR(500)
);

-- server_settings
DROP TABLE IF EXISTS server_settings CASCADE;
CREATE TABLE server_settings (
  setting_id SERIAL PRIMARY KEY,
  address VARCHAR(255),
  address_detail VARCHAR(255),
  login_logo_name VARCHAR(255),
  login_logo_image TEXT,
  login_content_image TEXT
);

-- event_codes
DROP TABLE IF EXISTS event_codes CASCADE;
CREATE TABLE event_codes (
  code_id SERIAL PRIMARY KEY,
  code_name VARCHAR(255) NOT NULL,
  sub_code INTEGER,
  sub_code_name VARCHAR(255),
  risk_id INTEGER NOT NULL,
  CONSTRAINT fk_event_codes_risk FOREIGN KEY (risk_id) REFERENCES event_risks(risk_id) ON DELETE CASCADE
);

-- user_account
DROP TABLE IF EXISTS user_account CASCADE;
CREATE TABLE user_account (
  user_id VARCHAR(100) PRIMARY KEY,
  PASSWORD VARCHAR(255) NOT NULL,
  name VARCHAR(30) NOT NULL,
  department_name VARCHAR(100),
  position VARCHAR(255),
  position_title VARCHAR(100),
  created_at TIMESTAMP,
  role_id INTEGER NOT NULL,
  CONSTRAINT fk_user_account_role FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE
);

-- monitoring_settings
DROP TABLE IF EXISTS monitoring_settings CASCADE;
CREATE TABLE monitoring_settings (
  setting_id SERIAL PRIMARY KEY,
  setting_name VARCHAR(100) NOT NULL,
  camera_ids TEXT NOT NULL,
  split_type INTEGER NOT NULL,
  registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  user_id VARCHAR(100) NOT NULL,
  CONSTRAINT fk_monitoring_settings_user FOREIGN KEY (user_id) REFERENCES user_account(user_id) ON DELETE CASCADE
);

-- camera_info
DROP TABLE IF EXISTS camera_info CASCADE;
CREATE TABLE camera_info (
  camera_id SERIAL PRIMARY KEY,
  camera_ip VARCHAR(255) NOT NULL,
  camera_port INTEGER NOT NULL,
  lat double precision,
  lon double precision,
  location_name VARCHAR(255),
  location_address VARCHAR(255),
  location_address_detail VARCHAR(255),
  rtsp_id VARCHAR(100),
  rtsp_password VARCHAR(255),
  stream_path VARCHAR(500),
  camera_name VARCHAR(255) NOT NULL,
  detection_days TEXT DEFAULT '{"MON":1,"TUE":1,"WED":1,"THU":1,"FRI":1,"SAT":1,"SUN":1}',
  detection_start_time TIME DEFAULT '00:00:00',
  detection_end_time TIME DEFAULT '23:59:59',
  registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  group_id INTEGER,
  analyzer_id INTEGER NOT NULL,
  CONSTRAINT fk_camera_info_group FOREIGN KEY (group_id) REFERENCES camera_group(group_id) ON DELETE SET NULL,
  CONSTRAINT fk_camera_info_analyzer FOREIGN KEY (analyzer_id) REFERENCES analyzer_info(analyzer_id)
);

-- camera_area_setting
DROP TABLE IF EXISTS camera_area_setting CASCADE;
CREATE TABLE camera_area_setting (
  area_setting_id SERIAL PRIMARY KEY,
  polygon TEXT NOT NULL,
  area_name VARCHAR(100) NOT NULL,
  border_color VARCHAR(20) NOT NULL,
  fill_color VARCHAR(20) NOT NULL,
  fill_opacity INTEGER NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  camera_id INTEGER NOT NULL,
  CONSTRAINT fk_camera_area_setting_camera FOREIGN KEY (camera_id) REFERENCES camera_info(camera_id) ON DELETE CASCADE
);

-- camera_setting
DROP TABLE IF EXISTS camera_setting CASCADE;
CREATE TABLE camera_setting (
  setting_id SERIAL PRIMARY KEY,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  camera_id INTEGER NOT NULL,
  code_id INTEGER NOT NULL,
  CONSTRAINT fk_camera_setting_camera FOREIGN KEY (camera_id) REFERENCES camera_info(camera_id) ON DELETE CASCADE,
  CONSTRAINT fk_camera_setting_code FOREIGN KEY (code_id) REFERENCES event_codes(code_id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX idx_camera_setting_unique ON camera_setting (camera_id, code_id);

-- camera_detail_setting
DROP TABLE IF EXISTS camera_detail_setting CASCADE;
CREATE TABLE camera_detail_setting (
  detail_setting_id SERIAL PRIMARY KEY,
  setting_id INTEGER NOT NULL,
  area_setting_id INTEGER NOT NULL,
  CONSTRAINT fk_camera_detail_setting_setting FOREIGN KEY (setting_id) REFERENCES camera_setting(setting_id) ON DELETE CASCADE,
  CONSTRAINT fk_camera_detail_setting_area FOREIGN KEY (area_setting_id) REFERENCES camera_area_setting(area_setting_id) ON DELETE CASCADE
);

-- event_info
DROP TABLE IF EXISTS event_info CASCADE;
CREATE TABLE event_info (
  event_id SERIAL PRIMARY KEY,
  event_uuid VARCHAR(255),
  camera_id INTEGER NOT NULL,
  bounding_box TEXT NOT NULL,
  start_frame_at TIMESTAMP NOT NULL,
  end_frame_at TIMESTAMP,
  code_id INTEGER NOT NULL,
  CONSTRAINT fk_event_info_camera FOREIGN KEY (camera_id) REFERENCES camera_info(camera_id),
  CONSTRAINT fk_event_info_code FOREIGN KEY (code_id) REFERENCES event_codes(code_id) ON DELETE CASCADE
);

-- user_account_history
DROP TABLE IF EXISTS user_account_history CASCADE;
CREATE TABLE user_account_history (
  history_id SERIAL PRIMARY KEY,
  target_user_id VARCHAR(100),
  actor_user_id VARCHAR(100) NOT NULL,
  action_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  action_ip VARCHAR(255) NOT NULL,
  action_type VARCHAR(30) NOT NULL,
  action_result VARCHAR(20) NOT NULL
);

-- video
DROP TABLE IF EXISTS video CASCADE;
CREATE TABLE video (
  id SERIAL PRIMARY KEY,
  summary VARCHAR(255),
  video_url VARCHAR(255),
  start_time TIMESTAMP,
  thumbnail_url VARCHAR(255),
  end_time TIMESTAMP,
  camera_id BIGINT,
  CONSTRAINT fk_video_camera FOREIGN KEY (camera_id) REFERENCES camera_info(camera_id),
  event_id BIGINT,
  CONSTRAINT fk_video_event FOREIGN KEY (event_id) REFERENCES event_info(event_id)
);

-- detected_object
DROP TABLE IF EXISTS detected_object CASCADE;
CREATE TABLE detected_object (
  id SERIAL PRIMARY KEY,
  uuid VARCHAR(255),
  alias VARCHAR(255),
  feature VARCHAR(255),
  code_id BIGINT,
  crop_img bytea,
  CONSTRAINT fk_detected_object_code FOREIGN KEY (code_id) REFERENCES event_codes(code_id)
);

-- detection
DROP TABLE IF EXISTS detection CASCADE;
CREATE TABLE detection (
  id SERIAL PRIMARY KEY,
  detected_object_id BIGINT,
  appeared_time TIMESTAMP,
  exit_time TIMESTAMP,
  video_id BIGINT,
  crop_img bytea,
  CONSTRAINT fk_detection_video FOREIGN KEY (video_id) REFERENCES video(id),
  CONSTRAINT fk_detection_object FOREIGN KEY (detected_object_id) REFERENCES detected_object(id)
);
