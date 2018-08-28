package org.swat.jpa.multitenant;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class MultiTenantDataSource implements DataSource {
    private final DataSource globalDataSource;

    public MultiTenantDataSource(DataSource globalDataSource) {
        this.globalDataSource = globalDataSource;
    }

    protected DataSource resolveDataSource() {
        if (TenantContext.getTenantId() == null) {
            return globalDataSource;
        }
        //Here we can return Tenant specific Data Source
        return globalDataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return resolveDataSource().getConnection();
    }

    @Override
    public Connection getConnection(String s, String s1) throws SQLException {
        return resolveDataSource().getConnection(s, s1);
    }

    @Override
    public <T> T unwrap(Class<T> aClass) throws SQLException {
        return resolveDataSource().unwrap(aClass);
    }

    @Override
    public boolean isWrapperFor(Class<?> aClass) throws SQLException {
        return resolveDataSource().isWrapperFor(aClass);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return resolveDataSource().getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter printWriter) throws SQLException {
        resolveDataSource().setLogWriter(printWriter);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return resolveDataSource().getLoginTimeout();
    }

    @Override
    public void setLoginTimeout(int i) throws SQLException {
        resolveDataSource().setLoginTimeout(i);
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return resolveDataSource().getParentLogger();
    }
}
