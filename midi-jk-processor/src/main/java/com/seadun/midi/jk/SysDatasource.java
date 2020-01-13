package com.seadun.midi.jk;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jdialects.annotation.jpa.Table;
import com.github.drinkjava2.jsqlbox.ActiveRecord;

/**
 * sys_datasource实体类
 * 
 * @author 
 *
 */
@Table(name = "sys_datasource")
public class SysDatasource extends ActiveRecord<SysDatasource> {
	/**主键*/
	@Id
	private String id;
	/**编码*/
	private String name; 
	/**名称*/
	private String title; 
	/***/
	private String description;
	/***/
	@Column(name = "db_type")
	private String dbType;
	/***/
	@Column(name = "max_active")
	private Integer maxActive;
	/***/
	@Column(name = "auto_commit")
	private Integer autoCommit; 
	/***/
	@Column(name = "read_only")
	private Integer readOnly; 
	/***/
	private String catalog;
	/***/
	private String username; 
	/***/
	private String password; 
	/***/
	@Column(name = "jdbc_url")
	private String jdbcUrl; 
	/***/
	@Column(name = "driver_class")
	private String driverClass; 
	/***/
	@Column(name = "initial_size")
	private Integer initialSize; 
	/***/
	@Column(name = "max_wait")
	private Integer maxWait; 
	/***/
	@Column(name = "validation_query")
	private String validationQuery;
	/**是否可以使用 0 true 1 false*/
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 实例化
	 */
	public SysDatasource() {
		super();
	}

	
	/**
	 * 获取id
	 * 
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置id
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 获取name
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置name
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 获取title
	 * 
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置title
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * 获取description
	 * 
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置description
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * 获取dbType
	 * 
	 * @return
	 */
	public String getDbType() {
		return dbType;
	}

	/**
	 * 设置dbType
	 * 
	 * @param dbType
	 */
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	
	/**
	 * 获取maxActive
	 * 
	 * @return
	 */
	public Integer getMaxActive() {
		return maxActive;
	}

	/**
	 * 设置maxActive
	 * 
	 * @param maxActive
	 */
	public void setMaxActive(Integer maxActive) {
		this.maxActive = maxActive;
	}
	
	/**
	 * 获取autoCommit
	 * 
	 * @return
	 */
	public Integer getAutoCommit() {
		return autoCommit;
	}

	/**
	 * 设置autoCommit
	 * 
	 * @param autoCommit
	 */
	public void setAutoCommit(Integer autoCommit) {
		this.autoCommit = autoCommit;
	}
	
	/**
	 * 获取readOnly
	 * 
	 * @return
	 */
	public Integer getReadOnly() {
		return readOnly;
	}

	/**
	 * 设置readOnly
	 * 
	 * @param readOnly
	 */
	public void setReadOnly(Integer readOnly) {
		this.readOnly = readOnly;
	}
	
	/**
	 * 获取catalog
	 * 
	 * @return
	 */
	public String getCatalog() {
		return catalog;
	}

	/**
	 * 设置catalog
	 * 
	 * @param catalog
	 */
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	
	/**
	 * 获取username
	 * 
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置username
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * 获取password
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置password
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * 获取jdbcUrl
	 * 
	 * @return
	 */
	public String getJdbcUrl() {
		return jdbcUrl;
	}

	/**
	 * 设置jdbcUrl
	 * 
	 * @param jdbcUrl
	 */
	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}
	
	/**
	 * 获取driverClass
	 * 
	 * @return
	 */
	public String getDriverClass() {
		return driverClass;
	}

	/**
	 * 设置driverClass
	 * 
	 * @param driverClass
	 */
	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}
	
	/**
	 * 获取initialSize
	 * 
	 * @return
	 */
	public Integer getInitialSize() {
		return initialSize;
	}

	/**
	 * 设置initialSize
	 * 
	 * @param initialSize
	 */
	public void setInitialSize(Integer initialSize) {
		this.initialSize = initialSize;
	}
	
	/**
	 * 获取maxWait
	 * 
	 * @return
	 */
	public Integer getMaxWait() {
		return maxWait;
	}

	/**
	 * 设置maxWait
	 * 
	 * @param maxWait
	 */
	public void setMaxWait(Integer maxWait) {
		this.maxWait = maxWait;
	}
	
	/**
	 * 获取validationQuery
	 * 
	 * @return
	 */
	public String getValidationQuery() {
		return validationQuery;
	}

	/**
	 * 设置validationQuery
	 * 
	 * @param validationQuery
	 */
	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}

	@Override
	public String toString() {
		return "SysDatasource [id=" + id + " , name=" + name + " , title=" + title + " , description=" + description + " , dbType=" + dbType + " , maxActive=" + maxActive + " , autoCommit=" + autoCommit + " , readOnly=" + readOnly + " , catalog=" + catalog + " , username=" + username + " , password=" + password + " , jdbcUrl=" + jdbcUrl + " , driverClass=" + driverClass + " , initialSize=" + initialSize + " , maxWait=" + maxWait + " , validationQuery=" + validationQuery + "  ]";
	
	}
	
	
}
