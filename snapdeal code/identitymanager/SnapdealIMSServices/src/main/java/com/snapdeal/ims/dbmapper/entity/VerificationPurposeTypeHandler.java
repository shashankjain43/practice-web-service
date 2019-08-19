package com.snapdeal.ims.dbmapper.entity;


import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VerificationPurposeTypeHandler implements
		TypeHandler<VerificationPurpose> {
	@Override
	public void setParameter(PreparedStatement ps, int i,
			VerificationPurpose parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setString(i, parameter.getValue());
	}

	@Override
	public VerificationPurpose getResult(ResultSet rs, String columnName)
			throws SQLException {
		return VerificationPurpose.valueOf(rs.getString(columnName));
	}

	@Override
	public VerificationPurpose getResult(ResultSet rs, int columnIndex)
			throws SQLException {
		return VerificationPurpose.valueOf(rs.getString(columnIndex));
	}

	@Override
	public VerificationPurpose getResult(CallableStatement cs,
			int columnIndex) throws SQLException {
		return VerificationPurpose.valueOf(cs.getString(columnIndex));
	}
}