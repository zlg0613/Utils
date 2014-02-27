/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cn.zlg.excel.newmodel.model;

import cn.zlg.util.New;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author zlg2
 */
public class Model {
    private Map<String,Object> dataMap = New.hashMap();
    private String modelName;
    public void set(String key,Object data){
        dataMap.put(key,data);
    }
    public void addAll(Map map){
        Set<Entry> es = map.entrySet();
        for(Entry e:es){
            dataMap.put((String) e.getKey(), e.getValue());
        }
    }
    
    public Object get(String key){
        return dataMap.get(key);
    }
    
    public String getStr(String key){
        return (String) dataMap.get(key);
    }

    public Map<String, Object> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
    
}
