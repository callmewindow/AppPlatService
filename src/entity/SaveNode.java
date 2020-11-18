package entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by 杜鹏宇 on 2014/7/7.
 */
@XmlRootElement(name = "node")
public class SaveNode {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    private String name;
    private String data;
}
