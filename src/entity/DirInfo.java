package entity;

public class DirInfo {
	private String createTime;    //����ʱ��
	private String filePath;      //�洢·��
	private int isDirectory;      //1��ʾ·����2��ʾ�ļ�
	private String objectName;    //��������
	private double size;             //�ļ������С����λMB��������ΪĿ¼ʱֵΪ0
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
