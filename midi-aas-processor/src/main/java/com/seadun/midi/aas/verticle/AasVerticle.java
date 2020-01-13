package com.seadun.midi.aas.verticle;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;

import com.github.drinkjava2.jbeanbox.BeanBox;
import com.github.drinkjava2.jdbpro.SqlOption;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.github.drinkjava2.jtransactions.tinytx.TinyTxConnectionManager;
import com.seadun.midi.aas.config.DataSourceCfg;
import com.seadun.midi.aas.entity.TbLogDB;
import com.seadun.midi.aas.entity.TbLogModel;
import com.seadun.midi.aas.entity.TbResource;
import com.seadun.midi.aas.router.AppRouter;
import com.seadun.midi.aas.router.DatasourceRouter;
import com.seadun.midi.aas.router.DutyRouter;
import com.seadun.midi.aas.router.InitRouter;
import com.seadun.midi.aas.router.IpSqlBoxRouter;
import com.seadun.midi.aas.router.LogRouter;
import com.seadun.midi.aas.router.OrgRouter;
import com.seadun.midi.aas.router.PersonRouter;
import com.seadun.midi.aas.router.ResourceRouter;
import com.seadun.midi.aas.router.RoleRouter;
import com.seadun.midi.aas.router.StationRouter;
import com.seadun.midi.aas.router.UserRouter;
import com.seadun.midi.aas.util.HeaderTokenUtils;
import com.seadun.midi.aas.util.Utils;
import com.seadun.midi.aas.yml.Application;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AasVerticle extends AbstractVerticle {
    //上次记录
    static Map<String, String> recordFirst = new HashMap<String, String>();
    //第二次记录
    static Map<String, String> recordSecond = new HashMap<String, String>();
    //add
    String add = "1";
    //delete
    String delete = "2";
    //select
    String select = "3";
    //update
    String update = "4";
    static List<TbResource> tbResources = SqlBoxContext.getGlobalSqlBoxContext().eFindAll(TbResource.class);
    @Override
    public void start(Future<Void> startFuture) throws FileNotFoundException  {

        HttpServer server = vertx.createHttpServer();
        Router mainRouter = Router.router(vertx);
        mainRouter.route().handler(BodyHandler.create());
        Utils.loadYml();// 初始化数据
		SqlBoxContext ctx = new SqlBoxContext((DataSource) BeanBox.getBean(DataSourceCfg.class));
		ctx.setConnectionManager(TinyTxConnectionManager.instance());
		ctx.setAllowShowSQL(true);
		SqlBoxContext.setGlobalSqlBoxContext(ctx);

        mainRouter.route().handler(routingContext -> {
            String value = null;
            String clientIp = null;
            String headersUserCode = HeaderTokenUtils.getHeadersUserCode(routingContext);
            String headersUserName = HeaderTokenUtils.getHeadersUserNickName(routingContext);
            List<Map.Entry<String, String>> entries = routingContext.request().headers().entries();
            for (int i = 0; i < entries.size(); i++) {

                String key = entries.get(i).getKey();
                if ("Referer".equalsIgnoreCase(key)) {
                    value = entries.get(i).getValue();
                    for (int j = 0; j < 3; j++) {
                        value = value.substring(value.indexOf("/") + 1);
                    }
                    value = "/" + value;
                    if (recordFirst.size() == 0) {
                        recordFirst.put(headersUserCode, value);
                    } else if (StringUtils.isEmpty(recordFirst.get(headersUserCode))) {
                        recordFirst.put(headersUserCode, value);
                    }
                    if (!recordFirst.get(headersUserCode).equals(value)) {
                        recordSecond.put(headersUserCode, value);
                    }
                }

                if ("CLIENT-IP".equalsIgnoreCase(key)) {
                	clientIp = entries.get(i).getValue();
                }
            }

            for (int i = 0; i < tbResources.size(); i++) {
                String uri = tbResources.get(i).getUri();
                if (StringUtils.isNotEmpty(headersUserCode)) {
                    String first = recordFirst.get(headersUserCode);
                    String second = recordSecond.get(headersUserCode);
                    if (null == second) {
                        if (null != value && value.equals(uri)) {
                            recordSecond.put(headersUserCode, value);
                            log.info("开始记录模块日志");
                            insertModelLog(clientIp, headersUserCode, headersUserName, i);
                        }
                    }
                    if (StringUtils.isNotEmpty(first) && StringUtils.isNotEmpty(second) && !first.equals(second)) {
                        if (null != value && value.equals(uri)) {
                            log.info("开始记录模块日志");
                            insertModelLog(clientIp, headersUserCode, headersUserName, i);
                            recordFirst.put(headersUserCode, value);
                        }
                    }
                }
            }

            TbLogDB tbDbLog = new TbLogDB();
            String method = routingContext.request().method().toString();
            String path = routingContext.request().path();
            String operationType = "";
            String opration = "";
            //添加
            if (!path.endsWith("/page") && "POST".equals(method)) {
                //新增
                operationType = add;
                opration = "新增";
            }
            if ("DELETE".equals(method)) {
                //删除
                operationType = delete;
                opration = "删除";
            }
            if ((path.endsWith("/page") && "POST".equals(method)) || "GET".equals(method)) {
                //查询
                operationType = select;
                opration = "查询";
            }
            if ("PUT".equals(method)) {
                //修改
                operationType = update;
                opration = "修改";
            }
            tbDbLog.setClientIp(clientIp);
            tbDbLog.setCrtCode(headersUserCode);
            tbDbLog.setCrtName(headersUserName);
            tbDbLog.setDbName(null);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d = new Date();
            String str = sdf.format(d);
            tbDbLog.setLogContent(headersUserName+" 在 "+str+" 进行了 "+opration+" 操作");
            tbDbLog.setCrtTime(new Date());
            tbDbLog.setId(String.valueOf(System.currentTimeMillis()));
            tbDbLog.setOperationType(operationType);
            tbDbLog.insert(SqlOption.IGNORE_NULL);
            routingContext.next();
        });


        mainRouter.mountSubRouter("/duty", new DutyRouter(vertx).getApi());
        mainRouter.mountSubRouter("/app", new AppRouter(vertx).getApi());
        mainRouter.mountSubRouter("/log", new LogRouter(vertx).getApi());
        mainRouter.mountSubRouter("/org", new OrgRouter(vertx).getApi());
        mainRouter.mountSubRouter("/person", new PersonRouter(vertx).getApi());
        mainRouter.mountSubRouter("/resource", new ResourceRouter(vertx).getApi());
        mainRouter.mountSubRouter("/role", new RoleRouter(vertx).getApi());
        mainRouter.mountSubRouter("/station", new StationRouter(vertx).getApi());
        mainRouter.mountSubRouter("/user", new UserRouter(vertx).getApi());
        mainRouter.mountSubRouter("/datasource", new DatasourceRouter(vertx).getApi());
        mainRouter.mountSubRouter("/init", new InitRouter(vertx).getApi());
        mainRouter.mountSubRouter("/network", new IpSqlBoxRouter(vertx).getApi());
        server.requestHandler(mainRouter).listen(Application.server.getPort(), ar -> {
            if (ar.succeeded()) {
                try {
                    log.info("aas启动->>>>>>>>>>>>>>>>>>>>>>>");
                    super.start(startFuture);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                ar.cause().printStackTrace();
            }
        });
    }
    private void insertModelLog(String hostIp, String headersUserCode, String headersUserName, int i) {
        TbLogModel log = new TbLogModel();
        log.setAppCode("");
        log.setCrtCode(headersUserCode);
        log.setCrtName(headersUserName);
        log.setClientIp(hostIp);
        log.setCrtTime(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        String str = sdf.format(d);
        log.setLogContent(headersUserName +" 在："+ str + " 访问了--" + tbResources.get(i).getName());
        log.setId(String.valueOf(System.currentTimeMillis()));
        log.setModelCode(tbResources.get(i).getCode());
        log.setModelName(tbResources.get(i).getName());
        log.insert(SqlOption.IGNORE_NULL);
    }
}
