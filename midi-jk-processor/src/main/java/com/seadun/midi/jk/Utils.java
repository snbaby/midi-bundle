package com.seadun.midi.jk;

import com.google.common.base.Preconditions;
import lombok.extern.log4j.Log4j2;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
@Log4j2
public class Utils {

	public static void loadYml() throws FileNotFoundException {
		Yaml yaml = new Yaml();
		String ymlPath = System.getProperty("user.dir") + System.getProperty("file.separator") + "application-server.yml";
		File fServerFile = new File(ymlPath);
		if (fServerFile.exists()) {
			yaml.loadAs(new FileInputStream(fServerFile), Application.class);
//			log.debug("加载外部配置文件 server----->" + ymlPath);
			log.info("加载外部配置文件 server----->" + ymlPath);
		} else {
			yaml.loadAs(VerticleJK.class.getClassLoader().getResourceAsStream("application-server.yml"),
					Application.class);
//			log.debug("加载内部配置文件----->application-server.yml");
			log.info("加载内部配置文件----->application-server.yml");
		}
		@SuppressWarnings("unused")
		Application application = yaml.loadAs(
				VerticleJK.class.getClassLoader().getResourceAsStream("application-server.yml"), Application.class);
		Preconditions.checkNotNull(Application.server, "yml 配置 server节点缺失");
		Preconditions.checkState(Application.server.getPort() > 1000 && Application.server.getPort() < 65536,
				"yml 配置server.port:%s 错误,server.port允许范围为1000-65536", Application.server.getPort());
		Preconditions.checkNotNull(Application.server.getDatasources(), "yml 配置server.datasources配置缺失");
		for (int i = 0; i < Application.server.getDatasources().size(); i++) {
			Jdbc jdbc = Application.server.getDatasources().get(i);
			Preconditions.checkNotNull(jdbc, "yml 配置server.datasources[%s]配置缺失", i);
			Preconditions.checkNotNull(jdbc.getName(), "yml 配置server.datasources[%s].name 配置缺失", i);
			Preconditions.checkNotNull(jdbc.getUrl(), "yml 配置server.datasources[%s].url配置缺失", i);
			Preconditions.checkNotNull(jdbc.getDriverClassName(), "yml 配置server.datasources[%s].driverClassName配置缺失",
					i);
			Preconditions.checkNotNull(jdbc.getUserName(), "yml 配置server.datasources[%s].userName配置缺失", i);
			Preconditions.checkNotNull(jdbc.getPassword(), "yml 配置server.datasources[%s].password配置缺失", i);
		}
//		log.info("yml 配置加载server成功----->" + JSON.toJSONString(Application.server));
	}

}
