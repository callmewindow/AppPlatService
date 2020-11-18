package entity;

import javax.xml.bind.annotation.XmlRootElement;
/**
 * Created by 杜鹏宇 on 2014/7/4.
 */
@XmlRootElement(name = "struct")
public class Struct {
    private String parentPath;
    private String fileName;
    private String data;
    private String userId;

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
