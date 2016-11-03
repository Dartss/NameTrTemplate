package common.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;

/**
 * NeoHelper based on Bolt protocol that appears in Neo4j 3.0 version
 * 
 * @author Vit
 *
 */

public class NeoHelper {
	private Driver driver;
	private Session session;

	private String host;
	private String port;
	private String user;
	private String password;

	public NeoHelper(String host, String user, String password) {
		this.init(host, null, user, password);
	}

	public NeoHelper(String host, String port, String user, String password) {
		this.init(host, port, user, password);
	}

	public void init(String host, String port, String user, String password) {
		this.host = host;
		this.user = user;
		this.password = password;
		this.port = port;
		if (null == this.port || "".equals(this.port))
			this.port = "";
		else
			this.host += ":" + this.port;
		//
		connect();
	}

	public void connect() {
		if (this.driver == null) {
			this.driver = GraphDatabase.driver("bolt://" + host, AuthTokens.basic(user, password));
		}

		if (this.session == null || !this.session.isOpen()) {
			this.session = this.driver.session();
		}
	}

	public void destroy() {
		if (this.driver != null) {
			this.driver.close();
		}

		if (this.session != null && this.session.isOpen()) {
			this.session.close();
		}
	}

	public void close() {
		if (this.session != null && this.session.isOpen()) {
			this.session.close();
		}
	}

	private void prepareConnection() {
		if (this.session == null || !this.session.isOpen()) {
			connect();
		}
	}

	public void executeBatch(List<String> cypher) {
		prepareConnection();

		StatementResult statementResult;

		Transaction trx = this.session.beginTransaction();

		for (int i = 0; i < cypher.size(); i++) {
			statementResult = trx.run(cypher.get(i));
		}
		trx.success();
	}

	public StatementResult query(String cypher, Map<String, Object> parameters) {

		prepareConnection();

		StatementResult statementResult;

		if (null != parameters && parameters.size() > 0)
			statementResult = this.session.run(cypher, parameters);
		else
			statementResult = this.session.run(cypher);

		return statementResult;
	}

	public StatementResult query(String cypher) {
		return this.query(cypher, null);
	}

	/**
	 * This method return a list of uuids according to input query Notice: query
	 * must return NODE
	 * 
	 * @param query
	 * @return
	 */
	public List<String> queryUuids(String query) {
		List<String> uuids = new ArrayList<>();

		StatementResult result = this.query(query);

		while (result.hasNext()) {
			Record rec = result.next();

			String uuid = rec.get(0).get("uuid").asString();
			if (uuid != null && !uuid.isEmpty()) {
				uuids.add(uuid);
			}
		}

		return uuids;
	}

	public boolean delete(String query) {
		StatementResult result = this.query(query);
		// TODO: handle properly
		if (result.consume().counters().nodesDeleted() > 0 || result.consume().counters().relationshipsDeleted() > 0) {
			return true;
		} else {
			return false;
		}
	}
}