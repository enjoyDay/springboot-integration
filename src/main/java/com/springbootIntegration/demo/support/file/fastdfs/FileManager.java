

package com.springbootIntegration.demo.support.file.fastdfs;

import com.luhuiguo.fastdfs.conn.ConnectionManager;
import com.luhuiguo.fastdfs.conn.ConnectionPoolConfig;
import com.luhuiguo.fastdfs.conn.FdfsConnectionPool;
import com.luhuiguo.fastdfs.conn.PooledConnectionFactory;
import com.luhuiguo.fastdfs.conn.TrackerConnectionManager;
import com.luhuiguo.fastdfs.service.DefaultFastFileStorageClient;
import com.luhuiguo.fastdfs.service.DefaultTrackerClient;
import com.luhuiguo.fastdfs.service.FastFileStorageClient;
import com.luhuiguo.fastdfs.service.TrackerClient;
import java.io.Serializable;

import com.springbootIntegration.demo.util.DataUtil;
import com.springbootIntegration.demo.util.InstanceUtil;
import com.springbootIntegration.demo.util.PropertiesUtil;
import com.springbootIntegration.demo.util.DataUtil;
import com.springbootIntegration.demo.util.InstanceUtil;
import com.springbootIntegration.demo.util.PropertiesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileManager implements Serializable {
    private static Logger logger = LogManager.getLogger();
    private static FileManager fileManager;
    private FastFileStorageClient fastFileStorageClient;

    public static FileManager getInstance() {
        if (fileManager == null) {
            Class var0 = FileManager.class;
            synchronized(FileManager.class) {
                fileManager = new FileManager();
            }
        }

        return fileManager;
    }

    private FileManager() {
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
        pooledConnectionFactory.setSoTimeout(PropertiesUtil.getInt("fdfs.soTimeout", 1000));
        pooledConnectionFactory.setConnectTimeout(PropertiesUtil.getInt("fdfs.connectTimeout", 1000));
        ConnectionPoolConfig connectionPoolConfig = new ConnectionPoolConfig();
        FdfsConnectionPool pool = new FdfsConnectionPool(pooledConnectionFactory, connectionPoolConfig);
        TrackerConnectionManager trackerConnectionManager = new TrackerConnectionManager(pool, InstanceUtil.newArrayList(PropertiesUtil.getString("fdfs.trackerList").split(",")));
        TrackerClient trackerClient = new DefaultTrackerClient(trackerConnectionManager);
        ConnectionManager connectionManager = new ConnectionManager(pool);
        this.fastFileStorageClient = new DefaultFastFileStorageClient(trackerClient, connectionManager);
    }

    public void upload(FileModel file) {
        String path;
        if (DataUtil.isEmpty(file.getGroupName())) {
            path = this.fastFileStorageClient.uploadFile(file.getContent(), file.getExt()).getFullPath();
            logger.info("Upload to fastdfs success =>" + path);
            file.setRemotePath(PropertiesUtil.getString("fdfs.fileHost") + path);
        } else {
            path = this.fastFileStorageClient.uploadFile(file.getGroupName(), file.getContent(), file.getExt()).getFullPath();
            logger.info("Upload to fastdfs success =>" + path);
            file.setRemotePath(PropertiesUtil.getString("fdfs.fileHost") + path);
        }

    }

    public FileModel getFile(String groupName, String path) {
        FileModel file = new FileModel();
        file.setContent(this.fastFileStorageClient.downloadFile(groupName, path));
        return file;
    }

    public void deleteFile(String groupName, String path) throws Exception {
        this.fastFileStorageClient.deleteFile(groupName, path);
    }
}
