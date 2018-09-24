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
    x_coord double precision,
    y_coord double precision,
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

CREATE TABLE station
(
    id integer NOT NULL,
    type character varying(50),
    class integer,
    is_logistic_centre boolean,
    CONSTRAINT station_pkey PRIMARY KEY (id),
    CONSTRAINT station_id_fkey FOREIGN KEY (id)
        REFERENCES public.geo_object (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

create table project(
	id serial primary key,
	name varchar(100) not null,
	descr varchar(1000),
	create_date varchar(10),
	user_name varchar(100)
);

create table project_factory(
	project_id integer not null references project(project_id),
	factory_id integer not null references factory(id),
	constraint pf_primary_key primary key (project_id, factory_id)
);

create table project_station(
	project_id integer not null references project(project_id),
	station_id integer not null references station(id),
	constraint ps_primary_key primary key (project_id, station_id)
);

Insert into region(name) values('Самарская область'),('Республика татарстан'),('Ульяновская область');