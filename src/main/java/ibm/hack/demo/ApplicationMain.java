package ibm.hack.demo;

import com.ibm.watson.developer_cloud.discovery.v1.model.AggregationResult;
import com.ibm.watson.developer_cloud.discovery.v1.model.QueryResponse;
import com.ibm.watson.developer_cloud.discovery.v1.model.QueryResult;

import java.util.List;

/**
 * Created by nikzz on 08/05/18.
 */
public class ApplicationMain {
    public static void main(String[] args) throws Exception {
        DiscoveryQuery discoveryQuery = new DiscoveryQuery();

        QueryResponse r1 = discoveryQuery.queryNewsRaltedToADomain("Information And Technology");
        List<QueryResult> results = r1.getResults();
        for(QueryResult r : results){
            System.out.print(r.get("title"));
            System.out.print(", ");
            System.out.println();
        }
        /*
        QueryResponse r1 = discoveryQuery.getTopNCompaniesInDomain("Information and " +
                "Technology", 10);

        List<AggregationResult> resultList = r1.getAggregations().get(0).getAggregations().get(0).getAggregations().get(0).getResults();
        for(AggregationResult r : resultList){
            System.out.println(r.getKey()+" -> "+r.getMatchingResults());
        }

        System.out.println(r1.getResults().get(0));
        */



        /*QueryResponse response =  discoveryQuery.query("Morgan Stanley");

        List<QueryResult> result = response.getResults();

        for(QueryResult r : result){
            System.out.print("title is : "+r.get("title"));
            //System.out.print(" exriched text is : "+r.get("enriched_text"));
            //System.out.print(" exriched title is : "+r.get("enriched_title"));
            System.out.print(" text : "+r.get("text"));
            System.out.print(", Score is : "+r.getScore());
            System.out.print(", and key set is : "+r.keySet());
            System.out.println();
        }*/
        //System.out.println(response.getResults());
    }
}
