CREATE TABLE Account (
  Username varchar(15) NOT NULL,
  Password varchar(20) NOT NULL,
  Email_Id varchar(35) NOT NULL,
  Address varchar(50) DEFAULT NULL,
  Phone_No char(10) NOT NULL,
  adhar number(20),
  PRIMARY KEY (Username)
);

CREATE TABLE Train (
  Train_No number(6) NOT NULL,
  Name varchar(25) NOT NULL,
  Seat_Sleeper number(4) NOT NULL,
  Seat_First_Class_AC number(4) NOT NULL,
  Seat_Second_Class_AC number(4) NOT NULL,
  Seat_Third_Class_AC number(4) NOT NULL,
  PRIMARY KEY (Train_No)
);

CREATE TABLE Station (
  Station_Code char(5) NOT NULL,
  Station_Name varchar(25) NOT NULL,
  PRIMARY KEY (Station_Code)
);

CREATE TABLE Ticket (
  Ticket_No number(10) NOT NULL,
  Train_No number(6) NOT NULL,
  Date_of_Journey varchar(15) NOT NULL,
  Username varchar(15) NOT NULL,
  Seat_type varchar2(25) NOT NULL,
  Seat_number number DEFAULT NULL,
  PRIMARY KEY (Ticket_No),
  CONSTRAINT Ticket_ibfk_1 FOREIGN KEY (Username) REFERENCES Account (Username) ON DELETE CASCADE,
  CONSTRAINT Ticket_ibfk_2 FOREIGN KEY (Train_No) REFERENCES Train (Train_No) ON DELETE CASCADE
);

CREATE TABLE Passenger (
  Passenger_Id number(11) ,
  First_Name varchar(20) NOT NULL,
  Last_Name varchar(20) NOT NULL,
  Gender char(1) NOT NULL,
  Phone_No char(10) DEFAULT NULL,
  Ticket_No number(10) NOT NULL,
  Age number(11) NOT NULL,
  Class varchar(20) NOT NULL,
  PRIMARY KEY (Passenger_Id),
  CONSTRAINT Passenger_ibfk_1 FOREIGN KEY (Ticket_No) REFERENCES Ticket (Ticket_No) ON DELETE CASCADE
);


CREATE TABLE Stoppage (
  Train_No number(6) NOT NULL,
  Station_Code char(5) NOT NULL,
  Arrival_Time  varchar2(10),
  Departure_Time  varchar2(10),
  Sequence number,
  PRIMARY KEY (Train_No,Station_Code),
  CONSTRAINT Stoppage_ibfk_1 FOREIGN KEY (Train_No) REFERENCES Train (Train_No) ON DELETE CASCADE,
  CONSTRAINT Stoppage_ibfk_2 FOREIGN KEY (Station_Code) REFERENCES Station (Station_Code) ON DELETE CASCADE
);

CREATE or replace procedure register_account (Username in Account.Username%type, Password in Account.Password%type, Email_Id in Account.Email_Id%type
, Address in Account.Address%type, Phone_No in Account.Phone_No%type, adhar Account.adhar%type,result out varchar2)
AS
BEGIN
insert into Account values(Username,Password,Email_Id,Address,Phone_No,adhar);
exception
  when DUP_VAL_ON_INDEX then
  result := 'username already exists';
end;
/



create or replace procedure login (user in Account.Username%type, passw in Account.Password%type, result out varchar2)
is
pass Account.Password%type := '';
numb number(4);
BEGIN

  pass := '';
  select Password into pass from Account where Username=user;
  
  if pass = passw then
    result := 'yes';
  else
    result := 'username or password incorrect';
  end if;
exception
when NO_DATA_FOUND then
result := 'account does not exist';
end;
/

create or REPLACE procedure get_account (user_id in Account.Username%type, result out varchar2)
AS
row Account%rowtype;
BEGIN
  select * into row from Account where Username=user_id;
  result := row.Username||'@'||row.Password||'@'||row.Email_Id||'@'||row.Address||'@'||row.Phone_No||'@'||row.adhar;
exception
  when NO_DATA_FOUND then
  result := 'account does not exist';
  when others then
  result := 'error';
end;
/

CREATE OR REPLACE PROCEDURE delete_account (user_id Account.Username%type, result out varchar2)
AS 
BEGIN 
    delete from Account where Account.Username = user_id;
       result := 'successful';
    exception when others then
    result := 'failed';
END; 
/

Create or Replace procedure update_account(
        Username Account.Username%type,
        column_to_update varchar2,
        new_column_value varchar2,
        result out varchar2
        ) is
        sql_statement varchar2(200);
    begin
        if(column_to_update = 'Username') then
            result:='primary key cannot be updated';
        else    
            sql_statement := 'UPDATE Account SET ' || column_to_update || ' = ''' || new_column_value || ''' where '||'Username'||' '||'='||''''||Username||'''';
            EXECUTE IMMEDIATE sql_statement;
            result:='operation was a sucess';
        end if;
    exception
        WHEN others then
            result:='error while updating values';         
    end;
    /



--------------------------------------------------------------------------------------------------------------------------------------



CREATE or REPLACE procedure add_passenger(First_Name in Passenger.First_Name%type,Last_Name in Passenger.Last_Name%type,
                                          Gender in Passenger.Gender%type,Phone_No in Passenger.Phone_No%type,
                                          Age in Passenger.Age%type,
                                          result out varchar2)
AS
ticket_num Passenger.Ticket_No%type;
cl Passenger.Class%type;
BEGIN
  select ticket_no.currval into ticket_num from dual;
  select Seat_type into cl from Ticket where Ticket_No = ticket_num;
  insert into Passenger values(passenger_id.nextval,First_Name,Last_Name,Gender,Phone_No,ticket_num,Age,cl);
  result := 'successful';
  exception
  when others then
  result:= 'failed';
end;
/

--------------------------------------------------------------------------------------------------------------------------------------



create or REPLACE procedure register_station (Station_Code in Station.Station_Code%type, Station_Name in Station.Station_Name%type, result out varchar2)
AS
BEGIN
  insert into Station values(Station_Code,Station_Name);
exception
  when DUP_VAL_ON_INDEX then
  result := 'station already exists';
end;
/ 

CREATE OR REPLACE PROCEDURE delete_station (station_no Station.Station_Code%type, result out varchar2)
AS 
BEGIN 
    delete from Station where Station.Station_Code = station_no;
       result := 'successful';
    exception when others then
    result := 'failed';
END; 
/


Create or Replace procedure update_station(
        Station_Code Station.Station_Code%type,
        column_to_update varchar2,
        new_column_value varchar2,
        result out varchar2
        ) is
        sql_statement varchar2(200);
    begin
        if(column_to_update = 'Station_Code') then
            result:='primary key cannot be updated';
        else    
            sql_statement := 'UPDATE Station SET ' || column_to_update || ' = ''' || new_column_value || ''' where '||'Station_Code'||' '||'='||''''||Station_Code||'''';
            EXECUTE IMMEDIATE sql_statement;
            result:='operation was a sucess';
        end if;
    exception
        WHEN others then
            result:='error while updating values';         
    end;
    /


--------------------------------------------------------------------------------------------------------------------------------------



CREATE OR REPLACE TYPE NAMELIST IS TABLE of number(6);
/


CREATE or REPLACE procedure possible_trains (
                  Date_of_Journey in varchar2,
                  Source in Stoppage.Station_Code%type,
                  Destination in Stoppage.Station_Code%type,
                  
                  result1 out varchar,
                  sleeper out varchar,
                  fac out varchar,
                  sac out varchar,
                  tac out varchar
                  ) AS
name_table NAMELIST;                  
t_name  Train.Name%type;
sl number;
fc number;
sc number;
tc number;
BEGIN
  select e.Train_No BULK COLLECT into name_table from Stoppage e inner join Stoppage m
                 on e.Train_No = m.Train_No 
                 where e.Station_Code = Source and m.Station_Code = Destination and e.SEQUENCE < m.SEQUENCE;

  select Name into t_name from Train where Train_No = name_table(1);
  result1 := t_name||'('||name_table(1)||')';
  select 20-count(*) into sl from Ticket where Train_No = name_table(1) and Date_of_Journey = Date_of_Journey and Seat_type = 'sleeper';
  sleeper := ''||sl;
  select 20-count(*) into fc from Ticket where Train_No = name_table(1) and Date_of_Journey = Date_of_Journey and Seat_type = 'fac';
  fac := ''||fc;
  select 20-count(*) into sc from Ticket where Train_No = name_table(1) and Date_of_Journey = Date_of_Journey and Seat_type = 'sac';
  sac := ''||sc;
  select 20-count(*) into tc from Ticket where Train_No = name_table(1) and Date_of_Journey = Date_of_Journey and Seat_type = 'tac';
  tac := ''||tc;

  for i in 2..name_table.count
  LOOP
    select Name into t_name from Train where Train_No = name_table(i);
    result1 := result1||'@'||t_name||'('||name_table(i)||')';
    select 20-count(*) into sl from Ticket where Train_No = name_table(i) and Date_of_Journey = Date_of_Journey and Seat_type = 'sleeper';
  sleeper := sleeper||'@'||sl;
  select 20-count(*) into fc from Ticket where Train_No = name_table(i) and Date_of_Journey = Date_of_Journey and Seat_type = 'fac';
  fac := fac||'@'||fc;
  select 20-count(*) into sc from Ticket where Train_No = name_table(i) and Date_of_Journey = Date_of_Journey and Seat_type = 'sac';
  sac := sac||'@'||sc;
  select 20-count(*) into tc from Ticket where Train_No = name_table(i) and Date_of_Journey = Date_of_Journey and Seat_type = 'tac';
  tac := tac||'@'||tc;
  end loop;


  exception
  when others then
  result1 := 'no trains';
END;
/

CREATE or REPLACE procedure check_availabilty(train_num Train.Train_No%type,dateof Ticket.Date_of_Journey%type,result out varchar)
AS
sl number;
fc number;
sc number;
tc number;
row number;
BEGIN

select count(*) into row from Train where Train_No = train_num;
if row = 1 then
  select 20-count(*) into sl from Ticket where Train_No = train_num and Date_of_Journey = dateof and Seat_type = 'SS';
  select 20-count(*) into fc from Ticket where Train_No = train_num and Date_of_Journey = dateof and Seat_type = 'A1';
  select 20-count(*) into sc from Ticket where Train_No = train_num and Date_of_Journey = dateof and Seat_type = 'A2';
  select 20-count(*) into tc from Ticket where Train_No = train_num and Date_of_Journey = dateof and Seat_type = 'A3';

  result := sl||'@'||fc||'@'||sc||'@'||tc;
else
result:= 'invalid train';
end if;



END;
/



CREATE OR REPLACE TYPE CODELIST IS TABLE of char(5);
/

CREATE or Replace procedure possible_stations(train_num Train.Train_No%type,result out varchar)
AS
code_table CODELIST;
S_name Station.Station_Name%type;
BEGIN
  select Station_Code BULK COLLECT into code_table from Stoppage where Train_No = train_num order by Sequence ASC;

  select Station_Name into S_name from Station where Station_Code = code_table(1);
  result := S_name||'('||code_table(1)||')';

  for i in 2..code_table.count
  LOOP
    select Station_Name into S_name from Station where Station_Code = code_table(i);
    result := result||'@'||S_name||'('||code_table(i)||')';
  end loop;
  exception
  when NO_DATA_FOUND then
  result := 'no station';
END;
/



--------------------------------------------------------------------------------------------------------------------------------------



CREATE OR Replace procedure book_ticket (Train_Num in Ticket.Train_No%type, Date in Ticket.Date_of_Journey%type,
                                        User in Ticket.Username%type, Seat_t in Ticket.Seat_type%type, result out varchar)
AS
Seat_num Ticket.Seat_number%type;
BEGIN
begin
  select max(Seat_number) into Seat_num from Ticket where Train_No=Train_Num and Seat_type = Seat_t;
  exception
  when NO_DATA_FOUND then
  Seat_num := 0;
end;
  if Seat_num = 20 then
  result := 'this class is full';
  else
  Seat_num := Seat_num + 1;
  insert into Ticket values(ticket_no.nextval,Train_No,Date_of_Journey,Username,Seat_type,Seat_num);
  end if;
end;
/


  


--------------------------------------------------------------------------------------------------------------------------------------



CREATE OR REPLACE PROCEDURE register_train (Train_No Train.Train_No%type,Name Train.Name%type,Seat_Sleeper Train.Seat_Sleeper%type,
Seat_First_Class_AC Train.Seat_First_Class_AC%type,Seat_Second_Class_AC Train.Seat_Second_Class_AC%type,Seat_Third_Class_AC Train.Seat_Third_Class_AC%type,
result out varchar2)
AS
BEGIN
  insert into Train values(Train_No,Name,Seat_Sleeper,Seat_First_Class_AC,Seat_Second_Class_AC,Seat_Third_Class_AC);
  exception
  when DUP_VAL_ON_INDEX then
  result := 'username already exists';
end;
/

create or replace procedure get_train (train_num in Train.Train_No%type, result out varchar2)
AS
row  Train%rowtype;
BEGIN
  select * into row from TRAIN where Train_No=train_num;
  result := row.Train_No||'@'||row.Name||'@'||row.Seat_Sleeper||'@'||row.Seat_First_Class_AC||'@'||row.Seat_Second_Class_AC||'@'||row.Seat_Third_Class_AC;
exception
  when NO_DATA_FOUND then
  result := 'train does not exist';
  when others then
  result := 'error';
end;
/

CREATE OR REPLACE PROCEDURE delete_train (train_num Train.Train_No%type, result out varchar2)
AS 
BEGIN 
    delete from Train where Train.Train_No = train_num;
       result := 'successful';
    exception when others then
    result := 'failed';
END; 
/


Create or Replace procedure update_train(
        train_num Train.Train_No%type,
        column_to_update varchar2,
        new_column_value varchar2,
        result out varchar2
        ) is
        sql_statement varchar2(200);
    begin
        if(column_to_update = 'Train_No') then
            result:='primary key cannot be updated';
        else    
            sql_statement := 'UPDATE Train SET ' || column_to_update || ' = ''' || new_column_value || ''' where '||'Train_No'||' '||'='||''''||train_num||'''';
            EXECUTE IMMEDIATE sql_statement;
            result:='operation was a sucess';
        end if;
    exception
        WHEN others then
            result:='error while updating values';         
    end;
    /

--------------------------------------------------------------------------------------------------------------------------

CREATE SEQUENCE passenger_id
  MINVALUE 1
  START WITH 1
  INCREMENT BY 1
  CACHE 20;

CREATE SEQUENCE ticket_no
  MINVALUE 1
  START WITH 1
  INCREMENT BY 1
  CACHE 20;