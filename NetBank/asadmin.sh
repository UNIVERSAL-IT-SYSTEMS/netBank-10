create-auth-realm --classname com.sun.enterprise.security.auth.realm.jdbc.JDBCRealm --property jaas-context=jdbcRealm:datasource-jndi=jdbc/netBank:user-table=user_table:user-name-column=loginname:password-column=password:group-table=group_table:group-name-column=role:group-table-user-name-column=loginname:digestrealm-password-enc-algorithm=SHA-256 netBank

create-jdbc-connection-pool --datasourceclassname org.apache.derby.jdbc.ClientDataSource40 --restype javax.sql.DataSource --property User=netBank:Password=netBank:DatabaseName=netBank:ServerName=localhost netBank

create-jdbc-resource --connectionpoolid netBank jdbc/netBank

