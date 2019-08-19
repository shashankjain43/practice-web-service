package com.snapdeal.payments.view.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.snapdeal.payments.view.load.enums.LoadCashTxnStatus;

public class LVTxnStatusHandler implements TypeHandler<LoadCashTxnStatus>{

	public LVTxnStatusHandler() {
		super();
	}

	@Override
	public LoadCashTxnStatus getResult(ResultSet rs, String columnName)
			throws SQLException {
		return LoadCashTxnStatus.valueOf(rs.getString(columnName));
	}

	@Override
	public LoadCashTxnStatus getResult(ResultSet rs, int columnIndex) throws SQLException {
		return LoadCashTxnStatus.valueOf(rs.getString(columnIndex));
	}

	@Override
	public LoadCashTxnStatus getResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		return LoadCashTxnStatus.valueOf(cs.getString(columnIndex));
	}

	@Override
	public void setParameter(PreparedStatement ps, int i,
			LoadCashTxnStatus txnStatus, JdbcType arg3) throws SQLException {
		ps.setString(i, txnStatus.name());
	}
}
