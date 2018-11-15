package com.osetrova.project.dao.entitydao;

import com.osetrova.project.dao.BaseDaoImpl;
import com.osetrova.project.dao.entitydaointerface.ScreenshotDao;
import com.osetrova.project.entity.Screenshot;
import com.osetrova.project.entity.embeddable.GameScreenshot;
import org.springframework.stereotype.Repository;

@Repository
public class ScreenshotDaoImpl extends BaseDaoImpl<Screenshot, GameScreenshot> implements ScreenshotDao {

    @Override
    public Class<Screenshot> getEntityClass() {
        return Screenshot.class;
    }
}
