-- 1st ROLES TABLE

INSERT INTO MXLEAGUE.roles (id_role, grant) VALUES ('user', 'R');
INSERT INTO MXLEAGUE.roles (id_role, grant) VALUES ('staff', 'R/W');
INSERT INTO MXLEAGUE.roles (id_role, grant) VALUES ('admin', 'R/W/X');


--2nd USERS TABLE

INSERT INTO MXLEAGUE.users (id_user, password, name, id_role) VALUES ('usr00', 'skod-tmvu', 'AMATH NDIAYE DEIDHIOU', 'user');  	 		  
INSERT INTO MXLEAGUE.users (id_user, password, name, id_role) VALUES ('usr01', 'avaq-mpyn','ANDRES GARCIA MOHEDANO', 'user');  	 		  
INSERT INTO MXLEAGUE.users (id_user, password, name, id_role) VALUES ('usr02', 'casn-mnrc','DANIEL GODOY HENRIQUEZ', 'user');	  
INSERT INTO MXLEAGUE.users (id_user, password, name, id_role) VALUES ('usr03', 'xepb-nhgb','NESTOR JESUS GORDILLO BENITEZ', 'user'); 		  
INSERT INTO MXLEAGUE.users (id_user, password, name, id_role) VALUES ('usr04', 'bkdv-dbzz','PIERRE KUNDE MALONG', 'user');
INSERT INTO MXLEAGUE.users (id_user, password, name, id_role) VALUES ('usr05', 'lzor-vmoc','IVAN MARQUEZ ALVAREZ', 'user');  
INSERT INTO MXLEAGUE.users (id_user, password, name, id_role) VALUES ('usr06', 'wuzz-kffk','IGNACIO MONSALVE VICENTE', 'user');	  
INSERT INTO MXLEAGUE.users (id_user, password, name, id_role) VALUES ('usr07', 'chmz-rvmo','CARLOS MORALES MORA', 'user');
INSERT INTO MXLEAGUE.users (id_user, password, name, id_role) VALUES ('usr08', 'nzga-qxvn','JOSE ANTONIO MORENTE OLIVA', 'user');	  
INSERT INTO MXLEAGUE.users (id_user, password, name, id_role) VALUES ('usr09', 'abph-tefq','RAFAEL MUÑOZ BENAVIDES', 'user');  
INSERT INTO MXLEAGUE.users (id_user, password, name, id_role) VALUES ('usr10', 'tism-ltzb','IVAN PEREZ VICENTE', 'user');
INSERT INTO MXLEAGUE.users (id_user, password, name, id_role) VALUES ('usr11', 'qqmj-wakd','SAMUEL VILLA RODRIGUEZ', 'user');  
INSERT INTO MXLEAGUE.users (id_user, password, name, id_role) VALUES ('usr12', 'rmsq-ftin','BENJAMIN AKOTO ASAMOAH', 'user');  
INSERT INTO MXLEAGUE.users (id_user, password, name, id_role) VALUES ('usr13', 'fyuq-kyev','BERNABE BARRAGAN MAESTRE', 'user');	  
INSERT INTO MXLEAGUE.users (id_user, password, name, id_role) VALUES ('usr14', 'rsmj-eqmr','IVAN GABRIEL CAÑETE MARTINEZ', 'user'); 		  
INSERT INTO MXLEAGUE.users (id_user, password, name, id_role) VALUES ('usr15', 'kkwr-yxaw','DANIEL ESPEJO GUILLEN', 'user');  
INSERT INTO MXLEAGUE.users (id_user, password, name, id_role) VALUES ('usr16', 'wjkr-rwmw','BORJA GONZALEZ TEJADA', 'user');  
INSERT INTO MXLEAGUE.users (id_user, password, name, id_role) VALUES ('usr17', 'cxjk-sqqk','JORGE ANDRES KATIME VANEGAS', 'user');	  
INSERT INTO MXLEAGUE.users (id_user, password, name, id_role) VALUES ('usr18', 'jsta-gsug','JUAN MANUEL LABRADOR AGUILAR', 'user'); 		  
INSERT INTO MXLEAGUE.users (id_user, password, name, id_role) VALUES ('usr19', 'onkj-gxds','ROBERTO NUÑEZ MAÑAS', 'user');
INSERT INTO MXLEAGUE.users (id_user, password, name, id_role) VALUES ('usr20', 'ojnb-iono','ARONA SANE', 'user');

INSERT INTO MXLEAGUE.users (id_user, password, name, id_role) VALUES ('tec01', 'onjk-gxsd', 'VICTOR AFONSO', 'staff');
INSERT INTO MXLEAGUE.users (id_user, password, name, id_role) VALUES ('tec02', 'ojbn-ioon', 'JOSE LUIS PADRON', 'staff');

INSERT INTO MXLEAGUE.users (id_user, password, name, id_role) VALUES ('tec03', 'aasw-gxsd', 'JOSE MARIA NOMBELA', 'staff');
INSERT INTO MXLEAGUE.users (id_user, password, name, id_role) VALUES ('tec04', 'vftr-ioon', 'MIGUEL GONZALEZ BASTON', 'staff');
INSERT INTO MXLEAGUE.users (id_user, password, name, id_role) VALUES ('tec05', 'qawe-gxsd', 'FELIPE IGLESIAS', 'staff');
INSERT INTO MXLEAGUE.users (id_user, password, name, id_role) VALUES ('tec06', 'jhyt-ioon', 'GUILLERMO CHARNECO', 'staff');
INSERT INTO MXLEAGUE.users (id_user, password, name, id_role) VALUES ('tec07', 'uiop-gxsd', 'GUSTAVO LUCAS PASSOS DE SOUZA', 'staff');

INSERT INTO MXLEAGUE.users (id_user, password, name, id_role) VALUES ('tec08', 'uiop-gxsd', 'MIGUEL ANGEL GOMEZ GONZALEZ', 'admin');



--3rd PLAYERS TABLE

INSERT INTO MXLEAGUE.players (id_player, id_user, position, status) VALUES ('plyr01', 'usr00', 'centrocampista', 'convocado');  	 		  
INSERT INTO MXLEAGUE.players (id_player, id_user, position, status) VALUES ('plyr02', 'usr01', 'centrocampista', 'lesionado');  	 		  
INSERT INTO MXLEAGUE.players (id_player, id_user, position, status) VALUES ('plyr03', 'usr02', 'centrocampista', 'convocado');	  
INSERT INTO MXLEAGUE.players (id_player, id_user, position, status) VALUES ('plyr04', 'usr03', 'centrocampista', 'suplente'); 		  
INSERT INTO MXLEAGUE.players (id_player, id_user, position, status) VALUES ('plyr05', 'usr04', 'centrocampista', 'convocado');
INSERT INTO MXLEAGUE.players (id_player, id_user, position, status) VALUES ('plyr06', 'usr05', 'centrocampista', 'lesionado');  
INSERT INTO MXLEAGUE.players (id_player, id_user, position, status) VALUES ('plyr07', 'usr06', 'centrocampista', 'convocado');	  
INSERT INTO MXLEAGUE.players (id_player, id_user, position, status) VALUES ('plyr08', 'usr07', 'portero', 'suplente');
INSERT INTO MXLEAGUE.players (id_player, id_user, position, status) VALUES ('plyr09', 'usr08', 'centrocampista', 'convocado');	  
INSERT INTO MXLEAGUE.players (id_player, id_user, position, status) VALUES ('plyr10', 'usr09', 'delantero', 'lesionado');  
INSERT INTO MXLEAGUE.players (id_player, id_user, position, status) VALUES ('plyr11', 'usr10', 'delantero', 'convocado');
INSERT INTO MXLEAGUE.players (id_player, id_user, position, status) VALUES ('plyr12', 'usr11', 'delantero', 'suplente');  
INSERT INTO MXLEAGUE.players (id_player, id_user, position, status) VALUES ('plyr13', 'usr12', 'defensa', 'lesionado');  
INSERT INTO MXLEAGUE.players (id_player, id_user, position, status) VALUES ('plyr14', 'usr13', 'portero', 'convocado');	  
INSERT INTO MXLEAGUE.players (id_player, id_user, position, status) VALUES ('plyr15', 'usr14', 'defensa', 'suplente'); 		  
INSERT INTO MXLEAGUE.players (id_player, id_user, position, status) VALUES ('plyr16', 'usr15', 'defensa', 'convocado');  
INSERT INTO MXLEAGUE.players (id_player, id_user, position, status) VALUES ('plyr17', 'usr16', 'defensa', 'lesionado');  
INSERT INTO MXLEAGUE.players (id_player, id_user, position, status) VALUES ('plyr18', 'usr17', 'defensa', 'convocado');	  
INSERT INTO MXLEAGUE.players (id_player, id_user, position, status) VALUES ('plyr19', 'usr18', 'defensa', 'convocado'); 		  
INSERT INTO MXLEAGUE.players (id_player, id_user, position, status) VALUES ('plyr20', 'usr19', 'defensa', 'suplente');
INSERT INTO MXLEAGUE.players (id_player, id_user, position, status) VALUES ('plyr21', 'usr20', 'defensa', 'convocado');


--4th STATISTICS TABLE

INSERT INTO MXLEAGUE.statistics (id_statistic,id_player,score,matchesplayed,goals) VALUES ('sta00','plyr21',100,10,10);
INSERT INTO MXLEAGUE.statistics (id_statistic,id_player,score,matchesplayed,goals) VALUES ('sta01','plyr11',89,8,8);
INSERT INTO MXLEAGUE.statistics (id_statistic,id_player,score,matchesplayed,goals) VALUES ('sta02','plyr08',78,5,9);
INSERT INTO MXLEAGUE.statistics (id_statistic,id_player,score,matchesplayed,goals) VALUES ('sta03','plyr04',66,3,10);
INSERT INTO MXLEAGUE.statistics (id_statistic,id_player,score,matchesplayed,goals) VALUES ('sta04','plyr13',55,7,10);
INSERT INTO MXLEAGUE.statistics (id_statistic,id_player,score,matchesplayed,goals) VALUES ('sta05','plyr20',33,2,15);
INSERT INTO MXLEAGUE.statistics (id_statistic,id_player,score,matchesplayed,goals) VALUES ('sta06','plyr12',5,1,3);

--5th TRANSFERS TABLE

INSERT INTO MXLEAGUE.transfers (id_transfer,id_player,amount,teamfrom,teamto) VALUES ('transfr00','plyr17',15,'ATLETICO DE MADRID','REAL MADRID');
INSERT INTO MXLEAGUE.transfers (id_transfer,id_player,amount,teamfrom,teamto) VALUES ('transfr01','plyr18',10,'GETAFE','ATLETICO DE MADRID');
INSERT INTO MXLEAGUE.transfers (id_transfer,id_player,amount,teamfrom,teamto) VALUES ('transfr02','plyr19',8,'ATLETICO DE MADRID','REAL MADRID');


--6th BOARD TABLE

INSERT INTO MXLEAGUE.board (id_employee,id_user,job) VALUES ('emplo01','tec06','coach');
INSERT INTO MXLEAGUE.board (id_employee,id_user,job) VALUES ('emplo02','tec07','vicepresident');
INSERT INTO MXLEAGUE.board (id_employee,id_user,job) VALUES ('emplo03','tec08','president');