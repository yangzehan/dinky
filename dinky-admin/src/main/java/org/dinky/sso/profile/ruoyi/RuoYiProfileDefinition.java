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

import static org.pac4j.core.profile.AttributeLocation.PROFILE_ATTRIBUTE;

import java.util.Arrays;

import org.pac4j.core.profile.converter.Converters;
import org.pac4j.core.profile.converter.GenderConverter;

import org.pac4j.oauth.config.OAuth20Configuration;
import org.pac4j.oauth.profile.JsonHelper;
import org.pac4j.oauth.profile.definition.OAuth20ProfileDefinition;


import com.fasterxml.jackson.databind.JsonNode;
import com.github.scribejava.core.exceptions.OAuthException;
import com.github.scribejava.core.model.OAuth2AccessToken;

/**
 * @author 杨泽翰
 */
public class RuoYiProfileDefinition extends OAuth20ProfileDefinition<RuoYiProfile, OAuth20Configuration>  {


    private String profileUrl = "http://127.0.0.1:48080/admin-api/system/oauth2/user/get";
    public static final String NICKNAME = "nickname";
    /**
     * Gender, 1 male and 2 female
     */
    public static final String SEX = "sex";

    public static final String PROVINCE = "province";

    public static final String CITY = "city";

    /**
     * User avatar, the last value represents the size of the square avatar (0, 46, 64, 96, 132 values are optional, 0 is 640 * 640
     * square avatar), the item is empty when the user has no avatar
     */
    public static final String AVATAR = "avatar";



    public RuoYiProfileDefinition() {
        Arrays.stream(new String[] { NICKNAME, PROVINCE, CITY})
                .forEach(a -> primary(a, Converters.STRING));
        primary(SEX, new GenderConverter("1", "2"));
        primary(AVATAR, Converters.URL);
    }

    @Override
    public String getProfileUrl(OAuth2AccessToken oAuth2AccessToken, OAuth20Configuration oAuth20Configuration) {
        return this.profileUrl;
    }


    @Override
    public RuoYiProfile extractUserProfile(String body) {
        final RuoYiProfile profile = new RuoYiProfile();
//        需要调试一下看看从哪里开始解析
        final JsonNode json = JsonHelper.getFirstNode(body);
        if (json != null) {
            Integer code = (Integer) JsonHelper.getElement(json, "code");
            if (code != 0) {
                Object errmsg = JsonHelper.getElement(json, "msg");
                throw new OAuthException(errmsg != null ? errmsg.toString() : "error code " + code);
            }
            for (final String attribute : getPrimaryAttributes()) {
                convertAndAdd(profile, PROFILE_ATTRIBUTE, attribute, JsonHelper.getElement(json.get("data"), attribute));
            }
        } else {
            raiseProfileExtractionJsonError(body);
        }
        return profile;
    }
}
