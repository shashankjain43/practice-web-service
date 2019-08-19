package com.snapdeal.payments.view.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.snapdeal.payments.view.commons.enums.ClientRole;

public class ClientRoleTypeHandler implements TypeHandler<ClientRole>{

	public ClientRoleTypeHandler() {
		super();
	}

	@Override
	public ClientRole getResult(ResultSet rs, String columnName)
			throws SQLException {
		return ClientRole.valueOf(rs.getString(columnName));
	}

	@Override
	public ClientRole getResult(ResultSet rs, int columnIndex) throws SQLException {
		return ClientRole.valueOf(rs.getString(columnIndex));
	}

	@Override
	public ClientRole getResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		return ClientRole.valueOf(cs.getString(columnIndex));
	}

	@Override
	public void setParameter(PreparedStatement ps, int i,
			ClientRole clientRole, JdbcType arg3) throws SQLException {
		ps.setString(i, clientRole.name());
	}
}
