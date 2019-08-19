package com.snapdeal.payments.view.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.snapdeal.payments.view.merchant.commons.enums.MVTransactionStatus;

public class MVTransactionStatusHandler implements TypeHandler<MVTransactionStatus>{

	public MVTransactionStatusHandler() {
		super();
	}

	@Override
	public MVTransactionStatus getResult(ResultSet rs, String columnName)
			throws SQLException {
		return MVTransactionStatus.valueOf(rs.getString(columnName));
	}

	@Override
	public MVTransactionStatus getResult(ResultSet rs, int columnIndex) throws SQLException {
		return MVTransactionStatus.valueOf(rs.getString(columnIndex));
	}

	@Override
	public MVTransactionStatus getResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		return MVTransactionStatus.valueOf(cs.getString(columnIndex));
	}

	@Override
	public void setParameter(PreparedStatement ps, int i,
			MVTransactionStatus clientRole, JdbcType arg3) throws SQLException {
		ps.setString(i, clientRole.name());
	}
}
