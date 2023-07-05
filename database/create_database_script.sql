-- Table: public.users

--DROP TABLE IF EXISTS public.users;



CREATE TABLE IF NOT EXISTS public.user
(
    user_id UUID NOT NULL DEFAULT gen_random_uuid(),
    username character varying(50) COLLATE pg_catalog."default" NOT NULL,
    password_hash character varying(255) COLLATE pg_catalog."default" NOT NULL,
    email character varying(100) COLLATE pg_catalog."default" NOT NULL,
    reftoken character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT users_pk PRIMARY KEY (user_id),
    CONSTRAINT email_uniq UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS public.car
(
    car_id UUID NOT NULL DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    brand character varying(45) COLLATE pg_catalog."default" NOT NULL,
    model character varying(45) COLLATE pg_catalog."default" NOT NULL,
    car_year integer NOT NULL,
    car_type character varying(15) COLLATE pg_catalog."default" NOT NULL,
    engine_type character varying(15) COLLATE pg_catalog."default" NOT NULL,
    engine_size character varying(8) COLLATE pg_catalog."default" NOT NULL,
    odometer integer NOT NULL,
    number_plate character varying(15) COLLATE pg_catalog."default" NOT NULL,
    last_update timestamp DEFAULT current_timestamp,
    CONSTRAINT car_id_pk PRIMARY KEY (car_id),
    CONSTRAINT cars_users FOREIGN KEY (user_id)
        REFERENCES public.user (user_id) MATCH SIMPLE
        ON UPDATE SET NULL
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.event
(
    event_id UUID NOT NULL DEFAULT gen_random_uuid(),
    car_id UUID NOT NULL,
    event_date date NOT NULL,
    last_update timestamp DEFAULT current_timestamp,
    odometer int NOT NULL,
    oil boolean NOT NULL DEFAULT FALSE,
    oil_filter boolean NOT NULL DEFAULT FALSE,
    air_filter boolean NOT NULL DEFAULT FALSE,
    timing_belt_kit boolean NOT NULL DEFAULT FALSE,
    description text NOT NULL,
    CONSTRAINT event_id_PK PRIMARY KEY (event_id),
    CONSTRAINT car_id_car_FK FOREIGN KEY (car_id) 
        REFERENCES public.car (car_id) MATCH SIMPLE 
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.invoice
(
  id UUID NOT NULL DEFAULT gen_random_uuid(),
  user_id UUID NOT NULL,
  event_id UUID NOT NULL,
  invoice_date date NOT NULL,
  invoice_number varchar(30) NOT NULL,
  currency varchar(3) NOT NULL default 'USD',
  last_update timestamp NOT NULL DEFAULT current_timestamp,
  CONSTRAINT invoice_id_pk PRIMARY KEY (id),
  CONSTRAINT user_id_user_fk FOREIGN KEY (user_id)
      REFERENCES public.user (user_id) MATCH SIMPLE
      ON UPDATE NO ACTION
      ON DELETE CASCADE, 
  CONSTRAINT event_id_event_fk FOREIGN KEY (event_id)
      REFERENCES public.event (event_id) MATCH SIMPLE
      ON UPDATE NO ACTION
      ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.invoicePosition
(
  id UUID NOT NULL DEFAULT gen_random_uuid(),
  invoice_id UUID NOT NULL,
  name varchar(100) NOT NULL,
  price bigint NOT NULL,
  quantity integer NOT NULL,
  CONSTRAINT id_pk PRIMARY KEY (id),
  CONSTRAINT invoice_id_invoice_fk FOREIGN KEY (invoice_id)
      REFERENCES public.invoice (id) MATCH SIMPLE
      ON UPDATE NO ACTION
      ON DELETE CASCADE
);

--stworzenie widoku na podstawie tych tabel z kolumną total podliczającą wartość 