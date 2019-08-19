package com.snapdeal.ims.client.dbmapper.entity.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.snapdeal.ims.enums.Upgrade;

public class UpgradeStatusTypeHandler implements TypeHandler<Upgrade>{
    public UpgradeStatusTypeHandler() {
        super();
    }

    @Override
    public Upgrade getResult(ResultSet rs, String columnName)
            throws SQLException {
        return Upgrade.valueOf(rs.getString(columnName));
    }

    @Override
    public Upgrade getResult(ResultSet rs, int columnIndex) throws SQLException {
        return Upgrade.valueOf(rs.getString(columnIndex));
    }

    @Override
    public Upgrade getResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        return Upgrade.valueOf(cs.getString(columnIndex));
    }

    @Override
    public void setParameter(PreparedStatement ps, int i,
            Upgrade clientStatus, JdbcType arg3) throws SQLException {
        ps.setString(i, clientStatus.name());
    }
}
