/*
 *
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.dinky.sso.client;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.github.scribejava.core.httpclient.HttpClient;
import com.github.scribejava.core.httpclient.HttpClientConfig;
import com.github.scribejava.core.oauth.OAuth20Service;
import lombok.Getter;
import lombok.Setter;
import org.dinky.sso.profile.ruoyi.RuoYiProfileCreator;
import org.dinky.sso.profile.ruoyi.RuoYiProfileDefinition;
import org.dinky.sso.scribe.builder.api.RuoYiApi20;
import org.pac4j.oauth.client.OAuth20Client;
import org.pac4j.oauth.profile.wechat.WechatProfileCreator;
import org.pac4j.oauth.profile.wechat.WechatProfileDefinition;
import org.pac4j.scribe.builder.api.WechatApi20;
import org.pac4j.scribe.service.WechatService;

/**
 * @author 杨泽翰
 */
@Setter
@Getter
public class RuoyiClient extends OAuth20Client {

    public enum RuoyiScope {
        USER_READ("user.read"),

        USER_WRITE("user.write");
        private final String permission;
        RuoyiScope(String permission) {
            this.permission = permission;
        }
        public String getPermission() {
            return permission;
        }
    }

    protected List<RuoyiClient.RuoyiScope> scopes;


    public RuoyiClient(final String key, final String secret) {
        setKey(key);
        setSecret(secret);
    }

    @Override
    protected void clientInit() {
        String authUrl = "http://127.0.0.1:80/sso";
        String tokenUrl = "http://127.0.0.1:48080/admin-api/system/oauth2/token";
        RuoYiApi20 ruoYiApi20 = new RuoYiApi20(authUrl, tokenUrl);
        ruoYiApi20.setClientAuthenticationMethod("requestBody");
        configuration.setApi(ruoYiApi20);
        configuration.setScope(getOAuthScope());
        configuration.setProfileDefinition(new RuoYiProfileDefinition());
//        configuration.setWithState(true);
        defaultProfileCreator(new RuoYiProfileCreator(configuration, this));
        super.clientInit();
    }


    protected String getOAuthScope() {
        StringBuilder builder = null;
        if (scopes == null || scopes.isEmpty()) {
            scopes = new ArrayList<>();
            scopes.add(RuoyiScope.USER_READ);
            scopes.add(RuoyiScope.USER_WRITE);
        }
        for (RuoyiScope value : scopes) {
            if (builder == null) {
                builder = new StringBuilder();
            } else {
                builder.append(",");
            }
            builder.append(value.getPermission());
        }
        return builder.toString();
    }

    public void addScope(RuoyiClient.RuoyiScope scopes) {
        if (this.scopes == null) {
            this.scopes = new ArrayList<>();
        }
        this.scopes.add(scopes);
    }
}
