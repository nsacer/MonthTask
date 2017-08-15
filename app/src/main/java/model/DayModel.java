package model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/13.
 * 每日消费概略model
 */

@Table(name = "dayModel")
public class DayModel implements Serializable {

    /**
     * id，根据id查询dayDetailModel
     * */
    @Column(name = "id", isId = true)
    private int id;

    /**
     * 进行状态是否完成
     * true：完成
     * false:未完成
     * */
    @Column(name = "complete")
    private boolean complete;

    /**
     * 剩余金额
     * */
    @Column(name = "moneySurplus")
    private double moneySurplus;

    /**
     * 日期
     * */
    @Column(name = "dayDate")
    private String dayDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public double getMoneySurplus() {
        return moneySurplus;
    }

    public void setMoneySurplus(double moneySurplus) {
        this.moneySurplus = moneySurplus;
    }

    public String getDayDate() {
        return dayDate;
    }

    public void setDayDate(String dayDate) {
        this.dayDate = dayDate;
    }
}
