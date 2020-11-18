package buaa.edu.sortEntity;

import entity.databaseEntity.Node;
import java.util.Comparator;
/**
 * Created by winter on 2014/6/11.
 */
public class CompSolution implements Comparator<Node> {

    public int compare(Node o1, Node o2){
        int index = 0;
        index = o1.getNodeType().compareTo(o2.getNodeType());
        if (index == 0) {
            index = o1.getName().compareTo(o2.getName());
        }
        if (index != 0) {
            index = o1.getNodeOrder().compareTo(o2.getNodeOrder());
        }
           return index;
    }
}
