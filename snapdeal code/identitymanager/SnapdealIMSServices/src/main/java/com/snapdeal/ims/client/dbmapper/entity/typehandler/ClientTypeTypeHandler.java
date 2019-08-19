package com.snapdeal.ims.client.dbmapper.entity.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.snapdeal.ims.client.dbmapper.entity.info.ClientType;

public class ClientTypeTypeHandler implements TypeHandler<ClientType>{

	public ClientTypeTypeHandler() {
		super();
	}

	@Override
	public ClientType getResult(ResultSet rs, String columnName)
			throws SQLException {
		return ClientType.valueOf(rs.getString(columnName));
	}

	@Override
	public ClientType getResult(ResultSet rs, int columnIndex) throws SQLException {
		return ClientType.valueOf(rs.getString(columnIndex));
	}

	@Override
	public ClientType getResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		return ClientType.valueOf(cs.getString(columnIndex));
	}

	@Override
	public void setParameter(PreparedStatement ps, int i,
			ClientType clientStatus, JdbcType arg3) throws SQLException {
		ps.setString(i, clientStatus.name());
	}
}
