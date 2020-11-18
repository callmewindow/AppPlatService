package buaa.edu.global;

import java.util.UUID;

/**
 * Created by winter on 2014/6/28.
 */
public class IDGeneratorUtil {
    public static String getUUID(String NodeType8){
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return NodeType8+uuid.substring(0,23);
    }

}
