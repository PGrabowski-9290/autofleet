CREATE TABLE IF NOT EXISTS public.user
(
    user_id UUID NOT NULL DEFAULT gen_random_uuid(),
    username character varying(50) COLLATE pg_catalog."default" NOT NULL,
    password_hash character varying(255) COLLATE pg_catalog."default" NOT NULL,
    email character varying(100) COLLATE pg_catalog."default" NOT NULL,
    reftoken character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT users_pk PRIMARY KEY (user_id),
    CONSTRAINT email_uniq UNIQUE (email)
)

    TABLESPACE pg_default;


CREATE TABLE IF NOT EXISTS public.car
(
    car_id uuid NOT NULL DEFAULT gen_random_uuid(),
    user_id uuid NOT NULL,
    brand character varying(45) COLLATE pg_catalog."default" NOT NULL,
    model character varying(45) COLLATE pg_catalog."default" NOT NULL,
    car_year integer NOT NULL,
    car_type character varying(15) COLLATE pg_catalog."default" NOT NULL,
    engine_type character varying(15) COLLATE pg_catalog."default" NOT NULL,
    engine character varying(30) COLLATE pg_catalog."default" NOT NULL,
    odometer integer NOT NULL,
    number_plate character varying(15) COLLATE pg_catalog."default" NOT NULL,
    last_update timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT car_id_pk PRIMARY KEY (car_id),
    CONSTRAINT cars_users FOREIGN KEY (user_id)
        REFERENCES public."user" (user_id) MATCH SIMPLE
        ON UPDATE SET NULL
        ON DELETE CASCADE
)

    TABLESPACE pg_default;


CREATE TABLE IF NOT EXISTS public.event
(
    event_id uuid NOT NULL DEFAULT gen_random_uuid(),
    car_id uuid NOT NULL,
    event_date date NOT NULL,
    last_update timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    odometer integer NOT NULL,
    oil boolean NOT NULL DEFAULT false,
    oil_filter boolean NOT NULL DEFAULT false,
    air_filter boolean NOT NULL DEFAULT false,
    timing_belt_kit boolean NOT NULL DEFAULT false,
    description text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT event_id_pk PRIMARY KEY (event_id),
    CONSTRAINT car_id_car_fk FOREIGN KEY (car_id)
        REFERENCES public.car (car_id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)

    TABLESPACE pg_default;


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
)

    TABLESPACE pg_default;

CREATE TABLE IF NOT EXISTS public.invoiceposition
(
    id uuid NOT NULL DEFAULT gen_random_uuid(),
    invoice_id uuid NOT NULL,
    name character varying(150) COLLATE pg_catalog."default" NOT NULL,
    price numeric(20,2) NOT NULL,
    quantity numeric(20,2) NOT NULL,
    CONSTRAINT id_pk PRIMARY KEY (id),
    CONSTRAINT invoice_id_invoice_fk FOREIGN KEY (invoice_id)
        REFERENCES public.invoice (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
)

    TABLESPACE pg_default;