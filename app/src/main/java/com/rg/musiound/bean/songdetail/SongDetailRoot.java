/**
  * Copyright 2021 bejson.com 
  */
package com.rg.musiound.bean.songdetail;
import java.util.List;

/**
 * Auto-generated: 2021-03-05 22:14:55
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class SongDetailRoot {

    private List<Songs> songs;
    private List<Privileges> privileges;
    private int code;
    public void setSongs(List<Songs> songs) {
         this.songs = songs;
     }
     public List<Songs> getSongs() {
         return songs;
     }

    public void setPrivileges(List<Privileges> privileges) {
         this.privileges = privileges;
     }
     public List<Privileges> getPrivileges() {
         return privileges;
     }

    public void setCode(int code) {
         this.code = code;
     }
     public int getCode() {
         return code;
     }

}