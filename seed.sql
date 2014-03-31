drop table users;
drop table flights;
drop table tickets;

create table users (ID int primary key, ROLE text, USERNAME text unique, PASSWORD text);
create table flights (ID int primary key, DEPARTURE text, DESTINATION text, DATE date, TICKETCOST float);
create table tickets (ID int primary key, STATUS text, ADDRESS text, EMAIL text, NAME text, OWNERFROM text, PHONE text, FLIGHTID int, foreign key(FLIGHTID) references flights(ID));

insert into users (ID, USERNAME, PASSWORD, ROLE) values (1, 'root', 'root', 'SUPER');
insert into users (ID, USERNAME, PASSWORD, ROLE) values (2, 'administrator', 'administrator', 'BOOKING_ADMINISTRATOR');
insert into users (ID, USERNAME, PASSWORD, ROLE) values (3, 'accountant', 'accountant', 'ACCOUNTANT');
insert into users (ID, USERNAME, PASSWORD, ROLE) values (4, 'analytic', 'analytic', 'ANALYTIC');

insert into flights ('ID', 'DEPARTURE', 'DESTINATION', 'DATE', 'TICKETCOST') values (1, 'Київ', 'Мінськ', '21-04-2014', 150.15);