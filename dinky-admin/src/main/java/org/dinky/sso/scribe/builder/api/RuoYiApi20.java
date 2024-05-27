package org.dinky.sso.scribe.builder.api;

import java.io.OutputStream;
import java.util.Map;

import com.github.scribejava.core.oauth2.bearersignature.BearerSignature;
import com.github.scribejava.core.oauth2.bearersignature.BearerSignatureURIQueryParameter;
import com.github.scribejava.core.oauth2.clientauthentication.ClientAuthentication;
import com.github.scribejava.core.oauth2.clientauthentication.RequestBodyAuthenticationScheme;
import org.dinky.sso.scribe.extractors.RuoYiJsonExtractor;
import org.pac4j.oauth.client.WechatClient;
import org.pac4j.scribe.builder.api.GenericApi20;
import org.pac4j.scribe.extractors.WechatJsonExtractor;
import org.pac4j.scribe.service.WechatService;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.extractors.TokenExtractor;
import com.github.scribejava.core.httpclient.HttpClient;
import com.github.scribejava.core.httpclient.HttpClientConfig;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthConstants;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

/**
 * This class represents the OAuth API implementation for Tencent Wechat using OAuth protocol version 2.
 * It could be part of the Scribe library.
 * <p>More info at: <a href=
 * "https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419316505&token=&lang=zh_CN"
 * >OAuth2.0</a></p>
 *
 * @author zhangzhenli
 * @since 3.1.0
 */
public class RuoYiApi20 extends GenericApi20 {


    public RuoYiApi20(String authUrl, String tokenUrl) {
        super(authUrl, tokenUrl);
    }

    @Override
    public TokenExtractor<OAuth2AccessToken> getAccessTokenExtractor() {
        return RuoYiJsonExtractor.instance();
    }
}
