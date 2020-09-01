//package com.itcast.actviti7demo.utils;
//
//import com.alibaba.fastjson.JSONObject;
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.exceptions.JWTDecodeException;
//import com.park.hanyi.mapper.AdminUserMapper;
//import com.park.hanyi.model.User;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.http.HttpEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.message.BasicHeader;
//import org.apache.http.protocol.HTTP;
//import org.apache.http.util.EntityUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.*;
//import java.net.URL;
//import java.net.URLConnection;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//@Component
//public class CommonUtil {
//    @Autowired
//    private AdminUserMapper adminUserMapper;
//
//
//    public static String jwtScret = "jfisaufoejroiwejohaofisd";
//    private long expire = 12*60 * 60;//秒
//
//
//
//    //根据值取key
//    public static String getKeyByValue(JSONObject map, Object value) {
//        String keys = "";
//        Iterator it = map.entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry entry = (Map.Entry) it.next();
//            Object obj = entry.getValue();
//            if (obj != null && obj.equals(value)) {
//                keys = (String) entry.getKey();
//            }
//        }
//        return keys;
//    }
//
//
//    public String getToken(User user) {
//        Date nowDate = new Date();
//        //过期时间
//        Date expireDate = new Date(nowDate.getTime() + expire * 1000);
//        String token = "";
//        token = JWT.create().withAudience(String.valueOf(user.getUserId())).withIssuedAt(nowDate).withExpiresAt(expireDate)
//                .sign(Algorithm.HMAC256(jwtScret));
//        return token;
//    }
//
//
//
//    /**
//     * 获取真实IP
//     * @param request 请求体
//     * @return 真实IP
//     */
//    public static String getIpAdrress(HttpServletRequest request) {
//        String Xip = request.getHeader("X-Real-IP");
//        String XFor = request.getHeader("X-Forwarded-For");
//        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
//            //多次反向代理后会有多个ip值，第一个ip才是真实ip
//            int index = XFor.indexOf(",");
//            if(index != -1){
//                return XFor.substring(0,index);
//            }else{
//                return XFor;
//            }
//        }
//        XFor = Xip;
//        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
//            return XFor;
//        }
//        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
//            XFor = request.getHeader("Proxy-Client-IP");
//        }
//        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
//            XFor = request.getHeader("WL-Proxy-Client-IP");
//        }
//        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
//            XFor = request.getHeader("HTTP_CLIENT_IP");
//        }
//        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
//            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
//        }
//        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
//            XFor = request.getRemoteAddr();
//        }
//        return XFor;
//    }
//
//    public String getRandomString(int length) {
//        String str = "123456789ABCDEF";
//        Random random = new Random();
//        StringBuffer sb = new StringBuffer();
//        for (int i = 0; i < length; i++) {
//            int number = random.nextInt(15);
//            sb.append(str.charAt(number));
//        }
//        return sb.toString();
//    }
//
//
//    //方法功能描述:       判断是否是IE浏览器
//    public static boolean isMSBrowser(HttpServletRequest request) {
//        String[] IEBrowserSignals = {"MSIE", "Trident", "Edge"};
//        String userAgent = request.getHeader("User-Agent");
//        for (String signal : IEBrowserSignals) {
//            if (userAgent.contains(signal)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    //返回前端中文字符串转换
//    public static String stringConvert(String msg) {
//        String conMsg = "";
//        try {
//            conMsg = new String(msg.getBytes(), "iso-8859-1");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return conMsg;
//    }
//
//    //验证字符串是否是日期
//    public static boolean isValidDate(String str) {
//        boolean convertSuccess = true;
//        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
//            format.setLenient(false);
//            format.parse(str);
//        } catch (ParseException e) {
//            // e.printStackTrace();
//// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
//            convertSuccess = false;
//        }
//        return convertSuccess;
//    }
//
//
//    //判断是否为数字
//    public static boolean isNumeric(String str){
//        Pattern pattern = Pattern.compile("[0-9]*");
//        if(str.indexOf(".")>0){//判断是否有小数点
//            if(str.indexOf(".")==str.lastIndexOf(".") && str.split("\\.").length==2){ //判断是否只有一个小数点
//                return pattern.matcher(str.replace(".","")).matches();
//            }else {
//                return false;
//            }
//        }else {
//            return pattern.matcher(str).matches();
//        }
//    }
//
//    //计算某个日期几个月后的日期
//    public static String getAfterMonth(String inputDate,int number) {
//        Calendar c = Calendar.getInstance();//获得一个日历的实例
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = null; try{
//            date = sdf.parse(inputDate);//初始日期
//        }catch(Exception e){
//
//        }
//        c.setTime(date);//设置日历时间
//        c.add(Calendar.MONTH,number);//在日历的月份上增加6个月
//        String strDate = sdf.format(c.getTime());//的到你想要得6个月后的日期
//        return strDate;
//    }
//    //判断当前时间是星期几，并且返回本周一时间
//    public static String getWeek() {
//        Date now = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//可以方便地修改日期格式
//        String MTime = dateFormat.format( now );
//        Date date1=null;
//        try {
//            date1 = dateFormat.parse(MTime);//系统当前时间
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date1);
//
//        cal.add(Calendar.DAY_OF_MONTH, -1);//取当前日期的前一天.
//
//        cal.add(Calendar.DAY_OF_MONTH, +1);//取当前日期的后一天.
//        // hour中存的就是星期几了，其范围 1~7
//        // 1=星期日 7=星期六，其他类推
//        int hour=cal.get(Calendar.DAY_OF_WEEK);
//        System.out.println("当前系统日期"+MTime);
//        String stime=null;
//        String etime=null;
//        switch (hour) {
//            case 2:
//                //周一
//                stime=MTime;
//                cal.add(Calendar.DAY_OF_MONTH, +6);//获取当前日期的后六天.
//                String  date2=dateFormat.format(cal.getTime());
//                etime=date2;
//                break;
//            case 3:
//                //周二
//                cal.add(Calendar.DAY_OF_MONTH, -1);//获取当前日期的前一天.
//                String  date12=dateFormat.format(cal.getTime());
//                stime=date12;
//                cal.add(Calendar.DAY_OF_MONTH, +6);//获取周日时间
//                String  date22=dateFormat.format(cal.getTime());
//                etime=date22;
//                break;
//            case 4:
//                //周三
//                cal.add(Calendar.DAY_OF_MONTH, -2);
//                String  date13=dateFormat.format(cal.getTime());//取当前日期的前两天.
//                stime=date13;
//                cal.add(Calendar.DAY_OF_MONTH, +6);//获取周日时间
//                String  date23=dateFormat.format(cal.getTime());
//                etime=date23;
//                break;
//            case 5:
//                //周四
//                cal.add(Calendar.DAY_OF_MONTH, -3);//获取取当前日期的前三天.
//                String  date14=dateFormat.format(cal.getTime());
//                stime=date14;
//                cal.add(Calendar.DAY_OF_MONTH, +6);//获取周日时间
//                String  date24=dateFormat.format(cal.getTime());
//                etime=date24;
//                break;
//            case 6:
//                //周五
//                cal.add(Calendar.DAY_OF_MONTH, -4);//获取取当前日期的前四天.
//                String  date15=dateFormat.format(cal.getTime());
//                stime=date15;
//                cal.add(Calendar.DAY_OF_MONTH, +6);//获取周日时间
//                String  date25=dateFormat.format(cal.getTime());
//                etime=date25;
//                break;
//            case 7:
//                //周六
//                cal.add(Calendar.DAY_OF_MONTH, -5);//获取取当前日期的前五天.
//                String  date16=dateFormat.format(cal.getTime());
//                stime=date16;
//                cal.add(Calendar.DAY_OF_MONTH, +6);//获取周日时间
//                String  date26=dateFormat.format(cal.getTime());
//                etime=date26;
//                break;
//            default:
//                //周日
//                cal.add(Calendar.DAY_OF_MONTH, -6);
//                String  date7=dateFormat.format(cal.getTime());//取当前日期的前六天.
//                stime=date7;
//                etime=MTime;
//                break;
//        }
////        System.out.println("本周一日期："+stime+";周日时间："+etime);
//        return stime;
//    }
//
//    //根据当前时间获取上周一导上周天时间
//    public static Map getBefore() {
//        //获得当前系统的时间
//        Date date =new Date();
//
//        //时间模板
//        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
//        String dateForma=simpleDateFormat.format(date);
//
//        Calendar calendar1 = Calendar.getInstance();
//        Calendar calendar2 = Calendar.getInstance();
//        int dayOfWeek=calendar1.get(Calendar.DAY_OF_WEEK)-1;
//        int offset1=1-dayOfWeek;
//        int offset2=7-dayOfWeek;
//        calendar1.add(Calendar.DATE, offset1-7);
//        calendar2.add(Calendar.DATE, offset2-7);
//        Date timePreMonday= calendar1.getTime();
//        Date timeThisMonday = calendar2.getTime();
//        String formatPreMonday = simpleDateFormat.format(timePreMonday);//获取符合要求格式的上周周一日期
//        String formatThisMonday = simpleDateFormat.format(timeThisMonday);//获取符合要求格式的上周周日的日期
//
//        HashMap format = new HashMap();
//        format.put("formatPreMonday",formatPreMonday);
//        format.put("formatThisMonday",formatThisMonday);
////        System.out.println("上周周一的日期为："+formatPreMonday );
////        System.out.println("上之周日的日期为："+formatThisMonday );
//
//        return format;
//    }
//
//    /**
//    * 日期转换为字符串
//    * @author:wcx
//    * @return:
//    * @date:2019/6/12 16:54
//    */
//    public static String dateToString(Date date){
//        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
//    }
//  /**
//  * 将时间转换为时间戳
//  * @author:wcx
//  * @return:
//  * @date:2019/6/12 16:54
//  */
//    public  String dateToStamp(String s) throws ParseException {
//        String res;
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = simpleDateFormat.parse(s);
//        long ts = date.getTime();
//        res = String.valueOf(ts);
//        return res;
//    }
//
//    //通过token获取用户信息
//    public User getUserAuthority(HttpServletRequest request) {
//        String token=request.getParameter("token");
//        String userId= null;
//        try {
//            userId = JWT.decode(token).getAudience().get(0);
//        } catch (JWTDecodeException e) {
//            return null;
//            //throw new RuntimeException("401");
//        }
//        //根据用户id查询用户信息
//        User user = adminUserMapper.selectByPrimaryKey(Integer.parseInt(userId));
//        if(user ==null){
//            return null;
//        }
//        return user;
//
//    }
//
//
//
//    /**
//     * 将日期转换为yyyy-mm-dd形式
//     * @author:wcx
//     * @return:
//     * @date:2019/6/27 15:14
//     */
//    public static Date dateFormat(Date date){
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date date1=null;
//        try {
//            date1 = simpleDateFormat.parse(simpleDateFormat.format(date));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return date1;
//    }
//
//
//
//
//    /**
//     * 向指定 URL 发送POST方法的请求
//     *
//     * @param url
//     *            发送请求的 URL
//     * @param param
//     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
//     * @return 所代表远程资源的响应结果
//     */
//    public static String sendPost(String url, String param) {
//        PrintWriter out = null;
//        BufferedReader in = null;
//        String result = "";
//        try {
//            URL realUrl = new URL(url);
//            // 打开和URL之间的连接
//            URLConnection conn = realUrl.openConnection();
//            // 设置通用的请求属性
//            conn.setRequestProperty("accept", "*/*");
//            conn.setRequestProperty("connection", "Keep-Alive");
//            conn.setRequestProperty("user-agent",
//                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//            // 发送POST请求必须设置如下两行
//            conn.setDoOutput(true);
//            conn.setDoInput(true);
//            // 获取URLConnection对象对应的输出流
//            out = new PrintWriter(conn.getOutputStream());
//            // 发送请求参数
//            out.print(param);
//            // flush输出流的缓冲
//            out.flush();
//            // 定义BufferedReader输入流来读取URL的响应
//            in = new BufferedReader(
//                    new InputStreamReader(conn.getInputStream()));
//            String line;
//            while ((line = in.readLine()) != null) {
//                result = line;
//            }
//        } catch (Exception e) {
//            System.out.println("发送 POST 请求出现异常！"+e);
//            e.printStackTrace();
//        }
//        //使用finally块来关闭输出流、输入流
//        finally{
//            try{
//                if(out!=null){
//                    out.close();
//                }
//                if(in!=null){
//                    in.close();
//                }
//            }
//            catch(IOException ex){
//                ex.printStackTrace();
//            }
//        }
//        return result;
//    }
//
//
//    /**
//     * 发送post请求
//     * @param url  路径
//     * @param jsonObject  参数(json类型)
//     * @param encoding 编码格式
//     * @return
//     * @throws ParseException
//     * @throws IOException
//     */
//    public static String send(String url, String jsonObject, String encoding) throws ParseException, IOException{
//        String body = "";
//
//        //创建httpclient对象
//        CloseableHttpClient client = HttpClients.createDefault();
//        //创建post方式请求对象
//        HttpPost httpPost = new HttpPost(url);
//
//        //装填参数
//        StringEntity s = new StringEntity(jsonObject, "utf-8");
//        s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
//                "application/json"));
//        //设置参数到请求对象中
//        httpPost.setEntity(s);
//        System.out.println("请求地址："+url);
////        System.out.println("请求参数："+nvps.toString());
//
//        //设置header信息
//        //指定报文头【Content-type】、【User-Agent】
////        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
//        httpPost.setHeader("Content-type", "application/json");
//        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
//
//        //执行请求操作，并拿到结果（同步阻塞）
//        CloseableHttpResponse response = client.execute(httpPost);
//        //获取结果实体
//        HttpEntity entity = response.getEntity();
//        if (entity != null) {
//            //按指定编码转换结果实体为String类型
//            body = EntityUtils.toString(entity, encoding);
//        }
//        EntityUtils.consume(entity);
//        //释放链接
//        response.close();
//        System.out.println(body);
//        return body;
//    }
//
//
//    /**
//     * 将string转换为Date类型,再转换为string类型
//     * 解决跨年问题
//     * @author:wcx
//     * @return:
//     * @date:2019/7/4 14:41
//     */
//    public static String dateFormatToYear(String date){
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
//        if(date==null||date==""){
//            date = simpleDateFormat.format(new Date());
//        }
//        String string = null;
//        try {
//            string = simpleDateFormat.format(simpleDateFormat.parse(date));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return string;
//    }
//
//
//    /**
//     * 判断字符串是否为数字,0-9重复0次或者多次
//     * @param strnum
//     * @return
//     */
//    private static boolean isNumeric1(String strnum) {
//        Pattern pattern = Pattern.compile("[0-9]*");
//        Matcher isNum = pattern.matcher(strnum);
//        if (isNum.matches()) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    /**
//     * 功能：判断字符串出生日期是否符合正则表达式：包括年月日，闰年、平年和每月31天、30天和闰月的28天或者29天
//     *
//     * @param
//     * @return
//     */
//    public static boolean isDate(String strDate) {
//
//        Pattern pattern = Pattern
//                .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))?$");
//        Matcher m = pattern.matcher(strDate);
//        if (m.matches()) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//
//    //根据身份证号计算年龄
//    public static int getAgeByIDNumber(String idNumber) {
//        String dateStr;
//        if (idNumber.length() == 15) {
//            dateStr = "19" + idNumber.substring(6, 12);
//        } else if (idNumber.length() == 18) {
//            dateStr = idNumber.substring(6, 14);
//        } else {//默认是合法身份证号，但不排除有意外发生
//            return -1;
//        }
//
//
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
//        try {
//            Date birthday = simpleDateFormat.parse(dateStr);
//            return getAgeByDate(birthday);
//        } catch (ParseException e) {
//            return -1;
//        }
//
//
//    }
//
//    public static int getAgeByDate(Date birthday) {
//        Calendar calendar = Calendar.getInstance();
//
//        //calendar.before()有的点bug
//        if (calendar.getTimeInMillis() - birthday.getTime() < 0L) {
//            return -1;
//        }
//
//
//        int yearNow = calendar.get(Calendar.YEAR);
//        int monthNow = calendar.get(Calendar.MONTH);
//        int dayOfMonthNow = calendar.get(Calendar.DAY_OF_MONTH);
//
//        calendar.setTime(birthday);
//
//
//        int yearBirthday = calendar.get(Calendar.YEAR);
//        int monthBirthday = calendar.get(Calendar.MONTH);
//        int dayOfMonthBirthday = calendar.get(Calendar.DAY_OF_MONTH);
//
//        int age = yearNow - yearBirthday;
//
//
//        if (monthNow <= monthBirthday && monthNow == monthBirthday && dayOfMonthNow < dayOfMonthBirthday || monthNow < monthBirthday) {
//            age--;
//        }
//
//        return age;
//    }
//
//}
