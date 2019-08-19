package com.snapdeal.ims.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnector {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/ims";

	static final String USER = "root";
   static final String PASS = "password@123";

	static Connection conn;
	static PreparedStatement stmt;

	public static boolean createConnection() {
		boolean connectionCreated = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			connectionCreated = true;
		} catch (SQLException se) {

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connectionCreated;
	}

	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException e2) {

				}
			}
		}
	}

	public static String getOtp(String otpId) {
		String otp = null;
		if (createConnection()) {

			try {
				System.out.println("Creating statement...");
				stmt = conn
						.prepareStatement("select otp from user_otp where otp_id = ? ");
				stmt.setString(1, otpId);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					otp = rs.getString(1);
				}
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				// finally block used to close resources
				try {
					if (stmt != null)
						stmt.close();
				} catch (SQLException se2) {
				}
			}
			closeConnection();
		}
		return otp;
	}

   public static String getPassword(String email) {
      String password = null;
      if (createConnection()) {

         try {
            System.out.println("Creating statement...");
            stmt = conn.prepareStatement("select password from user where email = ? ");
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
               password = rs.getString(1);
            }
            rs.close();
            stmt.close();
         } catch (SQLException e) {
            e.printStackTrace();
         } finally {
            // finally block used to close resources
            try {
               if (stmt != null)
                  stmt.close();
            } catch (SQLException se2) {
            }
         }
         closeConnection();
      }
      return password;
   }

   public static void changeUpgradeStatus(String emailId, String upgradeState, String currentState) {
      if (createConnection()) {

         try {
            System.out.println("Creating statement...");
            stmt = conn
                     .prepareStatement("update upgrade set current_state = ?, upgrade_status = ? where email = ? ");
            stmt.setString(1, currentState);
            stmt.setString(2, upgradeState);
            stmt.setString(3, emailId);
            stmt.executeUpdate();
            stmt.close();
         } catch (SQLException e) {
            e.printStackTrace();
         } finally {
            // finally block used to close resources
            try {
               if (stmt != null)
                  stmt.close();
            } catch (SQLException se2) {
            }
         }
         closeConnection();
      }
   }


	public static void main(String[] args) {
			String otp = getOtp("11922d79-1f1f-4727-bd7c-ef9ace09a27e");
			System.out.println("otp = " + otp);

			otp = getOtp("3a7a1905-c23a-4ec9-bd91-3ed6ad5ff652");
			System.out.println("otp = " + otp);
	}
}