package cn.huanzi.qch.baseadmin.adas.service;

import cn.huanzi.qch.baseadmin.adas.entity.RemoteLoginInfo;
import cn.hutool.extra.ssh.JschUtil;
import cn.hutool.extra.ssh.Sftp;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;


@Component
public class SFTPUtils {

    /**
     * put()：      文件上传
     * get()：      文件下载
     * cd()：       进入指定目录
     * ls()：       得到指定目录下的文件列表
     * rename()：   重命名指定文件或目录
     * rm()：       删除指定文件
     * mkdir()：    创建目录
     * rmdir()：    删除目录
     */


    Log log = LogFactory.get();


    String targer_Dir = System.getProperty("user.dir") + "/temp/generatorScene";

    /**
     * 文件下载：
     *
     * @param  scenario_editor_id, choice_laws, select_case
     * @throws SftpException
     */
    public String downXoscFile(String scenario_editor_id,String choice_laws,String select_case) throws Exception {

        Long begin = System.currentTimeMillis();

        Sftp sftp = JschUtil.createSftp(RemoteLoginInfo.Source_sshHost, RemoteLoginInfo.Source_sshPort, RemoteLoginInfo.Source_sshUser, RemoteLoginInfo.Source_sshPass);
        ChannelSftp client = sftp.getClient();

        String xoscFile = String.format("%s/%s/%s", RemoteLoginInfo.source_NFS_Dir, scenario_editor_id, scenario_editor_id + ".xosc");
      //  String jsonFile = String.format("%s/%s/%s", RemoteLoginInfo.source_NFS_Dir, scenario_editor_id, scenario_editor_id + ".json");
      //  String paramFile = String.format("%s/%s/%s", RemoteLoginInfo.source_NFS_Dir, scenario_editor_id, scenario_editor_id + ".param");



        String rename_xoscFile = String.format("%s/%s", targer_Dir, select_case + ".xosc");
      //  String rename_jsonFile = String.format("%s/%s", targer_Dir, select_case + ".json");
     //   String rename_paramFile = String.format("%s/%s",targer_Dir, select_case + ".param");

        log.info("xoscFile:{}",xoscFile);

        try {

            File file = new File(targer_Dir);
            if(!file.exists()){
                file.mkdir();
            }

            client.get(xoscFile, rename_xoscFile);
         //   client.get(jsonFile, rename_jsonFile);
         //   client.get(paramFile, rename_paramFile);

            log.info("xoscFile正下载的文件:{}",xoscFile);
            Long end = System.currentTimeMillis();
            log.info("下载文件耗时:{} 毫秒", (end - begin));

            this.uploadXoscFile(scenario_editor_id,choice_laws,select_case);

        } catch (Exception e) {
            log.info("directory is not exist");
            client.disconnect();
            client.quit();
            sftp.close();
            throw new Exception();
        } finally {
            client.disconnect();
            client.quit();
            sftp.close();
        }



        return scenario_editor_id;

    }


    /**
     * 文件上传
     *
     * @param  scenario_editor_id, choice_laws, select_case
     * @throws SftpException
     */
    public String uploadXoscFile(String scenario_editor_id, String choice_laws, String select_case) throws Exception {

        Long begin = System.currentTimeMillis();
        Sftp sftp = JschUtil.createSftp(RemoteLoginInfo.Targer_sshHost, RemoteLoginInfo.Targer_sshPort, RemoteLoginInfo.Targer_sshUser, RemoteLoginInfo.Targer_sshPass);
        ChannelSftp client = sftp.getClient();

        //从源拷贝
        String source_xoscFile_old = String.format("%s/%s", targer_Dir, select_case + ".xosc");
     //   String source_jsonFile_old = String.format("%s/%s", targer_Dir, select_case + ".json");
     //   String source_paramFile_old = String.format("%s/%s", targer_Dir, select_case + ".param");

        //目标：
        String targer_xoscFile_new = String.format("%s/%s/%s/%s", RemoteLoginInfo.Targer_NFS_Dir,choice_laws,select_case, select_case + ".xosc");
      //  String targer_jsonFile_new = String.format("%s/%s/%s/%s", RemoteLoginInfo.Targer_NFS_Dir,choice_laws,select_case, select_case + ".json");
      //  String targer_paramFile_new = String.format("%s/%s/%s/%s", RemoteLoginInfo.Targer_NFS_Dir,choice_laws,select_case, select_case + ".param");

        //去目标地
        String targer_choice_laws = String.format("%s/%s/", RemoteLoginInfo.Targer_NFS_Dir, choice_laws);
        String targer_path = String.format("%s/%s/%s/", RemoteLoginInfo.Targer_NFS_Dir, choice_laws , select_case);
      //  String targer_catalogsPath = String.format("%s/%s/%s/%s", RemoteLoginInfo.Targer_NFS_Dir, choice_laws , select_case,"Catalogs");

        try {
            //如果不是目录则创建目录后再拷贝：/nfsdata/iscas-xosc/system-adas-new/deng_test/C2C_AEB_CCRm_30_100_20_E-NCAP
            client.cd(targer_choice_laws);
        }catch (Exception e){
            client.mkdir(targer_choice_laws);
        }


        try {
            //如果不是目录则创建目录后再拷贝：/nfsdata/iscas-xosc/system-adas-new/deng_test/C2C_AEB_CCRm_30_100_20_E-NCAP
            client.cd(targer_path);
        }catch (Exception e){
            client.mkdir(targer_path);
        }

        try {
            //创建目录：/nfsdata/iscas-xosc/system-adas-new/deng_test/C2C_AEB_CCRm_30_100_20_E-NCAP/Catalogs
          //  client.cd(targer_catalogsPath);
        }catch (Exception e){
           // client.mkdir(targer_catalogsPath);
        }

        log.info("源拷贝:{},拷目标:{}",source_xoscFile_old,targer_path);

        try {

            client.put(source_xoscFile_old, targer_xoscFile_new);
          //  client.put(source_jsonFile_old, targer_jsonFile_new);
          //  client.put(source_paramFile_old, targer_paramFile_new);


            //拷入：Catalogs
            Long start = System.currentTimeMillis();
            //  不拷入：Catalogs
           // this.copyCatalogs(choice_laws, select_case,client);
            Long end = System.currentTimeMillis();
            log.info("上传Catalogs目录文件耗时:{} 毫秒", (end - start));

            log.info("xoscFile正上传的文件:{}",targer_path);
            log.info("xoscFile上传完毕..");



        } catch (Exception e) {
            log.info("directory is not exist");
            client.disconnect();
            client.quit();
            sftp.close();
            throw new Exception();
        } finally {
            client.disconnect();
            client.quit();
            sftp.close();
        }

        Long end = System.currentTimeMillis();
        log.info("上传文件耗时:{} 毫秒", (end - begin));
        return null;

    }


    /*
     * 重命名:
     */
    public String reName(String oldFilePath, String newFilePath) throws Exception{

        Path oldFilePath_source = Paths.get(oldFilePath);
        Path newFilePath_source = Paths.get(newFilePath);

        try {
            Files.move(oldFilePath_source,newFilePath_source ,StandardCopyOption.REPLACE_EXISTING);
            System.out.println("文件重命名成功！");
        } catch (IOException e) {
            System.out.println("文件重命名失败：" + e.getMessage());
            throw new Exception();
        }

        return  null ;
    }


    /**
     * 拷Catalogs文件夹的文件
     */
    public void copyCatalogs(String choice_laws,String select_case,ChannelSftp client) throws Exception {

       // client.hardlink();


        //Catalogs 源
        String source_ControllerCatalog = String.format("%s/%s/%s/%s", targer_Dir, "Catalogs","Controllers" , "ControllerCatalog.xosc");
        String source_EnvironmentCatalog = String.format("%s/%s/%s/%s", targer_Dir, "Catalogs","Environments" , "EnvironmentCatalog.xosc");
        String source_ManeuverCatalog = String.format("%s/%s/%s/%s", targer_Dir, "Catalogs","Maneuvers" , "ManeuverCatalog.xosc");
        String source_MiscObjectCatalog = String.format("%s/%s/%s/%s", targer_Dir, "Catalogs","MiscObjects" , "MiscObjectCatalog.xosc");
        String source_PedestrianCatalog = String.format("%s/%s/%s/%s", targer_Dir, "Catalogs","Pedestrians" , "PedestrianCatalog.xosc");
        String source_RouteCatalog = String.format("%s/%s/%s/%s", targer_Dir, "Catalogs","Routes" ,"RouteCatalog.xosc");
        String source_TrajectoryCatalog = String.format("%s/%s/%s/%s", targer_Dir, "Catalogs","Trajectorys" , "TrajectoryCatalog.xosc");
        String source_VehicleCatalog = String.format("%s/%s/%s/%s", targer_Dir, "Catalogs","Vehicles", "VehicleCatalog.xosc");


        //Catalogs 目标
        String targer_ControllerCatalog = String.format("%s/%s/%s/%s/%s/%s", RemoteLoginInfo.Targer_NFS_Dir,choice_laws,select_case,"Catalogs","Controllers","ControllerCatalog.xosc");
        String targer_EnvironmentCatalog = String.format("%s/%s/%s/%s/%s/%s", RemoteLoginInfo.Targer_NFS_Dir,choice_laws,select_case,"Catalogs","Environments","EnvironmentCatalog.xosc");
        String targer_ManeuverCatalog = String.format("%s/%s/%s/%s/%s/%s", RemoteLoginInfo.Targer_NFS_Dir,choice_laws,select_case,"Catalogs","Maneuvers","ManeuverCatalog.xosc");
        String targer_MiscObjectCatalog = String.format("%s/%s/%s/%s/%s/%s", RemoteLoginInfo.Targer_NFS_Dir,choice_laws,select_case,"Catalogs","MiscObjects","MiscObjectCatalog.xosc");
        String targer_PedestrianCatalog = String.format("%s/%s/%s/%s/%s/%s", RemoteLoginInfo.Targer_NFS_Dir,choice_laws,select_case,"Catalogs","Pedestrians","PedestrianCatalog.xosc");
        String targer_RouteCatalog = String.format("%s/%s/%s/%s/%s/%s", RemoteLoginInfo.Targer_NFS_Dir,choice_laws,select_case,"Catalogs","Routes","RouteCatalog.xosc");
        String targer_TrajectoryCatalog = String.format("%s/%s/%s/%s/%s/%s", RemoteLoginInfo.Targer_NFS_Dir,choice_laws,select_case,"Catalogs","Trajectorys","TrajectoryCatalog.xosc");
        String targer_VehicleCatalog = String.format("%s/%s/%s/%s/%s/%s", RemoteLoginInfo.Targer_NFS_Dir,choice_laws,select_case,"Catalogs","Vehicles","VehicleCatalog.xosc");


       // Catalogs -> /nfsdata/iscas-xosc/Catalogs
     //   String targer_ControllerCatalog_test = String.format("%s/%s/%s/%s", RemoteLoginInfo.Targer_NFS_Dir,choice_laws,select_case,"Catalogs");
     //   client.symlink(targer_ControllerCatalog_test,"/nfsdata/iscas-xosc/Catalogs");
      //  client.hardlink(targer_ControllerCatalog_test,"/nfsdata/iscas-xosc/Catalogs");


        try {

            clientMkdir(targer_ControllerCatalog, client);
            clientMkdir(targer_EnvironmentCatalog, client);
            clientMkdir(targer_ManeuverCatalog, client);
            clientMkdir(targer_MiscObjectCatalog, client);
            clientMkdir(targer_PedestrianCatalog, client);
            clientMkdir(targer_RouteCatalog, client);
            clientMkdir(targer_TrajectoryCatalog, client);
            clientMkdir(targer_VehicleCatalog, client);

            log.info("远程创建Catalog相关目录完毕!");


            client.put(source_ControllerCatalog, targer_ControllerCatalog);
            client.put(source_EnvironmentCatalog, targer_EnvironmentCatalog);
            client.put(source_ManeuverCatalog, targer_ManeuverCatalog);
            client.put(source_MiscObjectCatalog, targer_MiscObjectCatalog);
            client.put(source_PedestrianCatalog, targer_PedestrianCatalog);
            client.put(source_RouteCatalog, targer_RouteCatalog);
            client.put(source_TrajectoryCatalog, targer_TrajectoryCatalog);
            client.put(source_VehicleCatalog, targer_VehicleCatalog);

            log.info("上传远程的Catalog目录文件完成!");
        }catch (Exception e){
            client.quit();
            e.printStackTrace();
            throw new Exception();
        }


    }


    /***
     * 创建远程目录:
     */
    public void clientMkdir(String targe_CatalogPath,ChannelSftp client) throws SftpException {

        int startIndex =  targe_CatalogPath.indexOf("/");
        int endIndex =  targe_CatalogPath.lastIndexOf("/");
        String filePath = targe_CatalogPath.substring(startIndex,endIndex);

      //  log.info("filePath:{}",filePath);

        try{
            client.cd(filePath);
        }catch (Exception e){
           log.info("创建目录:{}",filePath);
            client.mkdir(filePath);
        }

    }

}

