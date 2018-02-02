drop table Catalogue cascade constraint;
drop sequence seq_catalogue_id;

drop table Produit cascade constraint;
drop sequence seq_produit_id;


create table Catalogue (
	id varchar(30),
	nom varchar(256),
	constraint pk_catalogue_id primary key (id)
);

create sequence seq_catalogue_id start with 1;

create or replace trigger trig_catalogue_id
before insert on Catalogue 
for each row

begin
  select seq_catalogue_id.nextval
  into   :new.id
  from   dual;
end; 
/

create table Produit (
	id varchar(30),
	catalogueId varchar(30),
	nom varchar(256),
	prixHt number,
	quantite integer,

	constraint pk_produit_id primary key (id),
	constraint un_nom UNIQUE (catalogueId, nom),
	constraint fk_catalogueId foreign key (catalogueId) references Catalogue(id)
);

create sequence seq_produit_id start with 1;

create or replace trigger trig_produit_id
before insert on Produit 
for each row

begin
  select seq_produit_id.nextval
  into   :new.id
  from   dual;
end;
/