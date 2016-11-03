package common.graph;

import java.util.Date;

import jsmarty.core.common.graph.notation.NodeType;

/**
 * All queries were moved from old NeoHandler to this class
 * 
 * this class is responsible only for query building
 * 
 * This is temporary class, just for fixing errors caused by upgrading
 * dependency!
 * 
 * @author Vit
 *
 */

public final class NeoQueryBuilder {

	public static String setConstraint(String subjectNode, String subjectField, String assertion) {
		return "CREATE CONSTRAINT ON (" + subjectNode + ") ASSERT " + subjectField + " IS UNIQUE";
	}

	public static String persistPost(String uuid, String body, Date createdAt, String origin, String originalLanguage) {
		return "MERGE (n:post {content: '" + body.replace("'", " ") + "', uuid:'" + uuid + "', createdAt:"
				+ createdAt.getTime() + ", type:'" + NodeType.POST + "', origin:'" + origin + "', language:'" + originalLanguage + "'})";
	}

	public static String persistRelation(String partA, String partB, String relationType) {
		return "MATCH (a:" + partA + "), (b:" + partB + ") MERGE (a)-[:" + relationType + "]-(b)";
	}

	public static String persistEntity(String nodeName) {
		return "MERGE (n:entity {entity_name:'" + nodeName.replace("'", " ") + "', type:'" + NodeType.ENTITY + "'})";
	}
	public static String persistType(String nodeName) {
		return "MERGE (n:type {type_name:'" + nodeName.replace("'", " ") + "', type:'" + NodeType.TYPE + "'})";
	}
	public static String persistSubtype(String nodeName) {
		return "MERGE (n:subtype {subtype_name:'" + nodeName.replace("'", " ") + "', type:'" + NodeType.SUBTYPE + "'})";
	}

	public static String persistKeyword(String nodeName) {
		return "MERGE (n:keyword {keyword_name:'" + nodeName.replace("'", " ") + "', type:'" + NodeType.KEYWORD
				+ "'})";
	}

	public static String persistLocation(String nodeName) {
		return "MERGE (n:location {location_name:'" + nodeName.replace("'", " ") + "', type:'" + NodeType.LOCATION
				+ "'})";
	}

	public static String persistAction(String nodeName) {
		return "MERGE (n:action {action_name:'" + nodeName.replace("'", " ") + "', type:'" + NodeType.ACTION
				+ "'}) RETURN n";
	}

	public static String persistCategories(String nodeName) {
		return "MERGE (n:category {category_name:'" + nodeName.replace("'", " ") + "', type:'" + NodeType.CATEGORY
				+ "'}) RETURN n";
	}

	public static String persistSubCategories(String nodeName) {
		return "MERGE (n:sub_category {sub_category_name:'" + nodeName.replace("'", " ") + "', type:'" + NodeType.SUB_CATEGORY
				+ "'}) RETURN n";
	}
}
