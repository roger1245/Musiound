/**
  * Copyright 2021 bejson.com 
  */
package com.rg.musiound.bean.songlist;
import java.util.List;

/**
 * Auto-generated: 2021-03-03 21:30:45
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class SongList {

    private List<Playlists> playlists;
    private int total;
    private int code;
    private boolean more;
    private String cat;
    public void setPlaylists(List<Playlists> playlists) {
         this.playlists = playlists;
     }
     public List<Playlists> getPlaylists() {
         return playlists;
     }

    public void setTotal(int total) {
         this.total = total;
     }
     public int getTotal() {
         return total;
     }

    public void setCode(int code) {
         this.code = code;
     }
     public int getCode() {
         return code;
     }

    public void setMore(boolean more) {
         this.more = more;
     }
     public boolean getMore() {
         return more;
     }

    public void setCat(String cat) {
         this.cat = cat;
     }
     public String getCat() {
         return cat;
     }

}