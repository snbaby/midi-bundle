package com.seadun.midi.aas.yml;

import java.util.List;

public class Server {
	private int port;
	
	private List<Jdbc> datasources;
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public List<Jdbc> getDatasources() {
		return datasources;
	}

	public void setDatasources(List<Jdbc> datasources) {
		this.datasources = datasources;
	}

}
