/**
 *
 */
package com.seadun.midi.aas.router;

import com.github.drinkjava2.jbeanbox.BeanBox;
import com.seadun.midi.aas.response.MidiResponse;
import com.seadun.midi.aas.service.IpService;
import com.seadun.midi.aas.util.Utils;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class IpSqlBoxRouter {
	public IpService ipService = BeanBox.getBean(IpService.class);
	private Router router;

    public IpSqlBoxRouter(Vertx vertx){
        this.router = Router.router(vertx);
    }

	public Router getApi() {
		//获取ip
    	router.get("/").handler(this::getLocalIp);
    	router.put("/:name").handler(this::updateIp);
		return this.router;
	}


	private void updateIp(RoutingContext routingContext) {
		try {
			ipService.updateIp(routingContext);
			MidiResponse.success(routingContext);
		} catch (Exception e) {
			log.error("修改ip出错",e);
			e.printStackTrace();
			Utils.processException(e, routingContext);
		}
	}
	private void getLocalIp(RoutingContext routingContext) {
		try {
			List map = ipService.getLocalIp();
			MidiResponse.success(routingContext,map);
		} catch (Exception e) {
			log.error("查询网络错误",e);
			e.printStackTrace();
			Utils.processException(e, routingContext);
		}
	}

}
