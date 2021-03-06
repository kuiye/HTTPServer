package com.redhat.rpc.manager;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.rpc.mapper.InfoMapper;
import com.redhat.rpc.model.InfoModel;
import com.redhat.rpc.util.SqlSessionFactoryUtil;

/**
 * @author littleredhat
 */
public class InfoManager {
	private static Logger logger = LoggerFactory.getLogger(InfoManager.class);
	private static InfoManager instance;

	public static InfoManager getInstance() {
		if (instance == null) {
			// 双检查锁机制
			synchronized (InfoManager.class) {
				if (instance == null) {
					instance = new InfoManager();
				}
			}
		}
		return instance;
	}

	// 获取信息
	public InfoModel getInfo(long id) {
		SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
		try {
			// 获取映射
			InfoMapper infoMapper = sqlSession.getMapper(InfoMapper.class);
			// 执行事务
			InfoModel model = infoMapper.getInfo(id);
			sqlSession.commit();
			// 返回结果
			return model;
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			sqlSession.rollback();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return null;
	}

	// 更新信息
	public boolean updateInfo(long id, InfoModel info) {
		SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
		try {
			// 获取映射
			InfoMapper infoMapper = sqlSession.getMapper(InfoMapper.class);
			// 执行事务
			boolean result = infoMapper.updateInfo(id, info);
			sqlSession.commit();
			// 返回结果
			return result;
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			sqlSession.rollback();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return false;
	}
}
