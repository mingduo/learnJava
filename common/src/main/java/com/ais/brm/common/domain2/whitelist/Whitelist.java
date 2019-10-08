package com.ais.brm.common.domain2.whitelist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 构建指定类型的白名单列表
 * <p>
 * 说明：一个风险对象（类型）可能存在多种白名单类型
 *
 * @author lulj
 */
public class Whitelist {
    private int riskObjectTypeId;

    /**
     * WhitelistType -> WhitelistEntry[]
     */
    private Map<Integer, WhitelistEntry[]> whitelistMap;

    public int getRiskObjectTypeId() {
        return riskObjectTypeId;
    }

    public WhitelistEntry[] getWhitelist(int whitelistType) {
        return whitelistMap.get(whitelistType);
    }

    public int[] getWhitelistTypes() {
        int[] wlTypes = new int[whitelistMap.size()];
        int i = 0;
        for (Integer wlType : whitelistMap.keySet()) {
            wlTypes[i++] = wlType;
        }

        return wlTypes;
    }

    private Whitelist(int riskObjectTypeId) {
        this.riskObjectTypeId = riskObjectTypeId;
        this.whitelistMap = new HashMap<>();
    }

    public static Whitelist build(int riskObjectTypeId, List<WhitelistEntry> whitelist) {
        Map<Integer, List<WhitelistEntry>> tmpMap = new HashMap<>();
        for (WhitelistEntry entry : whitelist) {
            if (entry.getRiskObjectTypeId() != riskObjectTypeId) {
                continue;
            }

            List<WhitelistEntry> list = tmpMap.get(entry.getWhitelistType());
            if (list == null) {
                list = new ArrayList<WhitelistEntry>();
                tmpMap.put(entry.getWhitelistType(), list);
            }

            list.add(entry);
        }

        Whitelist wl = new Whitelist(riskObjectTypeId);
        for (Map.Entry<Integer, List<WhitelistEntry>> wlEntry : tmpMap.entrySet()) {
            wl.whitelistMap.put(wlEntry.getKey(),
                    wlEntry.getValue().toArray(new WhitelistEntry[wlEntry.getValue().size()]));
        }

        return wl;
    }
}
