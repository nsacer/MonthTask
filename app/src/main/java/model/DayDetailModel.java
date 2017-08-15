package model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2017/8/13.
 * 每次消费详细model
 */

@Table(name = "dayDetailModel")
public class DayDetailModel {

    /**
     * id，DayModel的id
     * */
    @Column(name = "id", isId = true)
    private int id;

    /**
     * 每次消费的金额
     * */
    @Column(name = "money")
    private double money;

    /**
     * 每笔消费详细时间
     * */
    @Column(name = "dayTime")
    private String dayDate;

    /**
     * 每笔消费的说明
     * */
    @Column(name = "project")
    private String project;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getDayDate() {
        return dayDate;
    }

    public void setDayDate(String dayDate) {
        this.dayDate = dayDate;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }
}
