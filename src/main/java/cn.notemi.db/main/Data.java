package cn.notemi.db.main;

/**
 * Title：Data
 * Description：
 *
 * @author Flicker
 * @create 2017/2/28 11:13
 **/
public class Data {
    /* 源数据库 */
    private static String url1 = "jdbc:sqlserver://127.0.0.1:1433;databaseName=yygl";
    private static String user1 = "sa";
    private static String password1 = "12345678910";
    /* 目标数据库 */
    private static String url2 = "jdbc:sqlserver://127.0.0.1:1433;databaseName=yygl2";
    private static String user2 = "sa";
    private static String password2 = "12345678910";

    public static String getUrl1() {
        return url1;
    }

    public static String getUser1() {
        return user1;
    }

    public static String getPassword1() {
        return password1;
    }

    public static String getUrl2() {
        return url2;
    }

    public static String getUser2() {
        return user2;
    }

    public static String getPassword2() {
        return password2;
    }
}
