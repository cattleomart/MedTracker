create type if not exists USER_ROLE as enum('ADMIN', 'USER');

create table  USERMODEL (
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    username varchar(255),
    password varchar(255),
    role USER_ROLE
);

create table evaluationEntry (
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    record_Date date,
    blood_pressure_systole int,
    blood_pressure_diastole int,
    heart_rate int,
    userModel_id int,
    foreign key (userModel_id) references userModel(id)
);

