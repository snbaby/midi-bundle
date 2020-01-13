package com.seadun.midi.aas.annotation;

import static com.github.drinkjava2.jbeanbox.JBEANBOX.inject;

import javax.sql.DataSource;

import com.github.drinkjava2.jbeanbox.BeanBox;
import com.github.drinkjava2.jtransactions.tinytx.TinyTx;
import com.seadun.midi.aas.config.DataSourceCfg;

public class TxBox extends BeanBox {
	{
		this.injectConstruct(TinyTx.class, DataSource.class, inject(DataSourceCfg.class));
	}
}
