#Tokyo Subway databae
drop database if exists TS;
create database TS character set utf8;
use TS;

#Station
drop table if exists TS_Station;
create table TS_Station(
   TS_StationId varchar(50) primary key,  
   TS_StationName varchar(100),                 
   TS_StationInsertionTimestamp DateTime,        
   State_type varchar(10),                      
   State_message varchar(100),                  
   State_totalPassengers int,                    
   State_upDateTime DateTime                     
); 
insert into TS_Station(TS_StationId,TS_StationName,TS_StationInsertionTimestamp,State_type,State_message,State_TotalPassengers,State_upDateTime) values('06eb5cba-2115-4452-9eab-d531fa61a09e','Oji',now(),'normal','',0,now());

#Line
drop table if exists TS_Line;
create table TS_Line(
   TS_LineId varchar(50) primary key,         
   TS_LineNum int,                            
   TS_LineName varchar(100),                  
   TS_LineInsertionTimestamp DateTime,        
   State_LineUpState int,                     
   State_upDateTime DateTime                  
); 
insert into TS_Line values('dd52c72f-6068-4df5-9860-8576cdf878df',1,'One num line',now(),0,now());



#Oling_Odata_JPA_ExposeTable
drop table if exists Oling_Odata_JPA_ExposeTable;
create table Oling_Odata_JPA_ExposeTable(
    ExposeTableName varchar(50) primary key     
);
insert into Oling_Odata_JPA_ExposeTable values('TS_Station');