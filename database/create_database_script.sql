-- Table: public.users

--DROP TABLE IF EXISTS public.users;
--DROP SEQUENCE IF EXISTS public.users_user_id_seq;


CREATE TABLE IF NOT EXISTS public.users
(
    user_id integer NOT NULL,
    username character varying(50) COLLATE pg_catalog."default" NOT NULL,
    password_hash character varying(255) COLLATE pg_catalog."default" NOT NULL,
    email character varying(100) COLLATE pg_catalog."default" NOT NULL,
    ref_token character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT users_pk PRIMARY KEY (user_id),
    CONSTRAINT email_uniq UNIQUE (email)
)

TABLESPACE pg_default;

CREATE SEQUENCE IF NOT EXISTS public.users_user_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1
    OWNED BY users.user_id;

ALTER TABLE IF EXISTS public.users
	alter column user_id set DEFAULT nextval('users_user_id_seq'::regclass),
    OWNER to postgres;
	
CREATE TABLE IF NOT EXISTS public.cars
(
    car_id integer NOT NULL,
    user_id integer NOT NULL,
    mark character varying(45) COLLATE pg_catalog."default" NOT NULL,
    model character varying(45) COLLATE pg_catalog."default" NOT NULL,
    year integer NOT NULL,
    type character varying(15) COLLATE pg_catalog."default" NOT NULL,
    engine_type character varying(15) COLLATE pg_catalog."default" NOT NULL,
    engine_size character varying(8) COLLATE pg_catalog."default" NOT NULL,
    odometer integer NOT NULL,
    number_plate character varying(15) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT cars_pk PRIMARY KEY (car_id),
    CONSTRAINT cars_users FOREIGN KEY (user_id)
        REFERENCES public.users (user_id) MATCH SIMPLE
        ON UPDATE SET NULL
        ON DELETE SET NULL
)

TABLESPACE pg_default;

CREATE SEQUENCE IF NOT EXISTS public.cars_car_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1
    OWNED BY cars.car_id;

ALTER TABLE IF EXISTS public.cars
	ALTER COLUMN car_id set DEFAULT nextval('cars_car_id_seq'::regclass),
    OWNER to postgres;