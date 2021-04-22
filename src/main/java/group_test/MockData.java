package group_test;

/**
 * @author aSigma
 * @date 2021/3/21 20:57
 */
public class MockData {
    private Long timestamp;
    private String userName;
    private String url;
    private String ip;
    private String traffic;

    public MockData(Long timestamp, String userName, String url, String ip, String traffic) {
        this.timestamp = timestamp;
        this.userName = userName;
        this.url = url;
        this.ip = ip;
        this.traffic = traffic;
    }

    @Override
    public String toString() {
        return "data{" +
                "timestamp=" + timestamp +
                ", userName='" + userName + '\'' +
                ", url='" + url + '\'' +
                ", ip='" + ip + '\'' +
                ", traffic='" + traffic + '\'' +
                '}';
    }
}


