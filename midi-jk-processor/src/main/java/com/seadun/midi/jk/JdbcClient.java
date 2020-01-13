package com.seadun.midi.jk;

import com.github.drinkjava2.jdbpro.SqlOption;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.github.drinkjava2.jsqlbox.sqlitem.SampleItem;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: midi-bundle
 * @Package: com.seadun.midi.jk
 * @ClassName: JdbcClient
 * @Author: Guzhendong
 * @Description:
 * @Date: 2019/5/24 16:02
 * @Version: seadun 1.0
 */
@Log4j2
public class JdbcClient implements Runnable {
    //上次查询数据库的status并记录
    private static List<SysDatasource> FRIST_LIST = new ArrayList<>();
    //下次查询数据库的status并记录
    private static List<SysDatasource> SECOND_LIST = new ArrayList<>();

    private static Boolean RESTART = false;
    //运行启动helios脚本
    private static String RUNSHELLSTRING = "sh " + System.getProperty("file.separator") + "opt" + System.getProperty("file.separator") + "seadun" + System.getProperty("file.separator") + "shell" + System.getProperty("file.separator")
            + "restartHelios.sh";

    @Override
    public void run() {
        SqlBoxContext globalSqlBoxContext = SqlBoxContext.getGlobalSqlBoxContext();
        globalSqlBoxContext.setAllowShowSQL(true);
        SysDatasource sysDatasource = new SysDatasource();
        SampleItem simpleItem = new SampleItem(sysDatasource);
        List<SysDatasource> datasourceList = sysDatasource.eFindAll(SysDatasource.class, simpleItem);
        datasourceList.forEach(item -> {
            Connection conn = null;
            try {
                Class.forName(item.getDriverClass());
                conn = DriverManager.getConnection(item.getJdbcUrl(), item.getUsername(), item.getPassword());
                if (!"0".equals(item.getStatus())) {
                    item.setStatus("0");
                    item.update(SqlOption.IGNORE_NULL);
                    RESTART = true;
                }
            } catch (Exception e) {
                //e.printStackTrace();
                try {
                    if (!"1".equals(item.getStatus())) {
                        item.setStatus("1");
                        item.update(SqlOption.IGNORE_NULL);
                        RESTART = true;
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                    log.info("更新链接状态败");
                    RESTART = true;
                }
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                        log.info("链接关闭");
                    } catch (SQLException e) {
                        log.error(e);
                    }
                }
            }
            SECOND_LIST.add(item);
        });

        if (!RESTART) {
            if (FRIST_LIST.size() == 0 || SECOND_LIST.size() == 0) {
                RESTART = true;
            } else if (FRIST_LIST.size() != SECOND_LIST.size()) {
                RESTART = true;
            } else {
                FRIST_LIST.forEach(first -> {
                    Boolean hasFirst = false;
                    for (int i = 0; i < SECOND_LIST.size(); i++) {
                        SysDatasource secound = SECOND_LIST.get(i);
                        if (first.getId().equals(secound.getId())) {
                            hasFirst = true;
                            if (!first.getStatus().equals(secound.getStatus())) {
                                RESTART = true;
                            }
                            if (!first.getJdbcUrl().equals(secound.getJdbcUrl())) {
                                RESTART = true;
                            }
                        }
                    }
                    if (!hasFirst) {
                        RESTART = true;
                    }
                });
            }
        }

        if (RESTART) {
            try {
                RunShellUtil.runScript(RUNSHELLSTRING);
                log.info("重启helios");
            } catch (Exception e) {
                log.error(e);
                log.info("重启helios失败");
            }
            RESTART = false;
        }

        SECOND_LIST.clear();
        FRIST_LIST = datasourceList;
    }

}
