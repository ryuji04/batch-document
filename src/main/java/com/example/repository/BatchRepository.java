package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Batch;
import com.example.domain.Sample;

@Repository
public class BatchRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private final static RowMapper<Sample>SAMPLE_ROW_MAPPER=(rs,i)->{
		Sample sample=new Sample();
		sample.setRoleId(rs.getString("role_id"));
		sample.setRoleName(rs.getString("role_name"));
		sample.setRoleDescription(rs.getString("role_description"));
		return sample;
	};
	
	public List<Sample>findAll(){
		String sql="select role_id,role_name,role_description from test_csv_file ";
		
		return template.query(sql, SAMPLE_ROW_MAPPER);
	}
	
	public void insertCsv(Batch csvData) {
		String sql="insert into test_csv_file (role_id,role_name,role_description) values(:roleId,:roleName,:roleDescription)";

		
		SqlParameterSource param=new BeanPropertySqlParameterSource(csvData);
		
		
		
		template.update(sql, param);
		
	}
}
