package buaa.edu.global;

/**
 * Created by winter on 2015/3/19.
 */
public class AppPlatFunction {
    //判断字符串是否为null或者空串
    public static  Boolean isNullOrEmpty(String str){
        if(str == null){
            return true;
        }
        else if(str.isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }
}
