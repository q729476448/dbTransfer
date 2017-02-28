package cn.notemi.db.main;

import cn.notemi.db.core.*;
import cn.notemi.db.util.Conn;

/**
 * Title：MainDirect
 * Description：
 *
 * @author Flicker
 * @create 2017/2/28 10:58
 **/
public class MainDirect {

    public static void main(String[] args) {
        Data data = new Data();
        /*连接数据库检测*/
        Conn.getConn(data.getUrl1(),data.getUser1(),data.getPassword1());
        Conn.getConn(data.getUrl2(),data.getUser2(),data.getPassword2());
        /*转移各数据库*/
        SickBed sickBed = new SickBed();
        DoctorAdviceInfo doctorAdviceInfo = new DoctorAdviceInfo();
        InpatientInfo inpatientInfo = new InpatientInfo();
        PatientInfo patientInfo = new PatientInfo();
        PayList payList = new PayList();
        PharmacyInfo pharmacyInfo = new PharmacyInfo();
        try {
            sickBed.go();
            doctorAdviceInfo.go();
            inpatientInfo.go();
            patientInfo.go();
            payList.go();
            pharmacyInfo.go();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
