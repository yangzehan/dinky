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

package org.dinky.sso.profile.ruoyi;

import java.net.URI;

import org.pac4j.core.profile.Gender;
import org.pac4j.oauth.profile.OAuth20Profile;
import org.pac4j.oauth.profile.wechat.WechatProfileDefinition;

/**
 * <p>This class is the user profile for Tencent Wechat with appropriate getters.</p>
 * <p>It is returned by the {@link org.pac4j.oauth.client.WechatClient}.</p>
 *
 * @author zhangzhenli
 * @since 3.1.0
 */
public class RuoYiProfile extends OAuth20Profile {

    private static final long serialVersionUID = 2576512203937798654L;

    @Override
    public String getDisplayName() {
        return (String) getAttribute(WechatProfileDefinition.NICKNAME);
    }

    @Override
    public String getUsername() {
        return (String) getAttribute(WechatProfileDefinition.NICKNAME);
    }

    @Override
    public Gender getGender() {
        return (Gender) getAttribute(WechatProfileDefinition.SEX);
    }


    @Override
    public URI getPictureUrl() {
        return (URI) getAttribute(RuoYiProfileDefinition.AVATAR);
    }
}
