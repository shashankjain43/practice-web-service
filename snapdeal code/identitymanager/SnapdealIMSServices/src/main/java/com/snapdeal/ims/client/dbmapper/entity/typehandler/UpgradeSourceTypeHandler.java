package com.snapdeal.ims.client.dbmapper.entity.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.snapdeal.ims.enums.UpgradeSource;

public class UpgradeSourceTypeHandler implements TypeHandler<UpgradeSource>{
    public UpgradeSourceTypeHandler() {
        super();
    }

    @Override
    public UpgradeSource getResult(ResultSet rs, String columnName)
            throws SQLException {
        return UpgradeSource.valueOf(rs.getString(columnName));
    }

    @Override
    public UpgradeSource getResult(ResultSet rs, int columnIndex) throws SQLException {
        return UpgradeSource.valueOf(rs.getString(columnIndex));
    }

    @Override
    public UpgradeSource getResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        return UpgradeSource.valueOf(cs.getString(columnIndex));
    }

    @Override
    public void setParameter(PreparedStatement ps, int i,
             UpgradeSource clientStatus, JdbcType arg3) throws SQLException {
        ps.setString(i, clientStatus.name());
    }
}
