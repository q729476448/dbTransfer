package cn.notemi.db.core;

import cn.notemi.db.main.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Title：PayList
 * Description：收费项目
 *
 * @author Flicker
 * @create 2017/2/27 11:45
 **/
public class PayList {
    public void go() throws Exception {
        Data data = new Data();
        //定义查询结果集
        ResultSet rs = null;
        //定义Statement
        Statement s1 = null;
        Statement s2 = null;
        //两条Connection连接
        //源数据库
        Connection cn1 = DriverManager.getConnection(data.getUrl1(),data.getUser1(),data.getPassword1());
        //目标数据库
        Connection cn2 = DriverManager.getConnection(data.getUrl2(),data.getUser2(),data.getPassword2());

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
            cn2.setAutoCommit(false);//插入oracle数据库时使用事务批量提交
            if (cn2 != null) {
                s1 = cn1.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                s2 = cn2.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                //查询源数据库
                rs = s1.executeQuery("select count(*) from 收费项目");
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
                rs = s1.executeQuery("select ID,项目,项目代码 from 收费项目 ");
                while (rs.next()) {
                    s2.addBatch("insert into 收费项目 (ID,项目,项目代码) values('"
                            +rs.getString("ID")+"','"
                            +rs.getString("项目")+"','"
                            +rs.getString("项目代码")+"')");
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
                    count++;
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
    public static void main(String args[]) throws Exception {

    }
}
