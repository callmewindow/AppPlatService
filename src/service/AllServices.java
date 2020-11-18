package service;

import serviceInterface.IAccessStatisic;
import serviceInterface.IDigitalSignatures;
import serviceInterface.ILocalStoreOperate;

/**
 * Created by yezhang989@163.com on 14-5-30.
 */
public class AllServices {

    private SolutionService solutionService;
    private TaskService taskService;
    private StructFileService structFileService;
    private ILocalStoreOperate iLocalStoreService;
    private IDigitalSignatures iDigitalSignatureService;
    private IAccessStatisic iAccessStatisic;
    private ServerStoreService serverStoreService;
    private EnCloudStoreService enCloudStoreService;
    private OrbitFileService orbitFileService;
    private PerTaskCommonService perTaskCommonService;
    private CoverAnalysisService coverAnalysisService;
    private DataTransAnalysisService dataTransAnalysisService;
    private CollaDesignService collaDesignService;

    public SolutionService getSolutionService() {
        return solutionService;
    }

    public void setSolutionService(SolutionService solutionService) {
        this.solutionService = solutionService;
    }

    public TaskService getTaskService() {
        return taskService;
    }

    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }


    public StructFileService getStructFileService() {
        return structFileService;
    }

    public void setStructFileService(StructFileService structFileService) {
        this.structFileService = structFileService;
    }

    public ILocalStoreOperate getiLocalStoreService() {
        return iLocalStoreService;
    }

    public void setiLocalStoreService(ILocalStoreOperate iLocalStoreService) {
        this.iLocalStoreService = iLocalStoreService;
    }

    public CollaDesignService getCollaDesignService() {
        return collaDesignService;
    }

    public void setCollaDesignService(CollaDesignService collaDesignService) {
        this.collaDesignService = collaDesignService;
    }

    public IDigitalSignatures getiDigitalSignatureService() {
        return iDigitalSignatureService;
    }

    public void setiDigitalSignatureService(IDigitalSignatures iDigitalSignatureService) {
        this.iDigitalSignatureService = iDigitalSignatureService;
    }

    public IAccessStatisic getiAccessStatisic() {
        return iAccessStatisic;
    }

    public void setiAccessStatisic(IAccessStatisic iAccessStatisic) {
        this.iAccessStatisic = iAccessStatisic;
    }

    public ServerStoreService getServerStoreService() {
        return serverStoreService;
    }

    public void setServerStoreService(ServerStoreService serverStoreService) {
        this.serverStoreService = serverStoreService;
    }

    public EnCloudStoreService getEnCloudStoreService() {
        return enCloudStoreService;
    }

    public void setEnCloudStoreService(EnCloudStoreService enCloudStoreService) {
        this.enCloudStoreService = enCloudStoreService;
    }

    public OrbitFileService getOrbitFileService() {
        return orbitFileService;
    }

    public void setOrbitFileService(OrbitFileService orbitFileService) {
        this.orbitFileService = orbitFileService;
    }

    public CoverAnalysisService getCoverAnalysisService() {
        return coverAnalysisService;
    }

    public void setCoverAnalysisService(CoverAnalysisService coverAnalysisService) {
        this.coverAnalysisService = coverAnalysisService;
    }

    public PerTaskCommonService getPerTaskCommonService() {
        return perTaskCommonService;
    }

    public void setPerTaskCommonService(PerTaskCommonService perTaskCommonService) {
        this.perTaskCommonService = perTaskCommonService;
    }

    public DataTransAnalysisService getDataTransAnalysisService() {
        return dataTransAnalysisService;
    }

    public void setDataTransAnalysisService(DataTransAnalysisService dataTransAnalysisService) {
        this.dataTransAnalysisService = dataTransAnalysisService;
    }
}
