package cn.huanzi.qch.baseadmin.adas.service;

import cn.huanzi.qch.baseadmin.adas.entity.RemoteLoginInfo;
import cn.hutool.extra.ssh.JschUtil;
import cn.hutool.extra.ssh.Sftp;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * 从可运行的xosc拷到场景编辑器
 */

@Service
public class RunToSceneEditorService {

    Log log = LogFactory.get();
    String sceneedit_Dir = System.getProperty("user.dir") + "/temp/nfstoSceneedit";

    /**
     * 文件下载：
     *
     * @param nfsSelectCase,sceneEditPath
     * @throws SftpException
     */
    public String downLoadNfsXosc(String choice_laws,String nfsSelectCase, String sceneEditId) throws Exception {
        Long begin = System.currentTimeMillis();
        Sftp sftp = JschUtil.createSftp(RemoteLoginInfo.Targer_sshHost, RemoteLoginInfo.Targer_sshPort, RemoteLoginInfo.Targer_sshUser, RemoteLoginInfo.Targer_sshPass);
        ChannelSftp client = sftp.getClient();

        String sourceXosc = String.format("%s/%s/%s/%s", RemoteLoginInfo.Targer_NFS_Dir, choice_laws, nfsSelectCase, nfsSelectCase+".xosc");
       // String sourceXosc_Dir = String.format("%s/%s/%s", RemoteLoginInfo.Targer_NFS_Dir, choice_laws, nfsSelectCase);

        log.info("源xoscFile:{}", sourceXosc);
        log.info("目标xoscFile:{}", sceneedit_Dir);


        try {
            File file = new File(sceneedit_Dir);
            if (!file.exists()) {
                file.mkdir();
            }

            client.get(sourceXosc, sceneedit_Dir);
            

            log.info("xoscFile正下载的文件:{}", sourceXosc);
            Long end = System.currentTimeMillis();
            log.info("下载文件耗时:{} 毫秒", (end - begin));

            File selectCaseFile = new File(sourceXosc);
            String selectCaseFileName = selectCaseFile.getName();

            String sceneedit_DirResult = String.format("%s/%s", sceneedit_Dir, selectCaseFileName);


            this.uploadXoscFileToSceneEdit(sceneedit_DirResult, sceneEditId);

        } catch (Exception e) {
            log.info("场景还原时下载到中间服务失败!");
            client.disconnect();
            client.quit();
            throw new Exception();
        } finally {
            client.disconnect();
            client.quit();
        }


        return null;

    }


    /**
     * 文件上传到重庆机器的场景编辑器
     *
     * @param sceneedit_DirResult, sceneEditPath
     * @throws SftpException
     */
    public String uploadXoscFileToSceneEdit(String sceneedit_DirResult, String sceneEditId) throws Exception {

        Long begin = System.currentTimeMillis();
        Sftp sftp = JschUtil.createSftp(RemoteLoginInfo.Source_sshHost, RemoteLoginInfo.Source_sshPort, RemoteLoginInfo.Source_sshUser, RemoteLoginInfo.Source_sshPass);
        ChannelSftp client = sftp.getClient();



        String sceneEditIdXosc = String.format("%s/%s/%s", RemoteLoginInfo.Custom_Scene_Path, sceneEditId, sceneEditId + ".xosc");
        String sceneEditIdXoscDir = String.format("%s/%s", RemoteLoginInfo.Custom_Scene_Path, sceneEditId);

        try {
            //如果不是目录则创建目录后再拷贝：/nfsdata/iscas-xosc/system-adas-new/deng_test/C2C_AEB_CCRm_30_100_20_E-NCAP
            client.cd(sceneEditIdXoscDir);
        } catch (Exception e) {
            client.mkdir(sceneEditIdXoscDir);
        }

        log.info("源拷贝:{},拷目标:{}", sceneedit_DirResult, sceneEditIdXosc);

        try {
            //删除后再拷贝:
            client.rm(sceneEditIdXosc);
            client.put(sceneedit_DirResult, sceneEditIdXosc);
        } catch (Exception e) {
            log.info("场景还原时上传到场景编辑器失败!");
            client.disconnect();
            client.quit();
            throw new Exception();
        } finally {
            client.disconnect();
            client.quit();
        }

        Long end = System.currentTimeMillis();
        log.info("上传文件耗时:{} 毫秒", (end - begin));
        return null;

    }


    public static void main(String[] args) throws Exception {

        RunToSceneEditorService  test = new RunToSceneEditorService();
        String choice_laws = "E-NCAP2023" ;
        String nfsSelectCase = "C2C_FCW_CCCscp_40_20_E-NCAP";
        String sceneEditId = "a9ae6192f1e44442b3161b9a1ed06e88";
        test.downLoadNfsXosc(choice_laws,nfsSelectCase,sceneEditId);



    }


    /**
     * 远程拷贝及计算：
     */






}
