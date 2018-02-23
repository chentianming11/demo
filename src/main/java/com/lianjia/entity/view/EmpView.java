package com.lianjia.entity.view;


import com.lianjia.entity.Emp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

@EqualsAndHashCode(callSuper=false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "empindex",type = "emp")
public class EmpView extends Emp {

	private static final long serialVersionUID = -8627959366921505919L;
	@Field(index= FieldIndex.analyzed,analyzer="ik",store=true,searchAnalyzer="ik",type = FieldType.String)
	private String deptName;
	@Field(index= FieldIndex.analyzed,analyzer="ik",store=true,searchAnalyzer="ik",type = FieldType.String)
	private String jobName;
	//测试返回上次修改的位置
}
