package com.snapdeal.payments.view.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.snapdeal.payments.view.merchant.commons.enums.MVTransactionType;

public class MVTransactionTypeHandler implements TypeHandler<MVTransactionType>{

	public MVTransactionTypeHandler() {
		super();
	}

	@Override
	public MVTransactionType getResult(ResultSet rs, String columnName)
			throws SQLException {
		return MVTransactionType.valueOf(rs.getString(columnName));
	}

	@Override
	public MVTransactionType getResult(ResultSet rs, int columnIndex) throws SQLException {
		return MVTransactionType.valueOf(rs.getString(columnIndex));
	}

	@Override
	public MVTransactionType getResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		return MVTransactionType.valueOf(cs.getString(columnIndex));
	}

	@Override
	public void setParameter(PreparedStatement ps, int i,
			MVTransactionType clientStatus, JdbcType arg3) throws SQLException {
		ps.setString(i, clientStatus.name());
	}
}
