package com.isle.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class PublicUtil {


    public static String readHtmlContentFromEntity(HttpEntity httpEntity) throws Exception {
        String html = "";
        Header header = httpEntity.getContentEncoding();
        if (httpEntity.getContentLength() < 2147483647L) { // EntityUtils无法处理ContentLength超过2147483647L的Entity
            if (header != null && "gzip".equals(header.getValue())) {
                html = EntityUtils.toString(new GzipDecompressingEntity(httpEntity), "UTF-8");
            } else {
                html = EntityUtils.toString(httpEntity, "UTF-8");
            }
        } else {
            InputStream in = httpEntity.getContent();
            if (header != null && "gzip".equals(header.getValue())) {
                html = unZip(in, ContentType.getOrDefault(httpEntity).getCharset().toString());
            } else {
                html = readInStreamToString(in, ContentType.getOrDefault(httpEntity).getCharset().toString());
            }
            if (in != null) {
                in.close();
            }
        }
        EntityUtils.consume(httpEntity);
        return html;
    }

    public static String readInStreamToString(InputStream in, String charSet) throws IOException {
        StringBuilder str = new StringBuilder();
        String line;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, charSet));
        while ((line = bufferedReader.readLine()) != null) {
            str.append(line);
            str.append("\n");
        }
        if (bufferedReader != null) {
            bufferedReader.close();
        }
        return str.toString();
    }

    public static String unZip(InputStream in, String charSet) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        GZIPInputStream gis = null;
        try {
            gis = new GZIPInputStream(in);
            byte[] _byte = new byte[1024];
            int len = 0;
            while ((len = gis.read(_byte)) != -1) {
                baos.write(_byte, 0, len);
            }
            String unzipString = new String(baos.toByteArray(), charSet);
            return unzipString;
        } finally {
            if (gis != null) {
                gis.close();
            }
            if (baos != null) {
                baos.close();
            }
        }
    }


    public static DefaultHttpClient getHttpClient(String proxyIp) {

        DefaultHttpClient client = new DefaultHttpClient(new ThreadSafeClientConnManager());
        LaxRedirectStrategy redirectStrategy = new LaxRedirectStrategy();
        client.setRedirectStrategy(redirectStrategy);


        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 40000);
        client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);


//		client.getParams().setParameter("http.protocol.max-redirects", 20);
//		client.getParams().setParameter(CoreConnectionPNames.SO_KEEPALIVE, true);
        client.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);

        if (StringUtils.isNotBlank(proxyIp)) {
            String[] ippool = proxyIp.split(":");
            HttpHost proxy = new HttpHost(ippool[0], Integer.parseInt(ippool[1]), "http");
            client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
            AuthScope auth = new AuthScope(ippool[0], Integer.parseInt(ippool[1]));
            Credentials credentials = new org.apache.http.auth.NTCredentials("test","test123!@#","","");
            client.getCredentialsProvider().setCredentials(auth, credentials);
        }

        return client;
    }

    public static String getUserName(String user_name,String steamid) {
        DefaultHttpClient client = getHttpClient("47.52.11.194:1122");
        HttpGet httpGet = new HttpGet("https://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key=CF23C3E8FFDA0857C9763AC1193246AE&steamids="+steamid);
        HttpResponse execute = null;
        try {
            execute = client.execute(httpGet);
            String content = PublicUtil.readHtmlContentFromEntity(execute.getEntity());
            System.out.println(content);
            JSONObject jsonObject = JSONObject.fromObject(content);
            JSONObject response = jsonObject.getJSONObject("response");
            if(response==null){
                System.out.println(user_name+"|"+steamid+"->未获取到用户头像");
                return "ERROR_您的StremId有可能不对哦;请找管理核实";
            }else {
                JSONArray players = response.getJSONArray("players");
                String url = players.getJSONObject(0).getString("avatarfull");
                String personaname = players.getJSONObject(0).getString("personaname");
                System.out.println(user_name+"|"+steamid+"->获取到用户名字:"+personaname);
                DBHelper.getInstance().updateSql("update user set image = ?,steam_name = ? where steamid = ?", new Object[]{url,personaname,steamid});
                return personaname;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR_存龙异常;请重新存取";
        }
    }


}
