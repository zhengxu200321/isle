package com.isle.util;

import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class DBHelper {

	private static Logger logger = Logger.getLogger("logfile");

	private static DBHelper instance = new DBHelper();
	private static BasicDataSource writeDataSource = null;
	public static AtomicLong count = new AtomicLong(0);

	private ResourceBundle rb = null;

	private DBHelper() {

	}
	public static DBHelper getInstance() {
		return instance;
	}

	private void init() {
		try {
			rb = ResourceBundle.getBundle("jdbc");
			Properties p = new Properties();
			p.setProperty("driverClassName", rb.getString("driver"));
			p.setProperty("url", rb.getString("url"));
			p.setProperty("password", rb.getString("password"));
			p.setProperty("username", rb.getString("username"));
			p.setProperty("maxActive", "100");
			p.setProperty("maxIdle", "10");
			p.setProperty("initialSize", "10");
			p.setProperty("maxWait", "5000");
			p.setProperty("validationQuery", "SELECT 1");
			p.setProperty("testWhileIdle", "true");
			p.setProperty("timeBetweenEvictionRunsMillis", "30000");
			p.setProperty("removeAbandoned", "false");
			p.setProperty("removeAbandonedTimeout", "120");
			p.setProperty("testOnBorrow", "false");
			p.setProperty("logAbandoned", "true");
			writeDataSource = (BasicDataSource) BasicDataSourceFactory.createDataSource(p);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			System.exit(0);
		}
	}

	private Connection getWriteConnection() throws SQLException {
		if (writeDataSource == null) {
			init();
		}
		Connection conn = writeDataSource.getConnection();

		if (conn == null) {
			logger.error("init db connction error.");
			return null;
		}

		return conn;
	}

	private Connection getReadConnection() throws SQLException {
		if (writeDataSource == null) {
			init();
		}
		Connection conn = writeDataSource.getConnection();

		if (conn == null) {
			logger.error("init db connction error.");
			return null;
		}

		return conn;
	}

	public int updateSql(String sql, Object[] params) {
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			conn = getWriteConnection();
			pstmt = conn.prepareStatement(sql);
			if (params != null && params.length != 0) {
				int index = 1;
				for (Object param : params) {
					pstmt.setObject(index++, param);
				}
			}
			return pstmt.executeUpdate();
		} catch (Exception exp) {
			exp.printStackTrace();
			logger.error(exp.getMessage(), exp);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
					pstmt = null;
				}

				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception exp) {
				logger.error(exp.getMessage(), exp);
			}
		}
		return 0;
	}

	public long insertSql(String sql, Object[] params) {
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			conn = getWriteConnection();
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			if (params != null && params.length != 0) {
				int index = 1;
				for (Object param : params) {
					pstmt.setObject(index++, param);
				}
			}
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			// The generated order id
			return rs.getLong(1);
		} catch (Exception exp) {
			logger.error(exp.getMessage(), exp);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
					pstmt = null;
				}

				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception exp) {
				logger.error(exp.getMessage(), exp);
			}
		}
		return 0;
	}
	public long insertSql1(String sql, Object[] params) {
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			conn = getWriteConnection();
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			if (params != null && params.length != 0) {
				int index = 1;
				for (Object param : params) {
					pstmt.setObject(index++, param);
				}
			}
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			// The generated order id
			return rs.getLong(1);
		} catch (Exception exp) {
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
					pstmt = null;
				}
				
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception exp) {
//				logger.error(exp.getMessage(), exp);
			}
		}
		return 0;
	}


	/**
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> getDataMap(String sql) {
		List<Map<String, Object>> rtn = new ArrayList<Map<String, Object>>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			if (count.longValue() % 3 == 0) {
				conn = getReadConnection();
			} else {
				conn = getWriteConnection();
			}

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			List<String> fields = new ArrayList<String>(columnCount);
			for (int i = 1; i <= columnCount; ++i) {
				fields.add(rsmd.getColumnLabel(i));
			}
			rsmd = null;
			Map<String, Object> row = null;
			while (rs.next()) {
				row = new HashMap<String, Object>(columnCount);
				for (String obj : fields) {
					row.put(obj, rs.getObject(obj));
				}
				rtn.add(row);
			}
		} catch (Exception exp) {
			logger.error(exp.getMessage(), exp);
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (pstmt != null) {
					pstmt.close();
					pstmt = null;
				}

				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception exp) {
				logger.error(exp.getMessage(), exp);
			}
		}
		return rtn;
	}

	/**
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> getDataMap(String sql, Object[] params) {
		List<Map<String, Object>> rtn = new ArrayList<Map<String, Object>>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			if (count.longValue() % 3 == 0) {
				conn = getReadConnection();
			} else {
				conn = getWriteConnection();
			}

			pstmt = conn.prepareStatement(sql);
			if (params != null && params.length != 0) {
				int index = 1;
				for (Object param : params) {
					pstmt.setObject(index++, param);
				}
			}
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			List<String> fields = new ArrayList<String>(columnCount);
			for (int i = 1; i <= columnCount; ++i) {
				fields.add(rsmd.getColumnLabel(i));
			}
			rsmd = null;
			Map<String, Object> row = null;
			while (rs.next()) {
				row = new HashMap<String, Object>(columnCount);
				for (String obj : fields) {
					row.put(obj, rs.getObject(obj));
				}
				rtn.add(row);
			}
		} catch (Exception exp) {
			logger.error(exp.getMessage(), exp);
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (pstmt != null) {
					pstmt.close();
					pstmt = null;
				}

				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception exp) {
				logger.error(exp.getMessage(), exp);
			}
		}
		return rtn;
	}

	public <T> List<T> getData(String sql, Object[] params, Class<T> obj) {

		List<T> rtnList = new ArrayList<T>();
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			if (count.longValue() % 3 == 0) {
				conn = getReadConnection();
			} else {
				conn = getWriteConnection();
			}

			pstmt = conn.prepareStatement(sql);
			if (params != null && params.length != 0) {
				int index = 1;
				for (Object param : params) {
					pstmt.setObject(index++, param);
				}
			}
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			List<String> fields = new ArrayList<String>(columnCount);
			for (int i = 1; i <= columnCount; ++i) {
				fields.add(rsmd.getColumnLabel(i));
			}
			rsmd = null;
			while (rs.next()) {
				T rtn = obj.newInstance();
				for (String f : fields) {
					try {
						PropertyUtils.setProperty(rtn, f, rs.getObject(f));
					} catch (Exception exp) {
					}
				}
				rtnList.add(rtn);
			}
		} catch (Exception exp) {
			logger.error(exp.getMessage(), exp);
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (pstmt != null) {
					pstmt.close();
					pstmt = null;
				}

				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception exp) {
				logger.error(exp.getMessage(), exp);
			}
		}
		return rtnList;
	}

	public int selectOrder(String sql) {
		List<Map<String, Object>> rtn = new ArrayList<Map<String, Object>>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		int i = 0;
		try {
			if (count.longValue() % 3 == 0) {
				conn = getReadConnection();
			} else {
				conn = getWriteConnection();
			}

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				i++;
			}
		} catch (Exception exp) {
			logger.error(exp.getMessage(), exp);
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (pstmt != null) {
					pstmt.close();
					pstmt = null;
				}

				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception exp) {
				logger.error(exp.getMessage(), exp);
			}
		}
		return i;
	}

	public <T> List<T> getQDOrderData(String sql, Object[] params, Class<T> obj) {

		List<T> rtnList = new ArrayList<T>();
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			if (count.longValue() % 3 == 0) {
				conn = getReadConnection();
			} else {
				conn = getWriteConnection();
			}

			pstmt = conn.prepareStatement(sql);
			if (params != null && params.length != 0) {
				int index = 1;
				for (Object param : params) {
					pstmt.setObject(index++, param);
				}
			}
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			List<String> fields = new ArrayList<String>(columnCount);
			for (int i = 1; i <= columnCount; ++i) {
				fields.add(rsmd.getColumnLabel(i));
			}
			rsmd = null;
			while (rs.next()) {
				T rtn = obj.newInstance();
				PropertyUtils.setProperty(rtn, "id", rs.getObject("id"));
				PropertyUtils.setProperty(rtn, "orderNo", rs.getObject("order_no"));
				PropertyUtils.setProperty(rtn, "createTime", rs.getObject("create_time"));
				PropertyUtils.setProperty(rtn, "pnrCode", rs.getObject("pnr_code"));
				PropertyUtils.setProperty(rtn, "flightInfo", rs.getObject("flight_info"));
				PropertyUtils.setProperty(rtn, "passengerInfo", rs.getObject("passenger_info"));
				PropertyUtils.setProperty(rtn, "flightNum", rs.getObject("flight_num"));
				PropertyUtils.setProperty(rtn, "issueBillID", rs.getObject("issueBillID"));
				PropertyUtils.setProperty(rtn, "flightInfo", rs.getObject("flight_info"));
				PropertyUtils.setProperty(rtn, "passengerName", rs.getObject("passenger_name"));				
				rtnList.add(rtn);
			}
		} catch (Exception exp) {
			logger.error(exp.getMessage(), exp);
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (pstmt != null) {
					pstmt.close();
					pstmt = null;
				}

				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception exp) {
				logger.error(exp.getMessage(), exp);
			}
		}
		return rtnList;
	}

	public static void main(String[] args) {
		DBHelper.getInstance().updateSql(
				"insert into emailOrder (pnr,dep,depDate,arr,arrDate,url,jsonText) values(?,?,?,?,?,?,?)",
				new Object[] { 1, 2, 3, 4, 5, 6, 7 });
	}
}
