drop table Produit cascade constraint;
drop sequence seq_id;

create table Produit (
	id varchar(30),
	nom varchar(256),
	prixHt number,
	quantite integer,

	constraint pk_id primary key (id),
	constraint un_nom UNIQUE (nom)
);

create sequence seq_id start with 1;

create or replace trigger trig_id
before insert on Produit 
for each row

begin
  select seq_id.nextval
  into   :new.id
  from   dual;
end;