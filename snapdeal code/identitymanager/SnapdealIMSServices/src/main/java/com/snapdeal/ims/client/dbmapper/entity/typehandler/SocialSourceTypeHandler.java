package com.snapdeal.ims.client.dbmapper.entity.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.snapdeal.ims.enums.SocialSource;

public class SocialSourceTypeHandler implements TypeHandler<SocialSource>{
    public SocialSourceTypeHandler() {
        super();
    }

    @Override
    public SocialSource getResult(ResultSet rs, String columnName)
            throws SQLException {
        return SocialSource.valueOf(rs.getString(columnName));
    }

    @Override
    public SocialSource getResult(ResultSet rs, int columnIndex) throws SQLException {
        return SocialSource.valueOf(rs.getString(columnIndex));
    }

    @Override
    public SocialSource getResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        return SocialSource.valueOf(cs.getString(columnIndex));
    }

    @Override
    public void setParameter(PreparedStatement ps, int i,
             SocialSource clientStatus, JdbcType arg3) throws SQLException {
        ps.setString(i, clientStatus.name());
    }
}
