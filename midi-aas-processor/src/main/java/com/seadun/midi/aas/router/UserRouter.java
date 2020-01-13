/**
 *
 */
package com.seadun.midi.aas.router;

import com.github.drinkjava2.jbeanbox.BeanBox;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.seadun.midi.aas.entity.Page;
import com.seadun.midi.aas.entity.TbUser;
import com.seadun.midi.aas.response.MidiResponse;
import com.seadun.midi.aas.service.UserService;
import com.seadun.midi.aas.util.Utils;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class UserRouter {
    private UserService userService = BeanBox.getBean(UserService.class);
    private Router router;

    public UserRouter(Vertx vertx) {
        this.router = Router.router(vertx);
    }

    public Router getApi() {
        //创建用户
//        router.post("/").blockingHandler(this::insertUser, false);
        //用户分页查询 带模糊查询
        router.post("/page").blockingHandler(this::findUserPageList, false);
        //获取用户详细信息
        router.get("/:code/detail").blockingHandler(this::findByUserCode, false);
        //修改用户密码(管理员重置密码)
//        router.put("/:code/pwd").blockingHandler(this::updateUser, false);
        //修改用户个人密码
//        router.put("/pwd").blockingHandler(this::updateSelfUser, false);
        //获取用户个人详细信息
        router.get("/detail").blockingHandler(this::findSelfUser, false);
        //获取用户资源分页查询
        router.post("/resource/page").blockingHandler(this::findUserResourcePageList, false);
        //根据code删除User
        router.delete("/:code").blockingHandler(this::deleteUserByCode, false);
        //修改用户状态
        router.put("/:code/status").blockingHandler(this::updateStatus, false);
        //获取用户组织信息
        router.post("/org").blockingHandler(this::findUserOrgPageList, false);
        //获取用户角色信息
        router.post("/role").blockingHandler(this::findUserRolePageList, false);
        //获取用户组织岗位信息
        router.post("/org-station").blockingHandler(this::findUserOrgStationPageList, false);
        //外部获取用户组织信息
        router.post("/userCode/org").blockingHandler(this::findByUserCodeOrgPageList, false);
        return this.router;
    }


    public void findByUserCode(RoutingContext routingContext) {
        try {
            List byUserCode = userService.findByUserCode(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, byUserCode);
        } catch (Exception e) {
            log.error("获取用户详细信息错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    /**
     * 获取用户个人详细信息
     * @param routingContext
     */
    public void findSelfUser(RoutingContext routingContext) {
        try {
            TbUser tbUser = userService.findSelfUser(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, tbUser);
        } catch (Exception e) {
            log.error("获取用户个人详细信息错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

//    public void insertUser(RoutingContext routingContext) {
//        try {
//            userService.insertUser(routingContext);
//            MidiResponse.success(routingContext);
//        } catch (Exception e) {
//            log.error("错误",e);
//            e.printStackTrace();
//            Utils.processException(e, routingContext);
//        }
//    }

//    public void updateUser(RoutingContext routingContext) {
//        try {
//            userService.updateUser(routingContext);
//            MidiResponse.success(routingContext);
//        } catch (Exception e) {
//            log.error("错误",e);
//            e.printStackTrace();
//            Utils.processException(e, routingContext);
//        }
//
//    }

    public void updateStatus(RoutingContext routingContext) {
        try {
            userService.updateStatus(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("修改用户状态错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }

    }

//    public void updateSelfUser(RoutingContext routingContext) {
//        try {
//            userService.updateSelfUser(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
//            MidiResponse.success(routingContext);
//        } catch (Exception e) {
//            log.error("错误",e);
//            e.printStackTrace();
//            Utils.processException(e, routingContext);
//        }
//
//    }

    public void deleteUserByCode(RoutingContext routingContext) {
        try {
            userService.deleteUserByCode(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("删除错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    public void findUserPageList(RoutingContext routingContext) {
        try {
            Page list = userService.findUserPageList(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, list);
        } catch (Exception e) {
            log.error("用户分页查询错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }
    public void findUserResourcePageList(RoutingContext routingContext) {
        try {
            Page list = userService.findUserResourcePageList(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, list);
        } catch (Exception e) {
            log.error("获取用户资源分页查询错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }
    public void findByUserCodeOrgPageList(RoutingContext routingContext) {
        try {
            Page list = userService.findByUserCodeOrgPageList(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, list);
        } catch (Exception e) {
            log.error("外部获取用户组织信息错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    public void findUserOrgPageList(RoutingContext routingContext) {
        try {
            Page list = userService.findUserOrgPageList(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, list);
        } catch (Exception e) {
            log.error("获取用户组织信息错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    public void findUserRolePageList(RoutingContext routingContext) {
        try {
            Page list = userService.findUserRolePageList(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, list);
        } catch (Exception e) {
            log.error("获取用户角色信息错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }
    public void findUserOrgStationPageList(RoutingContext routingContext) {
        try {
            Page list = userService.findUserOrgStationPageList(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext, list);
        } catch (Exception e) {
            log.error("获取用户组织岗位信息错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }


    public void respSuccess(RoutingContext routingContext, JsonObject jsonObject) {
        routingContext
                .response()
                .putHeader("content-type", "application/json; charset=utf-8")
                .setStatusCode(200)
                .end(jsonObject.encode());
    }

    public void respError(RoutingContext routingContext, Integer errorCode, JsonObject jsonObject) {
        routingContext
                .response()
                .putHeader("content-type", "application/json; charset=utf-8")
                .setStatusCode(errorCode)
                .end(jsonObject.encode());
    }
}
