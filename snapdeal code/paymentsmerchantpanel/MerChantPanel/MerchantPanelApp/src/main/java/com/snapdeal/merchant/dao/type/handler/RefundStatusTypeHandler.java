package com.snapdeal.merchant.dao.type.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.snapdeal.merchant.enums.RefundStatus;


public class RefundStatusTypeHandler implements TypeHandler<RefundStatus>{

	@Override
	public RefundStatus getResult(ResultSet rs, String colName) throws SQLException {
		return RefundStatus.valueOf(rs.getString(colName));
	}

	@Override
	public RefundStatus getResult(ResultSet rs, int colIndex) throws SQLException {
		return RefundStatus.valueOf(rs.getString(colIndex));
	}

	@Override
	public RefundStatus getResult(CallableStatement stmt, int colIndex) throws SQLException {
		return RefundStatus.valueOf(stmt.getString(colIndex));
	}

	@Override
	public void setParameter(PreparedStatement stmt, int col, RefundStatus status, JdbcType type) throws SQLException {
		stmt.setString(col, status.name());
	}

}
