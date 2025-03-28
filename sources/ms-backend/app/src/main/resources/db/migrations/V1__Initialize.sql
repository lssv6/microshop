create table seller(
    id bigint primary key,
    name varchar(255) unique,
    version bigint
);
create sequence seller_seq as bigint start with 0;

create table category(
    id bigint primary key,
    name varchar(255),
    full_name varchar(255) unique,
    path varchar(255),
    full_path varchar(255) unique,
    parent_id bigint references category(id)
    version bigint
);

create sequence category_seq as bigint start with 0;

create table manufacturer(
    id bigint primary key,
    name varchar(255) unique,
    img varchar(2048)
);

create sequence manufacturer_seq as bigint start with 0;

create table product(
    id bigint primary key,
    name varchar(255),
    description varchar(50000),
    tag_description varchar(50000),
    friendly_name varchar(255),
    price bigint,
    old_price bigint,

    seller_id bigint references seller(id),
    category_id bigint references category(id),
    manufacturer_id bigint references manufacturer(id)
);

create sequence product_seq as bigint start with 0;
