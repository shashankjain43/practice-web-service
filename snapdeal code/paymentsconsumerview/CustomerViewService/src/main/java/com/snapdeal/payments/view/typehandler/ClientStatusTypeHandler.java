package com.snapdeal.payments.view.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.snapdeal.payments.view.commons.enums.ClientStatus;

public class ClientStatusTypeHandler implements TypeHandler<ClientStatus>{
	
	public ClientStatusTypeHandler() {
		super();
	}

	@Override
	public ClientStatus getResult(ResultSet rs, String columnName)
			throws SQLException {
		return ClientStatus.valueOf(rs.getString(columnName));
	}

	@Override
	public ClientStatus getResult(ResultSet rs, int columnIndex) throws SQLException {
		return ClientStatus.valueOf(rs.getString(columnIndex));
	}

	@Override
	public ClientStatus getResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		return ClientStatus.valueOf(cs.getString(columnIndex));
	}

	@Override
	public void setParameter(PreparedStatement ps, int i,
			ClientStatus clientStatus, JdbcType arg3) throws SQLException {
		ps.setString(i, clientStatus.name());
	}
}
