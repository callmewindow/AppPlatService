package buaa.edu.global;

/**
 * Created by winter on 2014/6/10.
 */
public class AppPlatConstant {
    public static final String NODETYPE_TASK = "Task";
    public static final String NODETYPE_SOLUTION = "Solution";
    public static final String NODETYPE_SATELLITE = "Satellite";
    public static final String NODETYPE_GROUNDSTATION = "GroundStation";
    public static final String NODETYPE_SENSOR = "Sensor";
    public static final String NODETYPE_ATTITUDEDATA = "Attitudedata";
    public static final String NODETYPE_ORBITDATA = "Orbitdata";
    public static final String NODETYPE_STRUCTURE = "Structure";
    public static final String NODETYPE_COVER_ANALYSIS = "CoverAnalysis";
    public static final String NODETYPE_DATATRANS_ANALYSIS = "DataTransAnalysis";
    public static final String NODETYPE_CHAIN_ANALYSIS = "ChainAnalysis";
    public static final String NODETYPE_OVERSTATION_ANALYSIS = "OverStationAnalysis";
    public static final String NODETYPE_PLANET_COVER_POINT = "PlanetCoverPoint";
    public static final String NODETYPE_PLANET_COVER_SQUARE = "PlanetCoverSquare";
    public static final String NODETYPE_PLANET_COVER_GLOBAL = "PlanetCoverGlobal";
    public static final String NODETYPE_SKY_COVER_POINT = "SkyCoverPoint";
    public static final String NODETYPE_SKY_COVER_REGIN = "SkyCoverRegin";
    public static final String NODETYPE_ANTENNA = "Antenna";



    public static final String TASK_PRE_ID = "task1234";
    public static final String SOLUTION_PRE_ID = "solution";
    public static final String SATELLITE_PRE_ID = "satellit";
    public static final String GROUNDSTATION_PRE_ID = "groundst";
    public static final String SENSOR_PRE_ID = "sensor12";
    public static final String ATTITUDEDATA_PRE_ID= "attitude";
    public static final String ORBITDATA_PRE_ID = "orbitdat";
    public static final String STRUCTURE_PRE_ID = "structur";
    public static final String COVER_ANALYSIS_PRE_ID = "coverana";
    public static final String DATATRANS_ANALYSIS_PRE_ID = "datatran";
    public static final String CHAIN_ANALYSIS_PRE_ID = "chainana";
    public static final String OVERSTATION_ANALYSIS_PRE_ID = "overstat";
    public static final String PLANET_COVER_POINT_PRE_ID = "planetpt";
    public static final String PLANET_COVER_SQUARE_PRE_ID = "planetsq";
    public static final String PLANET_COVER_GLOBAL_PRE_ID = "planetgl";
    public static final String SKY_COVER_POINT_PRE_ID = "skypoint";
    public static final String SKY_COVER_REGIN_PRE_ID = "skyregin";
    public static final String ANTENNA_PRE_ID = "antenna1";

    public static final String PROCESS_ORBITDESIGN = "轨道设计";
    public static final String PROCESS_STRUCTDESIGN = "结构设计";
    public static final String PROCESS_COVERANALYSIS = "覆盖分析";
    public static final String PROCESS_DATATRANSANALYSIS = "数传分析";

    public static final byte PROCESS_NOT_SUBMIT = 0;
    public static final byte PROCESS_SUBMIT = 1;
    public static final byte IS_NOT_CREATER = 0;
    public static final byte IS_CREATER = 1;
    public static final String TEMPORARY_USER_ID = "XXXXXX"; //临时的用户ID

    public static final String PROPERTY_FILE_SUFFIX = "property";

    public static final String SECTION_TASK = "task";//
    public static final String SECTION_SOLUTION = "solution";
    public static final String SECTION_SATELLITE = "satellite";
    public static final String SECTION_GROUNDSTATION = "groundstation";
    public static final String SECTION_SENSOR = "sensor";
    public static final String SECTION_ATTITUDEDATA = "attitudedata";
    public static final String SECTION_ORBITDATA = "orbitdata";
    public static final String SECTION_STRUCTURE = "structure";
    public static final String SECTION_COVER_ANALYSIS = "coveranalysis";
    public static final String SECTION_DATATRANS_ANALYSIS = "datatransanalysis";
    public static final String SECTION_CHAIN_ANALYSIS = "chainanglysis";
    public static final String SECTION_OVERSTATION_ANALYSIS = "overstationanalysis";
    public static final String SECTION_VIEWFIELD = "viewfield";//视场
    public static final String SECTION_VIEWFIELDPOINT = "viewfieldpoint";//视场
    public static final String CLOUD_PLATFORM_HOST = "http://159.226.22.205";
    public static final String STRUCT_FILE_NAME = "StructDesign";
    public static final String OBJ_FILE_SUFFIX = ".obj";
    public static final String PICTURE_FILE_SUFFIX = ".map";
    public static final String JSON_FILE_SUFFIX = ".json";
    public static final String MTL_FILE_SUFFIX = ".mtl";
    public static final String CLOUD_STORAGE_USER_ID  = "532f98a9e1627816ac735d1f";
    public static final int CLOUD_STORAGE_TIME_INTERVAL  = 5000; //向云存储同步数据的时间间隔
    public static final int LOCAL_STORAGE_TIME_INTERVAL  = 5000; //向云存储同步数据的时间间隔

    public static final String PER_TASK_TYPE_ORBIT_DESIGN  = "轨道设计"; //轨道设计
    public static final String PER_TASK_TYPE_STRUCTURE_DESIGN  = "结构设计"; //结构设计
    public static final String PER_TASK_TYPE_COVER_ANALYSIS  = "覆盖分析"; //覆盖分析
    public static final String PER_TASK_TYPE_DATATRANS_ANALYSIS  = "数传分析"; //数传分析
    public static final String TASK_TYPE_COLLABORATIVE_ARGUMENT  = "协同论证"; //数传分析
    public static final byte IS_PER_TASK_SAVED_NOT  = 0; //单项任务未保存
    public static final byte IS_PER_TASK_SAVED  = 1; //单项任务未保存

    public static final String CSS_LIGHT_OPEN  = "lightopen"; //单项任务未保存
    public static final String CSS_LIGHT_OFF  = "lightoff"; //单项任务未保存
    public static final String SAVE_SCENE_DATA_BY_TIMER_URL = "http://localhost:8080/AppPlatService/services/CollaDesignManage/saveSceneDataByTimer";
    public static final String REFRESH_SCENE_DATA_URL = "http://localhost:8080/AppPlatService/services/CollaDesignManage/refreshSceneData";

    public static final String MODEL_ID_COLLABORATIVE_ARGUMENT  = "collaborativeargument";
    public static final String MODEL_ID_ORBIT_DESIGN = "orbitdesign";
    public static final String MODEL_ID_STRUCT_DESIGN = "structdesign";
    public static final String MODEL_ID_COVER_ANALYSIS = "coveranalysis";
    public static final String MODEL_ID_DATA_TRANS_ANALYSIS = "datatransanalysis";
    public static final String TOOL_TYPE_STRUCT_DESIGN = "StructDesign";
    public static final String TOOL_TYPE_ORBIT_DESIGN = "OrbitDesign";

    public static final String STRUCT_DESIGN = "StructDesign";
    public static final String ORBIT_DESIGN = "OrbitDesign";
    public static final String COVER_ANALYSIS = "CoverAnalysis";
    public static final String DATA_TRANS_ANALYSIS = "DataTransAnalysis";

}
