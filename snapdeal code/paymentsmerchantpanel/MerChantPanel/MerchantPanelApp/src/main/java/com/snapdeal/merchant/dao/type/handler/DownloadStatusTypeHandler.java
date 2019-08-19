package com.snapdeal.merchant.dao.type.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.snapdeal.merchant.enums.DownloadStatus;

public class DownloadStatusTypeHandler implements TypeHandler<DownloadStatus> {

	@Override
	public DownloadStatus getResult(ResultSet rs, String colName) throws SQLException {
		return DownloadStatus.valueOf(rs.getString(colName));
	}

	@Override
	public DownloadStatus getResult(ResultSet rs, int colIndex) throws SQLException {
		return DownloadStatus.valueOf(rs.getString(colIndex));
	}

	@Override
	public DownloadStatus getResult(CallableStatement stmt, int colIndex) throws SQLException {
		return DownloadStatus.valueOf(stmt.getString(colIndex));
	}

	@Override
	public void setParameter(PreparedStatement stmt, int col, DownloadStatus status, JdbcType type) throws SQLException {
		stmt.setString(col, status.name());
	}

}
