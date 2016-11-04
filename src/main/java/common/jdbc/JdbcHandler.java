package common.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;


public class JdbcHandler
{

	private String URL;
	private String DRIVER;
	private String USER;
	private String PASSWORD;

	private JdbcConfig jdbcConfig;
	private JdbcPoolHandler jdbcPoolHandler;

	private Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public JdbcHandler(String url, String driver, String user, String password) {
		this.init(url, driver, user, password);
	}

	private void init(String url, String driver, String user, String password)
	{
		LOGGER.info("Loaded from properties - URL: " + URL + " DRIVER: " + DRIVER);
		//
		this.URL = url;
		this.DRIVER = driver;
		this.USER = user;
		this.PASSWORD = password;
		//
		this.jdbcConfig = new JdbcConfig(URL, DRIVER, USER, PASSWORD);
		this.jdbcPoolHandler = new JdbcPoolHandler();
		this.jdbcPoolHandler.setJdbcConfig(jdbcConfig);
	}

	private void logQuery(String sql)
	{
	}

    public int updateQuery(String sql) throws SQLException
    {
	if (!sql.isEmpty())
	{
	    //
	    logQuery(sql);
	    //
	    Connection connection = this.jdbcPoolHandler.getConnection(this.jdbcPoolHandler);
	    Statement statement = null;
	    List resultList = null;
	    int updatedCount = 0;

	    try
	    {
		statement = connection.createStatement();
		// statement = this.connection.prepareStatement(sql);
		updatedCount = statement.executeUpdate(sql);

	    } catch (SQLException e)
	    {
		LOGGER.log(Level.SEVERE, "JdbcHandler: ERROR executing Query : " + sql, e);
		throw new SQLException();
	    } finally
	    {
		if (statement != null)
		{
		    statement.close();
		}
		if (connection != null)
		{
		    connection.close();
		}
	    }
	    return updatedCount;
	} else
	{
	    return 0;
	}
    }

	public List query(String sql) throws SQLException
	{
		if (!sql.isEmpty())
		{
			//
			logQuery(sql);
			//
			Connection connection = this.jdbcPoolHandler.getConnection(this.jdbcPoolHandler);
			Statement statement = null;
			List resultList = null;
			ResultSet resultSet = null;

			try
			{
				statement = connection.createStatement();
				// statement = this.connection.prepareStatement(sql);
				resultSet = statement.executeQuery(sql);
				resultList = resultSetToArrayList(resultSet);

			} catch (SQLException e)
			{
				LOGGER.log(Level.SEVERE, "JdbcHandler: ERROR executing Query : " + sql, e);
				throw new SQLException();
			} finally
			{
				if (resultSet != null)
				{
					resultSet.close();
				}
				if (statement != null)
				{
					statement.close();
				}
				if (connection != null)
				{
					connection.close();
				}
			}
			return resultList;
		} else
		{
			return null;
		}
	}

	public JSONArray queryJson(String sql) throws Exception
	{
		if (!sql.isEmpty())
		{
			//
			logQuery(sql);
			//
			Connection connection = this.jdbcPoolHandler.getConnection(this.jdbcPoolHandler);
			Statement statement = null;
			JSONArray jsonArray = null;
			ResultSet resultSet = null;
			try
			{
				statement = connection.createStatement();
				// statement = this.connection.prepareStatement(sql);
				resultSet = statement.executeQuery(sql);
				jsonArray = resultSetToJsonArray(resultSet);

			} catch (SQLException e)
			{
				LOGGER.log(Level.SEVERE, "JdbcHandler: ERROR executing queryJson : " + sql, e);
				throw new SQLException();
			} finally
			{
				if (resultSet != null)
				{
					resultSet.close();
				}
				if (statement != null)
				{
					statement.close();
				}
				if (connection != null)
				{
					connection.close();
				}
			}
			return jsonArray;
		} else
		{
			return null;
		}
	}

	public void insert(String sql) throws SQLException
	{
		//
		logQuery(sql);
		//
		Connection connection = this.jdbcPoolHandler.getConnection(this.jdbcPoolHandler);
		Statement statement = null;

		try
		{
			statement = connection.createStatement();
			statement.execute(sql);
		} catch (SQLException e)
		{
			LOGGER.log(Level.SEVERE, "JdbcHandler: ERROR executing INSERT Query : " + sql, e);
			throw new SQLException();
		} finally
		{
			if (statement != null)
			{
				statement.close();
			}
			if (connection != null)
			{
				connection.close();
			}
		}
	}
	
	/**
	 * the order of objects in params list should be according to sql query
	 * @param sql
	 * @param params 
	 * @throws SQLException
	 */
	public void insert(String sql, List<Object> params) throws SQLException
	{
		logQuery(sql);
		
		Connection connection = JdbcPoolHandler.getConnection(this.jdbcPoolHandler);
		PreparedStatement statement = null;

		try
		{
			statement = connection.prepareStatement(sql);
			if(params != null){
			    for(int i = 0; i < params.size(); i++){
				statement.setObject(i+1, params.get(i));
			    }
			}
			statement.executeUpdate();
			
		} catch (SQLException e)
		{	
		    	e.printStackTrace();
			LOGGER.log(Level.SEVERE, "JdbcHandler: ERROR executing INSERT Query : " + sql, e);
			throw new SQLException();
		} finally
		{
			if (statement != null)
			{
				statement.close();
			}
			if (connection != null)
			{
				connection.close();
			}
		}
	}

	public int insertQueryReturnId(String sql)
	{
		int id = -1;
		Connection connection = this.jdbcPoolHandler.getConnection(this.jdbcPoolHandler);
		PreparedStatement statement = null;
		try
		{
			statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0)
			{
				throw new SQLException("Insertion failed, no rows affected.");
			}
			try (
					ResultSet generatedKeys = statement.getGeneratedKeys())
			{
				if (generatedKeys.next())
				{
					id = generatedKeys.getInt(1);
				} else
				{
					throw new SQLException("Insertion failed, no ID obtained.");
				}
			}
		} catch (SQLException e)
		{
			LOGGER.log(Level.SEVERE, "JdbcHandler: ERROR executing INSERT + Return ID Query : " + sql, e);
		} finally
		{
			if (statement != null)
			{
				try
				{
					statement.close();
				} catch (SQLException e)
				{
					LOGGER.log(Level.SEVERE, "JdbcHandler: ERROR closing Statement after INSERT + Return ID Query : " + sql, e);
				}
			}
			if (connection != null)
			{
				try
				{
					connection.close();
				} catch (SQLException e1)
				{
					LOGGER.log(Level.SEVERE, "JdbcHandler: ERROR closing Connection after INSERT + Return ID Query : " + sql, e1);
				}
			}
		}
		return id;
	}

	public void batchQuery(List<String> queries, int batchSize)
	{
		Connection connection = this.jdbcPoolHandler.getConnection(this.jdbcPoolHandler);
		Statement statement = null;
		try
		{
			statement = connection.createStatement();
			int count = 0;

			for (String sql : queries)
			{
				statement.addBatch(sql);

				if (++count % batchSize == 0)
				{
					statement.executeBatch();
				}
			}

			statement.executeBatch();

		} catch (SQLException e)
		{
			LOGGER.log(Level.SEVERE, "JdbcHandler: ERROR Executing batchQuery : " + queries, e);
		} finally
		{
			if (statement != null)
			{
				try
				{
					statement.close();
				} catch (SQLException e)
				{
					LOGGER.log(Level.SEVERE, "JdbcHandler: ERROR closing Statement after batchQuery : " + queries, e);
				}
			}
			if (connection != null)
			{
				try
				{
					connection.close();
				} catch (SQLException e1)
				{
					LOGGER.log(Level.SEVERE, "JdbcHandler: ERROR closing Connection after batchQuery : " + queries, e1);
				}
			}
		}
	}

	private List<HashMap<String, Object>> resultSetToArrayList(ResultSet resultSet) throws SQLException
	{
		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
		int columns = resultSetMetaData.getColumnCount();
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		while (resultSet.next())
		{
			HashMap<String, Object> row = new HashMap<String, Object>(columns);
			for (int i = 1; i <= columns; ++i)
			{
				try
				{
					row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
				} catch (SQLException sqlex)
				{
					if (sqlex.getMessage().contains("'0000-00-00 00:00:00'"))
					{
						row.put(resultSetMetaData.getColumnName(i), null);
					} else
					{
						LOGGER.log(Level.SEVERE, "JdbcHandler: ERROR put to row Map in resultSetToArrayList : ", sqlex);
					}
				} catch (Exception ex)
				{
					LOGGER.fine("JdbcHandler: ERROR Executing resultSetToArrayList -> md is NULL = " + (resultSetMetaData == null));
					LOGGER.fine("JdbcHandler: ERROR Executing resultSetToArrayList -> rs is NULL = " + (resultSet == null));
					LOGGER.fine("JdbcHandler: ERROR Executing resultSetToArrayList -> column number = " + columns);
					LOGGER.log(Level.SEVERE, "JdbcHandler: ERROR Executing resultSetToArrayList : ", ex);
				}
			}
			list.add(row);
		}
		return list;
	}

	private JSONArray resultSetToJsonArray(ResultSet resultSet) throws Exception
	{
		JSONArray jsonArray = new JSONArray();
		while (resultSet.next())
		{
			int total_rows = resultSet.getMetaData().getColumnCount();
			JSONObject jsonObject = new JSONObject();
			for (int i = 0; i < total_rows; i++)
			{
				String columnName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
				Object columnValue = resultSet.getObject(i + 1);
				// if value in DB is null, then we set it to default value
				if (columnValue == null)
				{
					columnValue = "null";
				}
				/*
				 * Next if block is a hack. In case when in db we have values
				 * like price and price1 there's a bug in jdbc - both this names
				 * are getting stored as price in ResulSet. Therefore when we
				 * store second column value, we overwrite original value of
				 * price. To avoid that, i simply add 1 to be consistent with
				 * DB.
				 */
				if (jsonObject.has(columnName))
				{
					columnName += "1";
				}
				jsonObject.put(columnName, columnValue);
			}
			jsonArray.put(jsonObject);
		}
		return jsonArray;
	}

	private void printResults(List<HashMap<String, Object>> results)
	{
		boolean headerPrinted = false;
		int size = results.size();
		if (size == 0)
		{
			LOGGER.log(Level.INFO, "JdbcHandler: printResults -> No results found.");
		}
		LOGGER.log(Level.INFO, "JdbcHandler: printResults . . .");
		for (int i = 0; i < size; i++)
		{
			Iterator result_itr = results.get(i).entrySet().iterator();
			while (result_itr.hasNext())
			{
				Entry entry = (Entry) result_itr.next();
				if (!headerPrinted)
				{
					LOGGER.log(Level.INFO, "| " + entry.getKey());
				} else
				{
					LOGGER.log(Level.INFO, "| " + entry.getValue() + "\t");
				}
			}
			LOGGER.log(Level.INFO, " |");
			if (!headerPrinted)
			{
				i = 0;
				headerPrinted = true;
			}
		}
	}

	/**
	 * used in translation for mass cache load
	 * @dmitry bulanuiy
	 * 
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public ResultSet queryForStreamResult(String sql) throws SQLException
	{
		if (!sql.isEmpty())
		{
			Connection connection = this.jdbcPoolHandler.getConnection(this.jdbcPoolHandler);
			Statement statement = null;
			List resultList = null;
			ResultSet resultSet = null;

			try
			{
				statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				statement.setFetchSize(Integer.MIN_VALUE);

				resultSet = statement.executeQuery(sql);
			} catch (SQLException e)
			{
				LOGGER.log(Level.SEVERE, "JdbcHandler: ERROR executing Query : " + sql, e);
				throw new SQLException();
			} finally
			{
				/*
				 * 		TODO
				 * 		!!!!
				 * this result should not be closed here, because it will be used in calling class...
				 * 
				if (resultSet != null)
				{
					resultSet.close();
				}
				if (statement != null)
				{
					statement.close();
				}
				if (connection != null)
				{
					connection.close();
				}
				 */
			}
			return resultSet;
		} else
		{
			return null;
		}
	}

}