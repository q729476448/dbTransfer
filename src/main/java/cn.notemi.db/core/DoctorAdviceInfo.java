package cn.notemi.db.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Title：DoctorAdviceInfo
 * Description：住院医嘱信息新
 *
 * @author Flicker
 * @create 2017/2/27 11:45
 **/
public class DoctorAdviceInfo {
    public static void main(String args[]) throws Exception {
        //定义查询结果集
        ResultSet rs = null;
        //定义Statement
        Statement s1 = null;
        Statement s2 = null;
        //两条Connection连接
        //源数据库
        Connection cn1 = DriverManager.getConnection("jdbc:sqlserver://127.0.0.1:1433;databaseName=yygl","sa","12345678910");
        //目标数据库
        Connection cn2 = DriverManager.getConnection("jdbc:sqlserver://127.0.0.1:1433;databaseName=yygl2","sa","12345678910");

        long startTime = 0;//开始时间
        long endTime = 0; //结束时间
        int count = 1; //计数
        int onerun = 0; //执行的最大数
        int datanum = 0; //总条数
        int num = 0; //取整最大数据

        try {
            //取得当前时间
            startTime = System.currentTimeMillis();
            //加载jdbc
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            cn2.setAutoCommit(false);//插入数据库时使用事务批量提交
            if (cn1 != null) {
                s1 = cn1.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                s2 = cn2.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                //查询源数据库
                rs = s1.executeQuery("select count(*) from 住院医嘱信息新");
                if (rs.next()) {
                    //获取总数据条数
                    datanum = rs.getInt(1);
                }
                System.out.println("总记录数："+datanum+"条");
                onerun = 10000;//执行的最大数
                num = datanum/onerun*onerun;//计算出总条数符合每批10000的数量是多少
                s1.setMaxRows(datanum);
                s1.setFetchSize(onerun);//每批执行的数据条数
                //查询源数据库
                rs = s1.executeQuery("select myid,住院号,操作员,开始时间,停止时间,费用名,类型,每次用量,每天几次,用法,代码,单价,明天开停,状态,开始日期,停止日期 from 住院医嘱信息新 ");
                while (rs.next()) {
                    s2.addBatch("insert into 住院医嘱信息新 (myid,住院号,操作员,开始时间,停止时间,费用名,类型,每次用量,每天几次," +
                            "用法,代码,单价,明天开停,状态,开始日期,停止日期) values('"
                            +rs.getString("myid")+"','"
                            +rs.getString("住院号")+"','"
                            +rs.getString("操作员")+"','"
                            +rs.getString("开始时间")+"','"
                            +rs.getString("停止时间")+"','"
                            +rs.getString("费用名")+"','"
                            +rs.getString("类型")+"','"
                            +rs.getString("每次用量")+"','"
                            +rs.getString("每天几次")+"','"
                            +rs.getString("用法")+"','"
                            +rs.getString("代码")+"','"
                            +rs.getString("单价")+"','"
                            +rs.getString("明天开停")+"','"
                            +rs.getString("状态")+"','"
                            +rs.getString("开始日期")+"','"
                            +rs.getString("停止日期")+"')");
                    if (count > num) {//10000取整后剩余的小数据量就顺序插入
                        s2.executeBatch();
                        cn2.commit();
                        s2.clearBatch();
                    } else {//数据够批次的就按批量插入
                        if (count % onerun == 0) {//10000条一批插入
                            s2.executeBatch();
                            cn2.commit();
                            s2.clearBatch();
                        }
                    }
                    if (num % 10000 == 0){
                        System.out.println(num);
                    }
                    num++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rs.close();
            s1.close();
            s2.close();
            cn1.close();
            cn2.close();
        }
        endTime = System.currentTimeMillis();
        System.out.println("成功移植数据："+(count-1)+"条，耗时"+(endTime-startTime)/1000+"秒");
    }
}
