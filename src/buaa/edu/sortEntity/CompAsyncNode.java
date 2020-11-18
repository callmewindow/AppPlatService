package buaa.edu.sortEntity;

import buaa.edu.global.AppPlatConstant;
import entity.databaseEntity.Node;

import java.util.Comparator;

/**
 * Created by winter on 2014/6/11.
 */
public class CompAsyncNode implements Comparator<Node> {

    public int compare(Node o1, Node o2){
        if(o1.getNodeType().equals(o2.getNodeType())){
            if(o1.getName().length()!= o2.getName().length()){
                return o1.getName().length() - o2.getName().length();
            }else{
                return o1.getName().compareTo(o2.getName());
            }
        }else{
            return getRank(o1)-getRank(o2);
        }
    }
    public int getRank(Node node){
        String type = node.getNodeType();
        if(type.equals(AppPlatConstant.NODETYPE_SATELLITE)){//卫星
            return 1;
        }
        if(type.equals(AppPlatConstant.NODETYPE_GROUNDSTATION)){//地面站
            return 2;
        }
        if(type.equals(AppPlatConstant.NODETYPE_SENSOR)){//传感器
            return 3;
        }
        if(type.equals(AppPlatConstant.NODETYPE_PLANET_COVER_POINT)){//地面点覆盖
            return 4;
        }
        if(type.equals(AppPlatConstant.NODETYPE_PLANET_COVER_SQUARE)){//地面区域覆盖
            return 5;
        }
        if(type.equals(AppPlatConstant.NODETYPE_PLANET_COVER_GLOBAL)){//全球覆盖
            return 6;
        }
        if(type.equals(AppPlatConstant.NODETYPE_COVER_ANALYSIS)){//覆盖分析
            return 7;
        }
        if(type.equals(AppPlatConstant.NODETYPE_DATATRANS_ANALYSIS)){//数据传输分析
            return 8;
        }
        if(type.equals(AppPlatConstant.NODETYPE_CHAIN_ANALYSIS)){//链路分析
            return 8;
        }
        return 0;
    }
}
