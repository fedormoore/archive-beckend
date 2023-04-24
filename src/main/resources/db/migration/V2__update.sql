alter table ar_fond
    alter column name type varchar(500);

alter table ar_fond
    alter column number_fond type varchar(50);

update ar_status_record set status = 'На доработку от заведующего хранилищем' where id = 4;
update ar_status_record set status = 'На доработку от отдела сканирования' where id = 6;
update ar_status_record set status = 'На доработку от отдела загрузки' where id = 9;
update ar_status_record set status = 'На доработку от проверяющего' where id = 11;