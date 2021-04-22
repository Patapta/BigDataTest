package group_test;

import com.google.gson.Gson;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Mock {
    public static void main(String[] args) throws Exception {
        createData(99,"2021-03-21", "2021-10-01",
                "ruozedata", 100, 1000, 5000,
                true);
    }

    public static void createData(Integer dataNumbers,String startTime, String endTime,
                                  String userName, Integer userNum, long startTraffic,
                                  long endTraffic, Boolean type) throws Exception {
        PrintStream out = new PrintStream(new FileOutputStream("E:/CodeData/mockData.txt"));
//        PrintWriter writer = new PrintWriter("E:/WorkSpace/HomeWork/data/data03.txt");
        Map<Integer, String> map = getUrlFromMySQL();
        Random random = new Random();
        for (int i = 0; i < dataNumbers; i++) {
            Long timestamp = createTime(startTime,endTime);
            String user = createUserName(userName,userNum);
            String url = map.get(random.nextInt(map.size()));
            String ip = createIp();
            String traffic = createTraffic(startTraffic, endTraffic, type);
            MockData mockData = new MockData(timestamp,user,url,ip,traffic);
            // 本地mock不是json格式的数据
//            writer.write(url + "\t" + traffic + "\t" + ip + "\t" + timestamp);
//            writer.write("\n");
            Gson gson = new Gson();
            String jsonObject = gson.toJson(mockData);
            out.println(jsonObject);
        }
//        writer.close();
        out.close();
    }

    /**
     * 生成范围内随机时间
     * @param startStr 开始时间
     * @param endStr   结束时间
     * @return         范围内时间戳
     * @throws ParseException
     */
    private static Long createTime(String startStr, String endStr) throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        long start = format.parse(startStr).getTime();
        long end = format.parse(endStr).getTime();
        long randomTime = random(start,end);
        return randomTime;
    }

    /**
     * 获取范围内的值(long类型的)
     * @param start 最小值
     * @param end   最大值
     * @return      范围结果值
     */
    private static long random(long start,long end){
        long rtn = start + (long)(Math.random() * (end - start));
        // 是否取临界值，如果不要哪个边界就把哪个边界的判断给去掉
        if(rtn == start || rtn == end){
            return random(start,end);
        }
        return rtn;
    }

    /**
     * 从MySQL里面获取随机的URL
     * @return String类型的URL
     * @throws Exception
     */
    private static Map<Integer, String> getUrlFromMySQL() throws Exception{
        Connection con;
        String driver="com.mysql.jdbc.Driver";
        String mysqlUrl="jdbc:mysql://192.168.101.61:3306/aSigma_data?useUnicode=true&characterEncoding=utf-8&useSSL=false";
        String username="root";
        String password="Forget100!";

        Class.forName(driver);
        con = DriverManager.getConnection(mysqlUrl,username,password);
        Statement statement=con.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM course");
        Map<Integer, String> map = new HashMap<>();
        for (int i = 0;resultSet.next();i++) {
            map.put(i,resultSet.getString("course_domain"));
        }

        con.close();
        return map;
    }

    /**
     * 随机生成用户名
     * @param name   用户名
     * @param range  后缀最大值
     * @return       用户名+后缀随机值
     */
    private static String createUserName(String name,Integer range){
        Random random = new Random();
        int num = random.nextInt(range);
        String userName = name + num;
        return userName;
    }

    /**
     * 创建随机IP地址
     * @return String类型的IP地址
     */
    private static String createIp() {
        Random random = new Random();
        String result = "";
        for (int i = 0; i < 4; i++) {
            if (i == 0){
                result += random.nextInt(255);
            }else {
                result += "." + random.nextInt(255);
            }
        }
        return result;
    }

    private static String createTraffic(long start,long end,Boolean type){
        long num = random(start,end);
        String result = "";
        if (num % 4 == 0 && type.equals(true)){
            result += "PK很短";
        }else {
            result += num;
        }
        return result;
    }
}
