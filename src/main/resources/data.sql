INSERT INTO merchant (name,created_at) VALUES ('KFC','2022-12-31 23.59.59'),('5Star','2022-12-31 23.59.59');


INSERT INTO b_operator (name,description ,created_at) VALUES 
('Equals','Can be used to check two entities are equal','2022-12-31 23.59.59'),
('Not Equals','Can be used to check two entities are not equal','2022-12-31 23.59.59'),
('Greater Than','Can be used to check two entities are greater than other','2022-12-31 23.59.59'),
('Greater Than or Equals To','Can be used to check two entities are greater than or equals to','2022-12-31 23.59.59'),
('In','Can be used to check if actual value exist in expected value','2022-12-31 23.59.59'),
('Not In','Can be used to check if actual value is notexist in expected value','2022-12-31 23.59.59'),
('Between','Can be used to check two entities are between','2022-12-31 23.59.59'),
('Not Between','Can be used to check two entities are not between','2022-12-31 23.59.59'),
('Like','Can be used to check two entities are like','2022-12-31 23.59.59');


INSERT INTO b_datatypes (name,description ,created_at) VALUES 
('Integer','Integer','2022-12-31 23.59.59'),
('Double','Double','2022-12-31 23.59.59'),
('String','String','2022-12-31 23.59.59'),
('Time','Time','2022-12-31 23.59.59'),
('Date','Date','2022-12-31 23.59.59');


INSERT INTO b_fraud_entities (name,description ,datatype_id) VALUES 
('Amount','Transaction Amount',2),
('city','City',3),
('terminalId','TerminalId',3);


INSERT INTO exchange_rates (exchange_code,from_curr_code, to_curr_code ,rate) VALUES 
('ABC','EUR','USD','22.3');


INSERT INTO b_fraud_type (f_type_code, name, description) VALUES 
('O','Operational Fraud','Operational Fraud'),
('V','Velocity Fraud','Velocity Fraud');


INSERT INTO b_fraud_param_api (name, description, f_type_code,class_detail) VALUES 
('TwoValueCompatator','Used to validate fraud rules in which 2 entities are required','O','OperationalFraudEvaluatorImpl');


INSERT INTO b_unit (unit_code, name, description) VALUES 
('D','Day','Day time window'),
('S','Second','Second time window'),
('M','Minute','Minute time window'),
('H','Hour','Hour time window'),
('W','Week','Week time window');



INSERT INTO fraud_score_level (score_lvl, name, min_range,max_range) VALUES 
('L','Low',0, 39),
('M','Medium',40, 59),
('H','High',50, 100);


INSERT INTO fraud_param_details (f_param, merchant, f_param_api_id, f_entity_id, f_score, f_tml_thd_score, f_criteria_value1, operator_id, exchange_code, to_curr_code ) VALUES 
('MaxTransAmount','KFC',1, 1, 40, 61, '500',3,'ABC', 'USD'),
('ValidTerminalIds','KFC',1, 3, 50, 50,'123,456,333',6,null,null);




