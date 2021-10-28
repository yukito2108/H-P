CREATE SEQUENCE IF NOT EXISTS user_data_seq;
CREATE TABLE IF NOT EXISTS user_data (
  user_id INT PRIMARY KEY NOT NULL DEFAULT nextval('user_data_seq'),
  username VARCHAR(45) UNIQUE NOT NULL,
  password VARCHAR(100) NOT NULL,
  email VARCHAR(45) NOT NULL,
  address VARCHAR(100) NOT NULL,
  telephone VARCHAR(45) NOT NULL,
  user_status VARCHAR(45) NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS doctor_seq;
CREATE TABLE IF NOT EXISTS doctor (
  doctor_id INT PRIMARY KEY NOT NULL DEFAULT nextval('doctor_seq'),
  first_name VARCHAR(45) NOT NULL,
  last_name VARCHAR(45) NOT NULL,
  qualifications VARCHAR(250) NOT NULL,
  birth_date DATE NOT NULL,
  gender CHAR(1) NOT NULL,
  licence_code VARCHAR(45) UNIQUE NOT NULL,
  other VARCHAR(250) NULL,
  user_id INT NOT NULL,
  CONSTRAINT fk_user
    FOREIGN KEY (user_id)
    REFERENCES user_data(user_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE SEQUENCE IF NOT EXISTS patient_seq;
CREATE TABLE IF NOT EXISTS patient (
  patient_id INT PRIMARY KEY NOT NULL DEFAULT nextval('patient_seq'),
  hs_code VARCHAR(45) UNIQUE NOT NULL,
  gender CHAR(1) NOT NULL,
  birth_date DATE NOT NULL,
  first_name VARCHAR(45) NOT NULL,
  last_name VARCHAR(45) NOT NULL,
  other VARCHAR(250) NULL,
  user_id INT NOT NULL, 
  CONSTRAINT fk_user
    FOREIGN KEY (user_id)
    REFERENCES user_data (user_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS record_type (
  component_code VARCHAR(45) PRIMARY KEY NOT NULL,
  component_description VARCHAR(100) UNIQUE NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS patient_record_seq;
CREATE TABLE IF NOT EXISTS patient_record (
  patient_record_id INT PRIMARY KEY NOT NULL DEFAULT nextval('patient_record_seq'),
  date DATE NOT NULL,
  record_details VARCHAR(1024) NOT NULL,
  patient_id INT NOT NULL,
  doctor_id INT NOT NULL,
  component_code VARCHAR(45) NOT NULL,  
  CONSTRAINT fk_patient
    FOREIGN KEY (patient_id)
    REFERENCES patient (patient_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_doctor
    FOREIGN KEY (doctor_id)
    REFERENCES doctor (doctor_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_record
    FOREIGN KEY (component_code)
    REFERENCES record_type (component_code)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS appointment_status_code (
  app_status_code VARCHAR(45) PRIMARY KEY NOT NULL,
  app_status_description VARCHAR(45) UNIQUE NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS appointment_seq;
CREATE TABLE IF NOT EXISTS appointment (
  app_id INT PRIMARY KEY NOT NULL DEFAULT nextval('appointment_seq'),
  app_datetime TIMESTAMP(5) NOT NULL,
  app_end_datetime TIMESTAMP(5) NOT NULL,
  appointment_details VARCHAR(250) NOT NULL,
  app_status_code VARCHAR(45) NOT NULL,
  patient_id INT NOT NULL,
  doctor_id INT NOT NULL,
  CONSTRAINT fk_app_status_code
    FOREIGN KEY (app_status_code)
    REFERENCES appointment_status_code (app_status_code)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_patient
    FOREIGN KEY (patient_id)
    REFERENCES patient (patient_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_doctor
    FOREIGN KEY (doctor_id)
    REFERENCES doctor (doctor_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE SEQUENCE IF NOT EXISTS role_seq;
CREATE TABLE IF NOT EXISTS role (
  role_id INT PRIMARY KEY NOT NULL DEFAULT nextval('role_seq'),
  role_type VARCHAR(45) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS user_role (
  user_id INT NOT NULL,
  role_id INT NOT NULL,
  CONSTRAINT fk_user
    FOREIGN KEY (user_id)
    REFERENCES user_data (user_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_role
    FOREIGN KEY (role_id)
    REFERENCES role (role_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

INSERT INTO "public"."role" (role_id,role_type) VALUES (1,'ADMIN'), (2,'DOCTOR'), (3,'USER');

INSERT INTO "public"."record_type" (component_code,component_description) VALUES('PHEX','physical examination'),('MDPR','medical prescription'),('RTSP','referral to specialists');

INSERT INTO "public"."appointment_status_code" (app_status_code,app_status_description) VALUES('CNLD','cancelled term by doctor'),('RSVD','reserved term by patient'),('FREE','free term opened by doctor'),('OTHR','other actions');

INSERT INTO "public"."user_data" (user_id,username,password,email,address,telephone,user_status) VALUES (1,'sneza','$2a$10$FOulUm753QCqNmKfWX006uopOhTeE9hdmOtrZrKpJD6tu7oy6pYg6','sneza@snnp.com','Nušićeva 93,Beograd','7865435','ACTIVE');

INSERT INTO "public"."user_data" (user_id,username,password,email,address,telephone,user_status) VALUES (9,'unknown','$2a$10$FOulUm753QCqNmKfWX006uopOhTeE9hdmOtrZrKpJD6tu7oy6pYg6','unknown@unk.un','No address','100000','INACTIVE');

INSERT INTO "public"."patient" (patient_id,hs_code,gender,birth_date,first_name,last_name,other,user_id) VALUES (3,'00000000000','M','2018-01-01','Unknown','Unknown','This is a patient generated for Appointment Records creation only.',9);

INSERT INTO "public"."user_role" (user_id,role_id) VALUES (1,1);

INSERT INTO "public"."user_role" (user_id,role_id) VALUES (9,3);