package com.xaicif.sso.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
@NoArgsConstructor
public class BaseSignRequest {
    @NotNull(message = "timestamp is null")
    private Long timestamp;
    @NotEmpty(message = "clientCode is empty")
    private String clientCode;
    @NotEmpty(message = "signature is empty")
    private String signature;

    public Map<String, Object> toSignMap() {
        String s = JSON.toJSONString(this, SerializerFeature.IgnoreNonFieldGetter, SerializerFeature.WriteMapNullValue);
        Map map = JSON.parseObject(s, Map.class);
        map.remove("signature");
        return map;
    }

    public String toJsonString() {
        return JSON.toJSONString(this, SerializerFeature.IgnoreNonFieldGetter, SerializerFeature.WriteMapNullValue);
    }
}
