CREATE INDEX user_id_index ON global_transaction (user_id) ;
CREATE INDEX global_txn_id_index ON global_transaction (global_txn_id) ;
CREATE INDEX merchant_name_index ON global_transaction (merchant_name) ;
CREATE INDEX total_transaction_amount_index ON global_transaction (total_transaction_amount) ;
CREATE INDEX created_on_index ON global_transaction (created_on) ;

CREATE INDEX global_transaction_index ON local_transaction (global_transaction_id) ;
CREATE INDEX local_transaction_index ON local_transaction (local_transaction_id) ;

