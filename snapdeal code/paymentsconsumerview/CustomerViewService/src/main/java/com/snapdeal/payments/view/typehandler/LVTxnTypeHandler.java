package com.snapdeal.payments.view.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.snapdeal.payments.view.load.enums.LoadCashTxnType;

public class LVTxnTypeHandler implements TypeHandler<LoadCashTxnType>{

	public LVTxnTypeHandler() {
		super();
	}

	@Override
	public LoadCashTxnType getResult(ResultSet rs, String columnName)
			throws SQLException {
		return LoadCashTxnType.valueOf(rs.getString(columnName));
	}

	@Override
	public LoadCashTxnType getResult(ResultSet rs, int columnIndex) throws SQLException {
		return LoadCashTxnType.valueOf(rs.getString(columnIndex));
	}

	@Override
	public LoadCashTxnType getResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		return LoadCashTxnType.valueOf(cs.getString(columnIndex));
	}

	@Override
	public void setParameter(PreparedStatement ps, int i,
			LoadCashTxnType txnType, JdbcType arg3) throws SQLException {
		ps.setString(i, txnType.name());
	}
}
