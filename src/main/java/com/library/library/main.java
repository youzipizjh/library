package com.library.library;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class main {
    public static void main(String[] args) {
        //创建generator对象
        AutoGenerator autoGenerator=new AutoGenerator();
        //数据源
        DataSourceConfig dataSourceConfig=new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.POSTGRE_SQL);
        dataSourceConfig.setUrl("jdbc:postgresql://124.71.193.191:26000/mydatabase?ApplicationName=app1");
        dataSourceConfig.setUsername("zjh");
        dataSourceConfig.setPassword("gauss@123");
        dataSourceConfig.setDriverName("org.postgresql.Driver");
        dataSourceConfig.setSchemaName("library");
        //全局配置
        autoGenerator.setDataSource(dataSourceConfig);
        GlobalConfig globalConfig=new GlobalConfig();
        globalConfig.setOutputDir(System.getProperty("user.dir")+"/src/main/java");
        globalConfig.setOpen(false);
        globalConfig.setAuthor("杨宇辰");
        globalConfig.setServiceName("%sService");
        autoGenerator.setGlobalConfig(globalConfig);
        //包信息
        PackageConfig packageConfig=new PackageConfig();
        packageConfig.setParent("com.library.library");
        packageConfig.setModuleName("");
        packageConfig.setController("controller");
        packageConfig.setService("service");
        packageConfig.setServiceImpl("service.impl");
        packageConfig.setMapper("mapper");
        packageConfig.setEntity("entity");
        autoGenerator.setPackageInfo(packageConfig);
        //配置策略
        StrategyConfig strategyConfig=new StrategyConfig();
        strategyConfig.setEntityLombokModel(true);
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        autoGenerator.setStrategy(strategyConfig);

        autoGenerator.execute();
    }
}