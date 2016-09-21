/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unnbiometrics.database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class Map extends TreeMap<String, Object> {
    public Map() {
    }

    public Map(java.util.Map<? extends String, ? extends Object> m) {
        super(m);
    }
    
    public Map(String key, Object val, Object... kv) {
        super();
        put(key, val);
        for (int i = 1; i < kv.length; i += 2) {
            key = kv[i - 1].toString();
            val = kv[i];
            put(key, val);
        }
    }
    
    public String getStr(String key) {
        Object o = get(key);
        return o == null ? null : o.toString();
    }
    
    public int getInt(String key) {
        Number n = (Number)get(key);
        return n.intValue();
    }
    
    public <T> T getAny(String key) {
        return (T) get(key);
    }
    
    public Map subset(Iterable<String> keys) {
        Map subset = new Map();
        keys.forEach((key) -> {
            Object val = this.get(key);
            if (val != null) subset.put(key, val);
        });
        
        return subset;
    }
    
    public Map subset(String... keys) {
        return subset(Arrays.asList(keys));
    }
    
    public static List<Map> subset(List<Map> maps, Iterable<String> keys) {
        List<Map> subset = new ArrayList<>(maps.size());
        maps.forEach((map) -> { subset.add(map.subset(keys)); });
        
        return subset;
    }
    
    public static List<Map> subset(List<Map> maps, String... keys) {
        return subset(maps, Arrays.asList(keys));
    }
    
    public String toString(String delim, String constVal) {
        StringBuilder sb = new StringBuilder();
        forEach((key, val) -> {
            if (sb.length() > 0) {
                sb.append(delim);
            }
            sb.append(key).append('=');
            sb.append(constVal == null ? val : constVal);
        });
        
        return sb.toString();
    }

    @Override
    public String toString() {
        return toString(",", null);
    }
}
