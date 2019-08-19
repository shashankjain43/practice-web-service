package com.snapdeal.payments.view.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.snapdeal.payments.view.commons.enums.ClientPlatform;

public class ClientPlatformTypeHandler implements TypeHandler<ClientPlatform>{

	public ClientPlatformTypeHandler() {
		super();
	}

	@Override
	public ClientPlatform getResult(ResultSet rs, String columnName)
			throws SQLException {
		return ClientPlatform.valueOf(rs.getString(columnName));
	}

	@Override
	public ClientPlatform getResult(ResultSet rs, int columnIndex) throws SQLException {
		return ClientPlatform.valueOf(rs.getString(columnIndex));
	}

	@Override
	public ClientPlatform getResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		return ClientPlatform.valueOf(cs.getString(columnIndex));
	}

	@Override
	public void setParameter(PreparedStatement ps, int i,
			ClientPlatform clientStatus, JdbcType arg3) throws SQLException {
		ps.setString(i, clientStatus.name());
	}
}
