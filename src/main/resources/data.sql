INSERT INTO merchant (name,created_at) VALUES ('KFC',CURRENT_TIMESTAMP()),('5Star',CURRENT_TIMESTAMP());


INSERT INTO b_operator (name,description ,created_at) VALUES
('Equals','Can be used to check two entities are equal',CURRENT_TIMESTAMP()),
('Not Equals','Can be used to check two entities are not equal',CURRENT_TIMESTAMP()),
('Greater Than','Can be used to check two entities are greater than other',CURRENT_TIMESTAMP()),
('Greater Than or Equals To','Can be used to check two entities are greater than or equals to',CURRENT_TIMESTAMP()),
('In','Can be used to check if actual value exist in expected value',CURRENT_TIMESTAMP()),
('Not In','Can be used to check if actual value does not exist in expected value',CURRENT_TIMESTAMP()),
('Between','Can be used to check two entities are between',CURRENT_TIMESTAMP()),
('Not Between','Can be used to check two entities are not between',CURRENT_TIMESTAMP()),
('Like','Can be used to check two entities are like',CURRENT_TIMESTAMP());


INSERT INTO b_datatypes (name,description ,created_at) VALUES
('Integer','Integer', CURRENT_TIMESTAMP()),
('Double','Double',CURRENT_TIMESTAMP()),
('String','String',CURRENT_TIMESTAMP()),
('Time','Time',CURRENT_TIMESTAMP()),
('Date','Date',CURRENT_TIMESTAMP());


INSERT INTO b_fraud_entities (name,description ,datatype_id) VALUES
('amount','Transaction Amount',2),
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


INSERT INTO fraud_param_details (fraud_rule_name, merchant, f_param_api_id, fraud_entity_id, fraud_score, terminal_threat_threshold, fraud_criteria_value1, operator_id, exchange_code, to_curr_code) VALUES
('MaxTransAmount','KFC',1, 1, 40, 61, '500',3,'ABC', 'USD'),
('ValidTerminalIds','KFC',1, 3, 50, 50,'123,456,333',6,null,null);
