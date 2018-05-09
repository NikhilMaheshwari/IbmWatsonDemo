package ibm.hack.demo;

import com.ibm.watson.developer_cloud.discovery.v1.Discovery;
import com.ibm.watson.developer_cloud.discovery.v1.model.ListEnvironmentsOptions;
import com.ibm.watson.developer_cloud.discovery.v1.model.ListEnvironmentsResponse;
import com.ibm.watson.developer_cloud.discovery.v1.model.QueryOptions;
import com.ibm.watson.developer_cloud.discovery.v1.model.QueryResponse;

import java.util.StringTokenizer;

/**
 * Created by nikzz on 08/05/18.
 */
public class DiscoveryQuery {

    private String collectionId;

    private Discovery discovery;

    private String environmentId;

    private String password;

    private String userName;

    private String queryFields = "none";

    public DiscoveryQuery() {
        userName = "9b5f1cc6-73ce-4c4b-aae9-9dc21e25d1f9";
        password = "j7VoysshS6i4";
        collectionId = "news-en";
        environmentId = "system";
        queryFields = "enriched_text.entities.text";

        discovery = new Discovery(Constants.DISCOVERY_VERSION);
        discovery.setEndPoint(Constants.DISCOVERY_URL);
        discovery.setUsernameAndPassword(userName, password);

    }

    public QueryResponse queryNewsRaltedToADomain(String domainName){
        String query = "enriched_text.concepts.text:"+domainName;
        String filter = "nested(enriched_text.entities).filter(enriched_text.entities.sentiment.score>=0.8).term" +
                "(enriched_text.entities.text)";
        QueryOptions queryOptions = new QueryOptions.Builder(environmentId, collectionId)
                .filter(query)
                .aggregation(filter)
                .build();

        return discovery.query(queryOptions).execute();
    }

    public QueryResponse query(String userQuery) throws Exception {

        StringBuilder sb = new StringBuilder();

        if (queryFields == null || queryFields.length() == 0 || queryFields.equalsIgnoreCase("none")) {
            sb.append(userQuery);
        } else {
            StringTokenizer st = new StringTokenizer(queryFields, ",");
            while (st.hasMoreTokens()) {
                sb.append(st.nextToken().trim());
                sb.append(":");
                sb.append(userQuery);
                if (st.hasMoreTokens()) {
                    sb.append(",");
                }
            }
        }

        QueryOptions queryOptions = new QueryOptions.Builder(environmentId, collectionId).filter("enriched_title.semantic_roles:(action.normalized:acquire,object.entities:(type::Company))").query(sb.toString())
                .build();

        return discovery.query(queryOptions).execute();
    }

    public QueryResponse getTopNCompaniesInDomain(String domain, int resultCount) throws
            Exception{
        String query = "enriched_text.categories.label::/"+domain;
        String aggregations = "nested(enriched_text.entities).filter(enriched_text.entities.type::Company).term" +
                "(enriched_text.entities.text,count:"+resultCount+")";
        QueryOptions queryOptions = new QueryOptions.Builder(environmentId, collectionId)
                .filter(domain)
                .aggregation(aggregations)
                .build();

        return discovery.query(queryOptions).execute();
    }
}
