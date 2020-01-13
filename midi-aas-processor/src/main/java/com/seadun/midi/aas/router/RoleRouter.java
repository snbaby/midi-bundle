/**
 *
 */
package com.seadun.midi.aas.router;

import com.github.drinkjava2.jbeanbox.BeanBox;
import com.github.drinkjava2.jsqlbox.SqlBoxContext;
import com.seadun.midi.aas.entity.Page;
import com.seadun.midi.aas.response.MidiResponse;
import com.seadun.midi.aas.service.RoleService;
import com.seadun.midi.aas.util.Utils;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class RoleRouter extends AbstractVerticle {
    private RoleService roleService = BeanBox.getBean(RoleService.class);

    private Router router;

    public RoleRouter(Vertx vertx) {
        this.router = Router.router(vertx);
    }

    public Router getApi() {
        //创建角色
        router.post("/").blockingHandler(this::insertRole, false);
        //角色分页查询
        router.post("/page").blockingHandler(this::findRolePageList, false);
        //删除角色
        router.delete("/:code").blockingHandler(this::deleteRoleByCode, false);
        //角色中添加用户
        router.post("/:roleCode/user-acl").blockingHandler(this::insertRoleUser, false);
        //修改角色信息
        router.put("/:code").blockingHandler(this::updateRole, false);
        //删除角色中用户
        router.delete("/:roleCode/user-acl").blockingHandler(this::deleteRoleUser, false);
        //角色中用户分页查询
        router.post("/:roleCode/user-acl/page").blockingHandler(this::findRoleUserPageList, false);
      //角色中用户分页查询
        router.post("/:roleCode/user-acl/_page").blockingHandler(this::findNotInRoleUserPageList, false);
        //角色中添加资源
        router.post("/:roleCode/resource-acl").blockingHandler(this::insertRoleResource, false);
        //角色中批量添加资源
        router.post("/:roleCode/resources-acl").blockingHandler(this::insertRoleResources, false);
        //删除角色中资源
        router.delete("/:roleCode/resource-acl").blockingHandler(this::deleteRoleResource, false);
        //角色中资源分页查询
        router.post("/:roleCode/resource-acl/page").blockingHandler(this::findRoleResourcePageList, false);
        return this.router;
    }


    public void updateRole(RoutingContext routingContext) {
        try {
            roleService.updateRole(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("修改角色信息错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    public void findRoleResourcePageList(RoutingContext routingContext) {
        try {
            Page roleResourcePageList = roleService.findRoleResourcePageList(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext,roleResourcePageList);
        } catch (Exception e) {
            log.error("角色中资源分页查询错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    public void findRoleUserPageList(RoutingContext routingContext) {
        try {
            Page list = roleService.findRoleUserPageList(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext,list);
        } catch (Exception e) {
            log.error("角色中用户分页查询错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }
    
    public void findNotInRoleUserPageList(RoutingContext routingContext) {
        try {
            Page list = roleService.findNotInRoleUserPageList(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext,list);
        } catch (Exception e) {
            log.error("非当前角色中所有用户分页查询错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    public void deleteRoleResource(RoutingContext routingContext) {
        try {
            roleService.deleteRoleResource(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("删除角色中资源错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    public void deleteRoleUser(RoutingContext routingContext) {
        try {
            roleService.deleteRoleUser(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("删除角色中用户错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    public void insertRoleResource(RoutingContext routingContext) {
        try {
            roleService.insertRoleResource(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("角色中添加资源错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }
    public void insertRoleResources(RoutingContext routingContext) {
        try {
            roleService.insertRoleResources(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("角色中批量添加资源错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    public void insertRoleUser(RoutingContext routingContext) {
        try {
            roleService.insertRoleUser(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("角色中添加用户错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    public void insertRole(RoutingContext routingContext) {
        try {
            roleService.insertRole(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("创建角色错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }


    public void deleteRoleByCode(RoutingContext routingContext) {
        try {
            roleService.deleteRoleByCode(routingContext);
            MidiResponse.success(routingContext);
        } catch (Exception e) {
            log.error("删除角色错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

    public void findRolePageList(RoutingContext routingContext) {
        try {
            Page rolePageList = roleService.findRolePageList(routingContext, SqlBoxContext.getGlobalSqlBoxContext());
            MidiResponse.success(routingContext,rolePageList);
        } catch (Exception e) {
            log.error("角色分页查询错误",e);
            e.printStackTrace();
            Utils.processException(e, routingContext);
        }
    }

}
