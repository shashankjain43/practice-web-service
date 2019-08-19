package com.snapdeal.ims.client.dbmapper.entity.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.snapdeal.ims.enums.Gender;

public class GenderTypeHandler implements TypeHandler<Gender>{

	@Override
	public void setParameter(PreparedStatement ps, int i, Gender gender,
			JdbcType jdbcType) throws SQLException {
		if(gender!=null){
			ps.setString(i, gender.getValue());
		}else{
			ps.setString(i, null);
		}
	}

	@Override
	public Gender getResult(ResultSet rs, String columnName)
			throws SQLException {
		return Gender.forValue(rs.getString(columnName));
	}

	@Override
	public Gender getResult(ResultSet rs, int columnIndex) throws SQLException {
		return Gender.forValue(rs.getString(columnIndex));
	}

	@Override
	public Gender getResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		return Gender.forValue(cs.getString(columnIndex));
	}

}
