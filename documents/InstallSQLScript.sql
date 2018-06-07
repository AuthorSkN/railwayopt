CREATE SEQUENCE public.geo_object_sequence
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;



CREATE TABLE region
(
    name character varying(100),
    id serial NOT NULL,
    CONSTRAINT region_pkey PRIMARY KEY (id)
);

CREATE TABLE geo_object
(
    id integer NOT NULL,
    name character varying(100),
    latitude double precision,
    longitude double precision,
    is_station boolean,
    region_id integer,
    descr character varying(500),
    CONSTRAINT geo_object_pkey PRIMARY KEY (id),
    CONSTRAINT geo_object_region_id_fkey FOREIGN KEY (region_id)
        REFERENCES public.region (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE factory
(
    id integer NOT NULL,
    cargoes_types character varying(1000),
    full_weight double precision,
    weight double precision,
    CONSTRAINT factory_pkey PRIMARY KEY (id),
    CONSTRAINT factory_id_fkey FOREIGN KEY (id)
        REFERENCES public.geo_object (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE railway_part
(
    railway_id serial NOT NULL,
    name character varying(100),
    CONSTRAINT railway_part_pkey PRIMARY KEY (railway_id)
);

CREATE TABLE railway_branch
(
    branch_id serial NOT NULL,
    railway_id integer,
    CONSTRAINT railway_branch_pkey PRIMARY KEY (branch_id),
    CONSTRAINT railway_branch_railway_id_fkey FOREIGN KEY (railway_id)
        REFERENCES public.railway_part (railway_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE station
(
    id integer NOT NULL,
    type character varying(50),
    class integer,
    branch_id integer,
    is_logistic_centre boolean,
    CONSTRAINT station_pkey PRIMARY KEY (id),
    CONSTRAINT station_branch_id_fkey FOREIGN KEY (branch_id)
        REFERENCES public.railway_branch (branch_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT station_id_fkey FOREIGN KEY (id)
        REFERENCES public.geo_object (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

Insert into region(name) values('Самарская область'),('Республика татарстан'),('Ульяновская область');