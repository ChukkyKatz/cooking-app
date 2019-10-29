create schema if not exists cooking;

create table if not exists cooking.dishes_types (
  id serial primary key,
  type varchar,
  advice_schedule varchar,
  last_advice_date timestamp
);

create table if not exists cooking.dishes (
  id serial primary key,
  name varchar,
  type int references cooking.dishes_types
);

create table if not exists cooking.ingredients (
  id serial primary key,
  name varchar
);

create table if not exists cooking.dish_ing (
  id serial primary key,
  dish_id int references cooking.dishes(id),
  ing_id int references cooking.ingredients(id)
);

create table if not exists cooking.receipts (
  id serial primary key,
  dish_id int references cooking.dishes(id),
  url varchar,
  image varchar
);

create table if not exists cooking.recipients (
  id serial primary key,
  name varchar,
  email varchar
);

grant usage on schema cooking to service_user;

grant usage on all sequences in schema cooking to service_user;

grant select, insert, update on all tables in schema cooking to service_user;
