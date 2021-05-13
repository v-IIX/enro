package edu.lxq.enro.config;

import java.util.Scanner;

import javax.sql.DataSource;

import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

@Configuration
public class Generator {
	public Generator() {
		AutoGenerator mpg= new AutoGenerator();
		
		GlobalConfig gc = new GlobalConfig();
		String projectPath = System.getProperty("user.dir");
	  gc.setOutputDir(projectPath + "/src/main/java");
	  gc.setAuthor("viix");
	  gc.setOpen(false);
	  gc.setFileOverride(false);
	  gc.setIdType(IdType.INPUT);
	  gc.setDateType(DateType.ONLY_DATE);
	  gc.setServiceName("%sService");
	  mpg.setGlobalConfig(gc);
	  
	  DataSourceConfig dsc = new DataSourceConfig();
	  dsc.setUrl("jdbc:mysql://localhost/shiro?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8");
	  dsc.setDriverName("com.mysql.cj.jdbc.Driver");
	  dsc.setUsername("root");
	  dsc.setPassword("lenovoroot");
	  mpg.setDataSource(dsc);
	  
	  PackageConfig pc = new PackageConfig();
	  pc.setParent("edu.lxq.enro");
	  pc.setModuleName("agg");
	  pc.setEntity("ag_entity");
	  pc.setMapper("ag_mapper");
	  pc.setService("ag_service");
	  pc.setController("ag_controller");
	  mpg.setPackageInfo(pc);
	  
	  StrategyConfig strategy = new StrategyConfig();
	  strategy.setNaming(NamingStrategy.underline_to_camel);
	  strategy.setColumnNaming(NamingStrategy.underline_to_camel);
	  strategy.setEntityLombokModel(true);
	  strategy.setRestControllerStyle(true);
	  strategy.setControllerMappingHyphenStyle(true);
	  strategy.setTablePrefix(pc.getModuleName() + "_");
	  strategy.setInclude("permission,role_permission");
	  mpg.setStrategy(strategy);
	  
	  mpg.execute();
	}
}
