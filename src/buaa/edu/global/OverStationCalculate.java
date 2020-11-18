package buaa.edu.global;

/**
 * Created by Administrator on 14-11-20.
 */
public class OverStationCalculate {
    private static native String load_dll();
    private static native String free_dll();
    private static native String calculate(String jsonstr);
    private static native String calculate_path(String jsonstr, String filepath);
    private static native String get_path();
    static{
        System.loadLibrary("CoversAnaysisDLLP"); //c++实现的中间件"DllLoadService.dll"
    }

    public synchronized String LoadDLL(){
        return load_dll();
    }

    public synchronized String FreeDLL(){
        return free_dll();
    }

    public synchronized String GetPath(){
        return get_path();
    }

    public synchronized String CalculateCover(String jsonstr){
        return calculate(jsonstr);
    }

    public synchronized String CalculateCoverPath(String jsonstr, String filepath){
        return calculate_path(jsonstr, filepath);
    }
}
