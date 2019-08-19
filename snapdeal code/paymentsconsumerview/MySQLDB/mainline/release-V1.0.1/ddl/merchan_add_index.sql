CREATE INDEX merchant_index ON txn_details (merchant_Id) ;
CREATE INDEX merchant_txn_id_index ON txn_details (merchant_txn_id) ;
CREATE INDEX custmer_id_index ON txn_details (customer_id) ;
CREATE INDEX txnid_txnstate_index ON txn_state_details (txn_id,txn_state) ;
CREATE INDEX merchant_txn_typ_index ON txn_details (merchant_txn_type) ;
