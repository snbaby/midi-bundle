package com.seadun.midi.equipment.annotation;

import com.github.drinkjava2.jbeanbox.BeanBox;
import com.github.drinkjava2.jtransactions.tinytx.TinyTx;
import com.seadun.midi.equipment.config.DataSourceCfg;

import javax.sql.DataSource;

import static com.github.drinkjava2.jbeanbox.JBEANBOX.inject;

public class TxBox extends BeanBox {
	{
		this.injectConstruct(TinyTx.class, DataSource.class, inject(DataSourceCfg.class));
	}
}
