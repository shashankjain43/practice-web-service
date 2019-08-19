package com.snapdeal.ims.client.dbmapper.entity.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.snapdeal.ims.enums.UpgradeChannel;

public class UpgradeChannelTypeHandler implements TypeHandler<UpgradeChannel>{
    public UpgradeChannelTypeHandler() {
        super();
    }

    @Override
    public UpgradeChannel getResult(ResultSet rs, String columnName)
            throws SQLException {
        return UpgradeChannel.valueOf(rs.getString(columnName));
    }

    @Override
    public UpgradeChannel getResult(ResultSet rs, int columnIndex) throws SQLException {
        return UpgradeChannel.valueOf(rs.getString(columnIndex));
    }

    @Override
    public UpgradeChannel getResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        return UpgradeChannel.valueOf(cs.getString(columnIndex));
    }

    @Override
    public void setParameter(PreparedStatement ps, int i,
             UpgradeChannel clientStatus, JdbcType arg3) throws SQLException {
        ps.setString(i, clientStatus.name());
    }
}
