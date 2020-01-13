package com.seadun.midi.aas.entity;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jdialects.annotation.jpa.Table;
import com.github.drinkjava2.jsqlbox.ActiveRecord;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;

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
	 * 实例化
	 * 
	 * @param obj
	 */
	public SysDatasource(JsonObject obj) {
		this();
		if (obj.getValue("id") instanceof Number) {
			this.setId((String) (obj.getValue("id")));
		}
		if (obj.getValue("name") instanceof String) {
			this.setName((String) obj.getValue("name"));
		}
		if (obj.getValue("title") instanceof String) {
			this.setTitle((String) obj.getValue("title"));
		}
		if (obj.getValue("description") instanceof String) {
			this.setDescription((String) obj.getValue("description"));
		}
		if (obj.getValue("dbType") instanceof String) {
			this.setDbType((String) obj.getValue("dbType"));
		}
		if (obj.getValue("status") instanceof String) {
			this.setStatus((String) obj.getValue("status"));
		}
		if (obj.getValue("maxActive") instanceof Number) {
			this.setMaxActive(((Number) obj.getValue("maxActive")).intValue());
		}
		if (obj.getValue("autoCommit") instanceof Number) {
			this.setAutoCommit(((Number) obj.getValue("autoCommit")).intValue());
		}
		if (obj.getValue("readOnly") instanceof Number) {
			this.setReadOnly(((Number) obj.getValue("readOnly")).intValue());
		}
		if (obj.getValue("catalog") instanceof String) {
			this.setCatalog((String) obj.getValue("catalog"));
		}
		if (obj.getValue("username") instanceof String) {
			this.setUsername((String) obj.getValue("username"));
		}
		if (obj.getValue("password") instanceof String) {
			this.setPassword((String) obj.getValue("password"));
		}
		if (obj.getValue("jdbcUrl") instanceof String) {
			this.setJdbcUrl((String) obj.getValue("jdbcUrl"));
		}
		if (obj.getValue("driverClass") instanceof String) {
			this.setDriverClass((String) obj.getValue("driverClass"));
		}
		if (obj.getValue("initialSize") instanceof Number) {
			this.setInitialSize(((Number) obj.getValue("initialSize")).intValue());
		}
		if (obj.getValue("maxWait") instanceof Number) {
			this.setMaxWait(((Number) obj.getValue("maxWait")).intValue());
		}
		if (obj.getValue("validationQuery") instanceof String) {
			this.setValidationQuery((String) obj.getValue("validationQuery"));
		}
	}
	/**
	 * 实例化
	 * 
	 * @param params
	 */
	public SysDatasource(MultiMap params) {
		this();
		this.setId(params.get("id"));
		this.setName(params.get("name"));
		this.setStatus(params.get("status"));
		this.setTitle(params.get("title"));
		this.setDescription(params.get("description"));
		this.setDbType(params.get("dbType"));
		this.setMaxActive(new Integer(params.get("maxActive")));
		this.setAutoCommit(new Integer(params.get("autoCommit")));
		this.setReadOnly(new Integer(params.get("readOnly")));
		this.setCatalog(params.get("catalog"));
		this.setUsername(params.get("username"));
		this.setPassword(params.get("password"));
		this.setJdbcUrl(params.get("jdbcUrl"));
		this.setDriverClass(params.get("driverClass"));
		this.setInitialSize(new Integer(params.get("initialSize")));
		this.setMaxWait(new Integer(params.get("maxWait")));
		this.setValidationQuery(params.get("validationQuery"));
	}
	/**
	 * 将当前对象转换为JsonObject
	 * 
	 * @return
	 */
	public JsonObject toJson() {
		JsonObject result = new JsonObject();
		if (this.getId() != null) {
			result.put("id",this.getId());
		}
		if (this.getName() != null) {
			result.put("name",this.getName());
		}
		if (this.getTitle() != null) {
			result.put("title",this.getTitle());
		}
		if (this.getDescription() != null) {
			result.put("description",this.getDescription());
		}
		if (this.getDbType() != null) {
			result.put("dbType",this.getDbType());
		}
		if (this.getMaxActive() != null) {
			result.put("maxActive",this.getMaxActive());
		}
		if (this.getAutoCommit() != null) {
			result.put("autoCommit",this.getAutoCommit());
		}
		if (this.getReadOnly() != null) {
			result.put("readOnly",this.getReadOnly());
		}
		if (this.getCatalog() != null) {
			result.put("catalog",this.getCatalog());
		}
		if (this.getUsername() != null) {
			result.put("username",this.getUsername());
		}
		if (this.getPassword() != null) {
			result.put("password",this.getPassword());
		}
		if (this.getJdbcUrl() != null) {
			result.put("jdbcUrl",this.getJdbcUrl());
		}
		if (this.getDriverClass() != null) {
			result.put("driverClass",this.getDriverClass());
		}
		if (this.getInitialSize() != null) {
			result.put("initialSize",this.getInitialSize());
		}
		if (this.getMaxWait() != null) {
			result.put("maxWait",this.getMaxWait());
		}
		if (this.getValidationQuery() != null) {
			result.put("validationQuery",this.getValidationQuery());
		}
		return result;
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
