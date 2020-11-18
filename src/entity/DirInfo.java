package entity;

public class DirInfo {
	private String createTime;    //创建时间
	private String filePath;      //存储路径
	private int isDirectory;      //1表示路径，2表示文件
	private String objectName;    //对象名称
	private double size;             //文件对象大小，单位MB，当对象为目录时值为0
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public int getIsDirectory() {
		return isDirectory;
	}
	public void setIsDirectory(int isDirectory) {
		this.isDirectory = isDirectory;
	}
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
}
