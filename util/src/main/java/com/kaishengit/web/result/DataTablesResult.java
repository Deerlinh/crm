package com.kaishengit.web.result;

import java.util.List;

/**
 * Created by 蔡林红 on 2017/11/8.
 */
public class DataTablesResult<T> {

    private  Integer draw;
    private  Integer recordsTotal;
    private  Integer recordsFiltered;
    private List<T> data;
    public DataTablesResult(){}

    public  DataTablesResult(Integer draw,Integer recordsTotal,Integer recordsFiltered,List<T> data){
        this.data=data;
        this.recordsTotal=recordsTotal;
        this.recordsFiltered=recordsFiltered;
        this.draw=draw;
    }
    public  DataTablesResult(Integer draw,Integer recordsTotal,List<T> data){
        this.data=data;
        this.recordsTotal=recordsTotal;
        this.recordsFiltered=recordsTotal;
        this.draw=draw;
    }


    public Integer getdraw() {
        return draw;
    }

    public void setdraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getrecordsTotal() {
        return recordsTotal;
    }

    public void setrecordsTotal(Integer recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public Integer getrecordsFiltered() {
        return recordsFiltered;
    }

    public void setrecordsFiltered(Integer recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
