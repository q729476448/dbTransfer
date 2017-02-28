package cn.notemi.db.main;

import cn.notemi.db.core.SickBed;
import cn.notemi.db.util.Conn;

/**
 * Title：Direct
 * Description：
 *
 * @author Flicker
 * @create 2017/2/28 10:58
 **/
public class Direct {

    public static void main(String[] args) {
        Data data = new Data();
        /*连接数据库检测*/
        Conn.getConn(data.getUrl1(),data.getUser1(),data.getPassword1());
        Conn.getConn(data.getUrl2(),data.getUser2(),data.getPassword2());
        /**/
        SickBed sickBed = new SickBed();
        try {
            sickBed.SickBed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
