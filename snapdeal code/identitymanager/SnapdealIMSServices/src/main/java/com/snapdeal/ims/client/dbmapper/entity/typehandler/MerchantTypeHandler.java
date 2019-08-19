package com.snapdeal.ims.client.dbmapper.entity.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.snapdeal.ims.enums.Merchant;

public class MerchantTypeHandler implements TypeHandler<Merchant>{
	public MerchantTypeHandler() {
		super();
	}

	@Override
	public Merchant getResult(ResultSet rs, String columnName)
			throws SQLException {
		return Merchant.valueOf(rs.getString(columnName));
	}

	@Override
	public Merchant getResult(ResultSet rs, int columnIndex) throws SQLException {
		return Merchant.valueOf(rs.getString(columnIndex));
	}

	@Override
	public Merchant getResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		return Merchant.valueOf(cs.getString(columnIndex));
	}

	@Override
	public void setParameter(PreparedStatement ps, int i,
			Merchant clientStatus, JdbcType arg3) throws SQLException {
		ps.setString(i, clientStatus.name());
	}
}
