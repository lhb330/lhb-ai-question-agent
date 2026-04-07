package com.lhb.typeHandlers;

import com.pgvector.PGvector;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.postgresql.util.PGobject;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// 自定义 float[] 转 PostgreSQL vector 类型处理器
@MappedTypes(float[].class)
@MappedJdbcTypes(JdbcType.OTHER)
public class FloatArrayToVectorTypeHandler extends BaseTypeHandler<float[]> {
    // 写入数据库：float[] → vector
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, float[] parameter, JdbcType jdbcType) throws SQLException {
        PGvector vector = new PGvector(parameter);
        PGobject pgo = new PGobject();
        pgo.setType("vector");
        pgo.setValue(vector.toString());
        ps.setObject(i, pgo);
    }

    // 读取数据库：PGobject → float[]  【这里是修复关键！】
    @Override
    public float[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return toFloatArray(rs.getObject(columnName));
    }

    @Override
    public float[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return toFloatArray(rs.getObject(columnIndex));
    }

    @Override
    public float[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return toFloatArray(cs.getObject(columnIndex));
    }

    // 核心转换方法
    private float[] toFloatArray(Object obj) throws SQLException {
        if (obj == null) {
            return null;
        }

        // 关键：数据库返回的是 PGobject → 转 PGvector → 转 float[]
        if (obj instanceof PGobject pgObj) {
            PGvector vector = new PGvector(pgObj.getValue());
            return vector.toArray();
        }

        throw new IllegalArgumentException("不支持的vector类型: " + obj.getClass());
    }
}