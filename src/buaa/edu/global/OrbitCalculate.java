package buaa.edu.global;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Calendar;
/**
 * Created by Administrator on 14-9-1.
 */
public class OrbitCalculate {
    private static OrbitCalculate instance = new OrbitCalculate();
    private static boolean isInited = false;

    private Calendar mdateCurrentTime;
    private String mPLEPHPath;

    public String getmPLEPHPath() {
        return mPLEPHPath;
    }
    public OrbitCalculate(){
        URL[] urls= sun.misc.Launcher.getBootstrapClassPath().getURLs();
        for (int i = 0; i < urls.length; i++) {
            System.out.println(urls[i]);
        }

        String path = null;
        try {
            path = this.getClass().getClassLoader().getResource("/").toURI().getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        path = path.substring(1);
        mPLEPHPath = new String(path + "../resources/data/ZBZHEphemerisData");
    }

    public Calendar getMdateCurrentTime() {
        return mdateCurrentTime;
    }

    public void setMdateCurrentTime(Calendar mdateCurrentTime) {
        this.mdateCurrentTime = mdateCurrentTime;
    }

    private static native boolean LoadLibrary();
    private static native void CorTrans(int y, int m, int d, int h, int mi, double sec,
                                       int a, int aT, int b, int bT, double[] p_mat,
                                       double[] p_dis, String s_path, int length_arg);
    private static native void Free();
    static{

        try {
            System.loadLibrary("OrbitCalculate"); //c++实现的中间件"DllLoadService.dll"
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static OrbitCalculate getInstance()
    {
        if (instance != null)
        {
            return instance;
        }
        return null;
    }

    public synchronized void ini()
    {
        if (isInited)
        {
            return;
        }
        //init
        if(LoadLibrary() == true){
            isInited = true;
        }
        else{
            isInited = false;
        }

    }

    //a,b:表示坐标系,其中1代表J2000惯性系,2代表固连系,3代表平赤道平春分点J2000,4代表平黄道J2000
    //    只有地球有5-mag, 6-gsm, 7-gse, 8-sm
    //    310 地月会合坐标系    113 日地会合坐标系
    //    注意，3101和3102中有J2000和310坐标系
    //	        1131和1132中有J2000和113坐标系
    //aT,bT:表示行星对象,其中1至9代表九大行星,10代表月球,11代表太阳,
    //      3101-地月系L1点  3102-地月系L2点  1131-日地系L1点 1132-日地系L2点
    public synchronized void computePlanet(double[] position, double[] matrixFixed, double[] matrixJ2000, int planetId, int centerPlanetid){
        //从太阳黄道坐标系转化到行星的某个赤道平面
        //先算固定 在算惯性 因为惯性的位置必须返回

        try {
            CorTrans(mdateCurrentTime.get(Calendar.YEAR), mdateCurrentTime.get(Calendar.MONTH),
                    mdateCurrentTime.get(Calendar.DATE), mdateCurrentTime.get(Calendar.HOUR_OF_DAY),
                    mdateCurrentTime.get(Calendar.MINUTE),(double)mdateCurrentTime.get(Calendar.SECOND),
                    1, planetId, 2, planetId, matrixFixed, position, mPLEPHPath, mPLEPHPath.length());

            CorTrans(mdateCurrentTime.get(Calendar.YEAR), mdateCurrentTime.get(Calendar.MONTH),
                    mdateCurrentTime.get(Calendar.DATE), mdateCurrentTime.get(Calendar.HOUR_OF_DAY),
                    mdateCurrentTime.get(Calendar.MINUTE),(double)mdateCurrentTime.get(Calendar.SECOND),
                    4, centerPlanetid, 1, planetId, matrixJ2000, position, mPLEPHPath, mPLEPHPath.length());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public synchronized void computeEarthSpecial(double[] position,
                                                 double[] matrixMagnetic,
                                                 double[] matrixGSM,
                                                 double[] matrixGSE,
                                                 double[] matrixSM,
                                                 int planetId,
                                                 int centerPlanetid){
        //从太阳黄道坐标系转化到行星的某个赤道平面
//        CorTrans(mdateCurrentTime.get(Calendar.YEAR), mdateCurrentTime.get(Calendar.MONTH),
//                mdateCurrentTime.get(Calendar.DATE), mdateCurrentTime.get(Calendar.HOUR_OF_DAY),
//                mdateCurrentTime.get(Calendar.MINUTE),(double)mdateCurrentTime.get(Calendar.SECOND),
//                1, 3, 5, planetId, matrixMagnetic, position, mPLEPHPath, mPLEPHPath.length());

        try {
            CorTrans(mdateCurrentTime.get(Calendar.YEAR), mdateCurrentTime.get(Calendar.MONTH),
                    mdateCurrentTime.get(Calendar.DATE), mdateCurrentTime.get(Calendar.HOUR_OF_DAY),
                    mdateCurrentTime.get(Calendar.MINUTE),(double)mdateCurrentTime.get(Calendar.SECOND),
                    2, 3, 6, planetId, matrixGSM, position, mPLEPHPath, mPLEPHPath.length());
        } catch (Exception e) {
            e.printStackTrace();
        }

//        CorTrans(mdateCurrentTime.get(Calendar.YEAR), mdateCurrentTime.get(Calendar.MONTH),
//                mdateCurrentTime.get(Calendar.DATE), mdateCurrentTime.get(Calendar.HOUR_OF_DAY),
//                mdateCurrentTime.get(Calendar.MINUTE),(double)mdateCurrentTime.get(Calendar.SECOND),
//                1, 3, 7, planetId, matrixGSE, position, mPLEPHPath, mPLEPHPath.length());

//        CorTrans(mdateCurrentTime.get(Calendar.YEAR), mdateCurrentTime.get(Calendar.MONTH),
//                mdateCurrentTime.get(Calendar.DATE), mdateCurrentTime.get(Calendar.HOUR_OF_DAY),
//                mdateCurrentTime.get(Calendar.MINUTE),(double)mdateCurrentTime.get(Calendar.SECOND),
//                1, 3, 8, planetId, matrixSM, position, mPLEPHPath, mPLEPHPath.length());
    }
    //计算固定惯性坐标系 相对于地球惯性
    public synchronized void computeMoon(double[] position,double[] matrixFixed,double[] matrixJ2000,int planetId,int centerPlanetid)//默认状态下为相对太阳的位置
    {
        //从太阳黄道坐标系转化到行星的某个赤道平面
        try {
            CorTrans(mdateCurrentTime.get(Calendar.YEAR), mdateCurrentTime.get(Calendar.MONTH),
                    mdateCurrentTime.get(Calendar.DATE), mdateCurrentTime.get(Calendar.HOUR_OF_DAY),
                    mdateCurrentTime.get(Calendar.MINUTE),(double)mdateCurrentTime.get(Calendar.SECOND),
                    1, planetId, 2, planetId, matrixFixed, position, mPLEPHPath, mPLEPHPath.length());

            CorTrans(mdateCurrentTime.get(Calendar.YEAR), mdateCurrentTime.get(Calendar.MONTH),
                    mdateCurrentTime.get(Calendar.DATE), mdateCurrentTime.get(Calendar.HOUR_OF_DAY),
                    mdateCurrentTime.get(Calendar.MINUTE),(double)mdateCurrentTime.get(Calendar.SECOND),
                    1, centerPlanetid, 1, planetId, matrixJ2000, position, mPLEPHPath, mPLEPHPath.length());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public synchronized void computeLagrange(double[] position,
                                             double[] matrixFixed, double[] matrixJ2000, int planetId, int planetAttachCoord, int centerPlanetid ){
        //从太阳黄道坐标系转化到行星的某个赤道平面
        //先算固定 再算惯性 因为惯性的位置必须返回
        CorTrans(mdateCurrentTime.get(Calendar.YEAR), mdateCurrentTime.get(Calendar.MONTH),
                mdateCurrentTime.get(Calendar.DATE), mdateCurrentTime.get(Calendar.HOUR_OF_DAY),
                mdateCurrentTime.get(Calendar.MINUTE),(double)mdateCurrentTime.get(Calendar.SECOND),
                1, planetId, 2, planetId, matrixFixed, position, mPLEPHPath, mPLEPHPath.length());

        CorTrans(mdateCurrentTime.get(Calendar.YEAR), mdateCurrentTime.get(Calendar.MONTH),
                mdateCurrentTime.get(Calendar.DATE), mdateCurrentTime.get(Calendar.HOUR_OF_DAY),
                mdateCurrentTime.get(Calendar.MINUTE),(double)mdateCurrentTime.get(Calendar.SECOND),
                4, centerPlanetid, planetAttachCoord, planetId, matrixJ2000, position, mPLEPHPath, mPLEPHPath.length());
    }
    //计算特别的坐标系
    public synchronized void computeTest(double[] position,
                                         double[] matrixTest,
                                         int planetId,
                                         int centerPlanetid){
        CorTrans(mdateCurrentTime.get(Calendar.YEAR), mdateCurrentTime.get(Calendar.MONTH),
            mdateCurrentTime.get(Calendar.DATE), mdateCurrentTime.get(Calendar.HOUR_OF_DAY),
            mdateCurrentTime.get(Calendar.MINUTE),(double)mdateCurrentTime.get(Calendar.SECOND),
            2, 3, 6, 3, matrixTest, position, mPLEPHPath, mPLEPHPath.length());
    }
    public synchronized void CorTransCalc(int y, int m, int d, int h, int mi, double sec,
                              int a, int aT, int b, int bT, double[] p_mat,
                              double[] p_dis, String s_path, int length_arg)
    {
        CorTrans(y, m, d, h, mi, sec, a, aT, b, bT, p_mat, p_dis, s_path, length_arg);
    }
    public synchronized boolean derivative(int y, int m, int d, int h, int mi, double sec,
                              int bT, double last){
        boolean upDown = false;

        double[] p_mat = new double[9], p_dis = new double[3];
        String s_path = new String("E:\\ZBZHEphemerisData");
        double dx = 0.0001;
        CorTrans(y, m, d, h, mi, sec + dx, 1, bT, 2, bT, p_mat, p_dis, s_path, s_path.length());
        double next = p_mat[0];
        if(next > last)
            upDown = true;
        return upDown;
    }
    public synchronized void free()
    {
        if (isInited == false)
        {
            return;
        }

        //free
        Free();
        isInited = false;
    }
}


