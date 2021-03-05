/**
  * Copyright 2021 bejson.com 
  */
package com.rg.musiound.bean.songdetail;
import java.util.List;

/**
 * Auto-generated: 2021-03-05 19:59:32
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class SongDetailRoot {

    private List<Data> data;
    private int code;
    public void setData(List<Data> data) {
         this.data = data;
     }
     public List<Data> getData() {
         return data;
     }

    public void setCode(int code) {
         this.code = code;
     }
     public int getCode() {
         return code;
     }

}