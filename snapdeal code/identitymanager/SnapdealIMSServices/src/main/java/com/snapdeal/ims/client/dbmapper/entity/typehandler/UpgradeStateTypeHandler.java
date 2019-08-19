package com.snapdeal.ims.client.dbmapper.entity.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.snapdeal.ims.enums.State;

public class UpgradeStateTypeHandler implements TypeHandler<State>{
    public UpgradeStateTypeHandler() {
        super();
    }

    @Override
    public State getResult(ResultSet rs, String columnName)
            throws SQLException {
        return State.valueOf(rs.getString(columnName));
    }

    @Override
    public State getResult(ResultSet rs, int columnIndex) throws SQLException {
        return State.valueOf(rs.getString(columnIndex));
    }

    @Override
    public State getResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        return State.valueOf(cs.getString(columnIndex));
    }

    @Override
    public void setParameter(PreparedStatement ps, int i,
            State status, JdbcType arg3) throws SQLException {
        ps.setString(i, status.name());
    }
}
