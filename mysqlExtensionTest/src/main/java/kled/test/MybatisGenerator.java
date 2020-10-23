package kled.test;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.*;

/**
 * @author: Kled
 * @version: MybatisGenerator.java, v0.1 2020-10-04 13:02 Kled
 */
public class MybatisGenerator {

    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/mysqlExtensionTest/src/main/java/");
        gc.setAuthor("kled");
        gc.setOpen(false);
        // service 命名方式
        gc.setServiceName("%sService");
        //gc.setEntityName("%sDO");
        // service impl 命名方式
        gc.setServiceImplName("%sServiceImpl");
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setFileOverride(true);
        gc.setActiveRecord(true);
        // XML 二级缓存
        gc.setEnableCache(false);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML columList
        gc.setBaseColumnList(false);
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://172.16.5.56:3306/db1?useUnicode=true&useSSL=false&characterEncoding=utf-8");
        // dsc.setSchemaName("public");
        //dsc.setTypeConvert() 自定义数据库表字段类型转换
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("uEXsn7NZrusBIGKe");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        //pc.setModuleName(scanner("模块名"));
        pc.setParent("kled.test");
        pc.setEntity("entity");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        mpg.setPackageInfo(pc);

        // 自定义属性注入模板
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("param", "hello");
                this.setMap(map);
                // to do nothing
            }
        };

        // 自定义输出配置
        // com/baomidou/mybatis-plus-generator/3.3.2/mybatis-plus-generator-3.3.2.jar/templates/...
        // 选择模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 选择模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                String moduleName = pc.getModuleName() == null ? "" : pc.getModuleName();
                return projectPath + "/mysqlExtensionTest/src/main/resources/mapper/" + moduleName
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录");
                return false;
            }
        });
        */
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置自定义输出模板
        TemplateConfig templateConfig = new TemplateConfig();
        // 放置自己项目的 src/main/resources/template 目录下, 默认名称一下可以不配置，也可以自定义模板名称
        // 指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        templateConfig.setEntity("templates/MyEntity.java");
        //templateConfig.setService();
        //templateConfig.setController();

        //在项目中不生成XML(默认mapper文件夹下xml),调整生成至根目录Resource
        templateConfig.disable(TemplateType.XML);
        mpg.setTemplate(templateConfig);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        //表名称的生成策略，下划线至驼峰
        strategy.setNaming(NamingStrategy.underline_to_camel);
        //表字段的生成策略，下划线至驼峰
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //strategy.setSuperEntityClass("cn.com.bluemoon.demo.entity");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
        //strategy.setSuperControllerClass("cn.com.bluemoon.demo.controller");
        // 写于父类中的公共字段
        //strategy.setSuperEntityColumns("id");
        //逆向工程的表
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        //排除的表
        //strategy.setExclude("");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);

        mpg.execute();
    }
}
