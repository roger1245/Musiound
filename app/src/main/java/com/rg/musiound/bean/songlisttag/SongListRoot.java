package com.rg.musiound.bean.songlisttag;

/**
 * Copyright 2021 bejson.com
 */
import java.util.List;

/**
 * Auto-generated: 2021-03-03 20:21:30
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class SongListRoot {

    private List<Tags> tags;
    private int code;
    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }
    public List<Tags> getTags() {
        return tags;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

}
