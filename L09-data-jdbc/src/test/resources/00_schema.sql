create table orders
(
    id        bigserial primary key,
    user_name varchar(200) not null,
    done      boolean default false not null,
    updated_at  timestamp
);

create table order_items
(
    id         bigserial primary key,
    order_id   bigint not null references orders(id),
    item_name  varchar(200)   not null,
    item_count int            not null,
    item_price numeric(10, 2) not null
);
create index order_items_order_id on order_items(order_id);
