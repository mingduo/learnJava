package com.ais.brm.common.domain2.ruleegine;

import com.ais.brm.common.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jeasy.rules.api.Facts;

import java.util.Map;

/**
 * <table border="1">
 * <tr><th>@Description:</th></tr>
 * <tr><td>@Date:Created in 2018-3-13</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public class FactDef {
    Gson gson = new Gson();

    public Facts getJsonFacts(String jsonStr) {
        Facts facts = new Facts();
        JsonElement element = gson.fromJson(jsonStr, JsonElement.class);
        JsonObject jsonObj = element.getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : jsonObj.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().getAsString();
            if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value)) {
                facts.put(key, value);
            }
        }
        return facts;

    }
    public Facts getFacts(String str) {
        Facts facts = new Facts();
        facts.put("p",str);
        return facts;

    }

}
