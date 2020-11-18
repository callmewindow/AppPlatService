package buaa.edu.global;

/**
 * Created by dell on 15-1-23.
 */
public class OrbitTransfer {
    private static OrbitTransfer instance = new OrbitTransfer(1, 1.327e11, 0);
    private static boolean isInited = false;

    private long DM;//固定取1
    private double U;//太阳的引力常数
    private long CIRCLE;//固定取0
    //构造函数
    OrbitTransfer(long dm, double u, long circle){
        DM = dm;
        U = u;
        CIRCLE = circle;
    }
    //加载计算转移轨道DLL
    private static native boolean loadDll();
    //计算转移轨道
    private static native void lambert(double [] R1, double [] R2, double FTIME, long DM, double U,
                                       long CIRCLE, double [] V1, double [] V2, long [] INFO);
    //释放计算转移轨道DLL
    private static native boolean freeDll();

    static{
        try {
            System.loadLibrary("OrbitTransfer");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取实例
    public static OrbitTransfer getInstance(){
        if(instance != null){
            return instance;
        }
        else{
            return null;
        }
    }
    //加载计算转移轨道DLL
    public synchronized void init(){
        if(isInited){
            return;
        }
        else{
            if(loadDll()){
                isInited = true;
            }
            else{
                isInited = false;
            }
        }
    }
    //计算转移轨道
    public synchronized void OrbitLambert(double [] R1, double [] R2, double FTIME, double [] V1, double [] V2, long [] INFO){
        lambert(R1, R2, FTIME, DM, U, CIRCLE, V1, V2, INFO);
    }
    //释放计算转移轨道DLL
    public synchronized void free(){
        if(isInited == false){
            return;
        }
        else{
            if(freeDll()){
                isInited = false;
            }
            else{
                isInited = true;
            }
        }
    }
}
