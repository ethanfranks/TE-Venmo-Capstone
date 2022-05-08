BEGIN TRANSACTION;

DROP TABLE IF EXISTS transfer, account, tenmo_user, transfer_type, transfer_status;
DROP SEQUENCE IF EXISTS seq_user_id, seq_account_id, seq_transfer_id;


CREATE TABLE transfer_type (
	transfer_type_id serial NOT NULL,
	transfer_type_desc varchar(10) NOT NULL,
	CONSTRAINT PK_transfer_type PRIMARY KEY (transfer_type_id)
);

CREATE TABLE transfer_status (
	transfer_status_id serial NOT NULL,
	transfer_status_desc varchar(10) NOT NULL,
	CONSTRAINT PK_transfer_status PRIMARY KEY (transfer_status_id)
);

CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  START WITH 1001
  NO MAXVALUE;

CREATE TABLE tenmo_user (
	user_id int NOT NULL DEFAULT nextval('seq_user_id'),
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	CONSTRAINT PK_tenmo_user PRIMARY KEY (user_id),
	CONSTRAINT UQ_username UNIQUE (username)
);

CREATE SEQUENCE seq_account_id
  INCREMENT BY 1
  START WITH 2001
  NO MAXVALUE;

CREATE TABLE account (
	account_id int NOT NULL DEFAULT nextval('seq_account_id'),
	user_id int NOT NULL,
	balance decimal(13, 2) NOT NULL,
	CONSTRAINT PK_account PRIMARY KEY (account_id),
	CONSTRAINT FK_account_tenmo_user FOREIGN KEY (user_id) REFERENCES tenmo_user (user_id)
);

CREATE SEQUENCE seq_transfer_id
  INCREMENT BY 1
  START WITH 3001
  NO MAXVALUE;

CREATE TABLE transfer (
	transfer_id int NOT NULL DEFAULT nextval('seq_transfer_id'),
	transfer_type_id int NOT NULL,
	transfer_status_id int NOT NULL,
	account_from int NOT NULL,
	account_to int NOT NULL,
	amount decimal(13, 2) NOT NULL,
	CONSTRAINT PK_transfer PRIMARY KEY (transfer_id),
	CONSTRAINT FK_transfer_account_from FOREIGN KEY (account_from) REFERENCES account (account_id),
	CONSTRAINT FK_transfer_account_to FOREIGN KEY (account_to) REFERENCES account (account_id),
	CONSTRAINT FK_transfer_transfer_status FOREIGN KEY (transfer_status_id) REFERENCES transfer_status (transfer_status_id),
	CONSTRAINT FK_transfer_transfer_type FOREIGN KEY (transfer_type_id) REFERENCES transfer_type (transfer_type_id),
	CONSTRAINT CK_transfer_not_same_account CHECK (account_from <> account_to),
	CONSTRAINT CK_transfer_amount_gt_0 CHECK (amount > 0)
    );


INSERT INTO transfer_status (transfer_status_desc) VALUES ('Pending');
INSERT INTO transfer_status (transfer_status_desc) VALUES ('Approved');
INSERT INTO transfer_status (transfer_status_desc) VALUES ('Rejected');

INSERT INTO transfer_type (transfer_type_desc) VALUES ('Request');
INSERT INTO transfer_type (transfer_type_desc) VALUES ('Send');



-- Need Users to create accounts
INSERT INTO tenmo_user (username, password_hash)
VALUES ('User1', 'pword'), -- user_id will be 1001 due to serial
       ('User2', 'pword'), -- user_id will be 1002 due to serial
       ('User3', 'pword'); -- user_id will be 1003 due to serial

-- Need accounts to create transfers
INSERT INTO account (user_id, balance)
VALUES (1001, 1000), -- account_id will be 2001 due to serial
       (1002, 2000), -- account_id will be 2002 due to serial
       (1003, 3000); -- account_id will be 2002 due to serial

-- Need transfers .. because that's the point of the app
INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount)
VALUES (2, 2, 2001, 2002, 100.00), -- transfer_id will be 3001 due to serial
       (2, 2, 2002, 2003, 200.00), -- transfer_id will be 3002 due to serial
       (2, 2, 2003, 2001, 300.00), -- transfer_id will be 3003 due to serial
       (1, 2, 2001, 2002, 400.00), -- transfer_id will be 3004 due to serial
       (1, 2, 2002, 2003, 500.00), -- transfer_id will be 3005 due to serial
       (1, 2, 2003, 2001, 600.00); -- transfer_id will be 3006 due to serial

COMMIT;
